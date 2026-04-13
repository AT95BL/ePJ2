package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import battery.Battery;
import battery.BatteryException;
import bill.Bill;
import javacitymap.JavaCityMap;
import malfunction.Malfunction;
import monitor.RentalRepairCostsMonitor;
import monitor.RentalSalaryMonitor;
import monitor.RentalSalesMonitor;
import passenger.Passenger;
import rental.Rental;
import utility.ConfigFileReader;

/**
 * Abstract base class for all vehicles in the city fleet.
 *
 * <p>Each vehicle runs as its own {@link Thread}, moving step-by-step across the
 * {@link JavaCityMap} grid from its start position to its destination, then generating
 * a {@link Bill} for its passenger.
 *
 * <p>Thread safety: individual cell moves are protected by a per-instance
 * {@link ReentrantLock}; map-cell access is protected inside {@link JavaCityMap}.
 */
public abstract class Vehicle extends Thread implements Serializable {

    // -------------------------------------------------------------------------
    // Movement / boundary messages
    // -------------------------------------------------------------------------
    private static final String MSG_BLOCKED_LEFT    = "Cannot move left — already at western boundary.";
    private static final String MSG_BLOCKED_RIGHT   = "Cannot move right — already at eastern boundary.";
    private static final String MSG_BLOCKED_UP      = "Cannot move up — already at northern boundary.";
    private static final String MSG_BLOCKED_DOWN    = "Cannot move down — already at southern boundary.";
    private static final String MSG_UNKNOWN         = "UNKNOWN";

    /** Battery percentage drained per movement step. */
    private static final double BATTERY_DISCHARGE_PER_STEP = 2.0;

    // -------------------------------------------------------------------------
    // Core attributes
    // -------------------------------------------------------------------------
    public String vehicleId;
    public String manufacturer;
    public String model;
    public Date purchaseDate;
    public double purchasePrice;
    public double autonomyOrMaxSpeed;
    public double maxSpeed;
    public String description;
    public VehicleType vehicleType;          // replaces the raw `type` string field

    public Battery battery;
    public boolean hasMalfunction = false;
    public Malfunction malfunctionModel = null;

    long tripDuration;                       // travel time from A → B (seconds)

    // Map coordinates
    int positionX;
    int positionY;
    int startPositionX;
    int startPositionY;
    int destinationPositionX;
    int destinationPositionY;

    // Passengers
    public int numberOfPassengers;
    public List<Passenger> passengers = new ArrayList<>();

    private static final ReentrantLock moveLock = new ReentrantLock();

    /** Used to read pricing constants at billing time. */
    ConfigFileReader configReader = new ConfigFileReader();

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    public Vehicle() {}

    public Vehicle(
            String vehicleId,
            String manufacturer,
            String model,
            Date purchaseDate,
            double purchasePrice,
            double autonomyOrMaxSpeed,
            double maxSpeed,
            String description,
            VehicleType vehicleType
    ) {
        this.vehicleId          = vehicleId;
        this.manufacturer       = manufacturer;
        this.model              = model;
        this.purchaseDate       = purchaseDate;
        this.purchasePrice      = purchasePrice;
        this.autonomyOrMaxSpeed = autonomyOrMaxSpeed;
        this.maxSpeed           = maxSpeed;
        this.description        = description;
        this.vehicleType        = vehicleType;
    }

    // -------------------------------------------------------------------------
    // Thread entry point
    // -------------------------------------------------------------------------

    @Override
    public void run() {
        if (isAlreadyAtDestination()) {
            System.out.println("Vehicle " + vehicleId + " is already at its destination — no movement needed.");
            return;
        }

        int stepsX = Math.abs(destinationPositionX - positionX);
        int stepsY = Math.abs(destinationPositionY - positionY);
        int totalSteps = stepsX + stepsY;

        if (totalSteps == 0) {
            System.err.println("Error: computed zero total steps for vehicle " + vehicleId);
            return;
        }

        long msPerStep = (tripDuration * 1000L) / totalSteps;

        try {
            driveToDestination(msPerStep);
            generateBill(msPerStep, totalSteps);
        } catch (InterruptedException ex) {
            System.err.println("Vehicle " + vehicleId + " interrupted: " + ex.getMessage());
            Thread.currentThread().interrupt();
        } catch (IndexOutOfBoundsException ex) {
            System.err.println("Vehicle " + vehicleId + " went out of bounds: " + ex.getMessage());
        } finally {
            if (isAlreadyAtDestination()) {
                JavaCityMap.clearCell(positionX, positionY);
            }
        }
    }

    // -------------------------------------------------------------------------
    // Private helpers
    // -------------------------------------------------------------------------

    private boolean isAlreadyAtDestination() {
        return positionX == destinationPositionX && positionY == destinationPositionY;
    }

    /**
     * Moves the vehicle along the X axis first, then the Y axis,
     * sleeping {@code msPerStep} milliseconds between each step.
     */
    private void driveToDestination(long msPerStep) throws InterruptedException {
        int stepCount = 0;

        while (positionX != destinationPositionX) {
            if (positionX < destinationPositionX) {
                System.out.printf("Vehicle %s — step %d: moving SOUTH%n", vehicleId, ++stepCount);
                moveDown();
            } else {
                System.out.printf("Vehicle %s — step %d: moving NORTH%n", vehicleId, ++stepCount);
                moveUp();
            }
            battery.discharge(BATTERY_DISCHARGE_PER_STEP);
            Thread.sleep(msPerStep);
        }

        while (positionY != destinationPositionY) {
            if (positionY < destinationPositionY) {
                System.out.printf("Vehicle %s — step %d: moving EAST%n", vehicleId, ++stepCount);
                moveRight();
            } else {
                System.out.printf("Vehicle %s — step %d: moving WEST%n", vehicleId, ++stepCount);
                moveLeft();
            }
            battery.discharge(BATTERY_DISCHARGE_PER_STEP);
            Thread.sleep(msPerStep);
        }
    }

    /**
     * Computes the trip fare and assigns a {@link Bill} to the first passenger.
     */
    private void generateBill(long msPerStep, int totalSteps) {
        // --- Update sales counters ---
        switch (vehicleType) {
            case CAR     -> RentalSalesMonitor.incrementCarRentals();
            case BIKE    -> RentalSalesMonitor.incrementBikeRentals();
            case SCOOTER -> RentalSalesMonitor.incrementScooterRentals();
        }

        // --- Base unit price ---
        String priceKey = switch (vehicleType) {
            case CAR     -> "CAR_UNIT_PRICE";
            case BIKE    -> "BIKE_UNIT_PRICE";
            case SCOOTER -> "SCOOTER_UNIT_PRICE";
        };
        double unitPrice = Double.parseDouble(configReader.getProperty(priceKey));

        double fare = unitPrice * msPerStep;

        // --- Distance zone multiplier ---
        boolean inWideZone = JavaCityMap.checkWidePartOfTheJavaCity(positionX, positionY);
        String distanceKey = inWideZone ? "DISTANCE_WIDE" : "DISTANCE_NARROW";
        fare *= Double.parseDouble(configReader.getProperty(distanceKey));

        // --- Malfunction waiver ---
        if (vehicleType == VehicleType.CAR && hasMalfunction) {
            fare = 0;
        } else {
            double discountRate = Double.parseDouble(configReader.getProperty("DISCOUNT"));
            RentalSalaryMonitor.addDiscountRevenue(fare * discountRate);

            if (Rental.RENTAL_COUNTER % 10 == 0) {
                double promoRate = Double.parseDouble(configReader.getProperty("DISCOUNT_PROM"));
                RentalSalaryMonitor.addPromotionRevenue(fare * promoRate);
                fare -= fare * promoRate;
            }

            fare -= fare * discountRate;
        }

        RentalSalaryMonitor.addTotalRevenue(fare);

        // --- Assign bill to passenger ---
        if (!passengers.isEmpty()) {
            passengers.get(0).bill = new Bill(fare, vehicleType.getLabel(), passengers.get(0), hasMalfunction);
        }
    }

    // -------------------------------------------------------------------------
    // Movement primitives (package-private for testing)
    // -------------------------------------------------------------------------

    public void moveDown() {
        moveLock.lock();
        try {
            if (positionX == JavaCityMap.NUMBER_OF_ROWS - 1) throw new IndexOutOfBoundsException(MSG_BLOCKED_DOWN);
            if (JavaCityMap.isCellClear(positionX + 1, positionY)) {
                JavaCityMap.clearCell(positionX, positionY);
                positionX++;
                JavaCityMap.updateCell(positionX, positionY, this);
            } else {
                System.err.println("Cannot move south — cell occupied.");
            }
        } finally { moveLock.unlock(); }
    }

    public void moveUp() {
        moveLock.lock();
        try {
            if (positionX == 0) throw new IndexOutOfBoundsException(MSG_BLOCKED_UP);
            if (JavaCityMap.isCellClear(positionX - 1, positionY)) {
                JavaCityMap.clearCell(positionX, positionY);
                positionX--;
                JavaCityMap.updateCell(positionX, positionY, this);
            } else {
                System.err.println("Cannot move north — cell occupied.");
            }
        } finally { moveLock.unlock(); }
    }

    public void moveLeft() {
        moveLock.lock();
        try {
            if (positionY == 0) throw new IndexOutOfBoundsException(MSG_BLOCKED_LEFT);
            if (JavaCityMap.isCellClear(positionX, positionY - 1)) {
                JavaCityMap.clearCell(positionX, positionY);
                positionY--;
                JavaCityMap.updateCell(positionX, positionY, this);
            } else {
                System.err.println("Cannot move west — cell occupied.");
            }
        } finally { moveLock.unlock(); }
    }

    public void moveRight() {
        moveLock.lock();
        try {
            if (positionY == JavaCityMap.NUMBER_OF_COLUMNS - 1) throw new IndexOutOfBoundsException(MSG_BLOCKED_RIGHT);
            if (JavaCityMap.isCellClear(positionX, positionY + 1)) {
                JavaCityMap.clearCell(positionX, positionY);
                positionY++;
                JavaCityMap.updateCell(positionX, positionY, this);
            } else {
                System.err.println("Cannot move east — cell occupied.");
            }
        } finally { moveLock.unlock(); }
    }

    // -------------------------------------------------------------------------
    // Accessors
    // -------------------------------------------------------------------------

    public String getVehicleId()            { return vehicleId; }
    public void   setVehicleId(String id)   { this.vehicleId = id; }

    public String getManufacturer()                   { return manufacturer; }
    public void   setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }

    public String getModel()             { return model; }
    public void   setModel(String model) { this.model = model; }

    public Date getPurchaseDate()                 { return purchaseDate; }
    public void setPurchaseDate(Date purchaseDate) { this.purchaseDate = purchaseDate; }

    public double getPurchasePrice()                   { return purchasePrice; }
    public void   setPurchasePrice(double purchasePrice) { this.purchasePrice = purchasePrice; }

    public double getAutonomyOrMaxSpeed()                          { return autonomyOrMaxSpeed; }
    public void   setAutonomyOrMaxSpeed(double autonomyOrMaxSpeed) { this.autonomyOrMaxSpeed = autonomyOrMaxSpeed; }

    public double getMaxSpeed()                { return maxSpeed; }
    public void   setMaxSpeed(double maxSpeed) { this.maxSpeed = maxSpeed; }

    public String getDescription()                    { return description; }
    public void   setDescription(String description)  { this.description = description; }

    public VehicleType getVehicleType()                  { return vehicleType; }
    public void        setVehicleType(VehicleType t)     { this.vehicleType = t; }

    public Battery getBattery()                { return battery; }
    public void    setBattery(Battery battery) { this.battery = battery; }

    public boolean isMalfunction()                    { return hasMalfunction; }
    public void    setMalfunction(boolean malfunction) { this.hasMalfunction = malfunction; }

    public Malfunction getMalfunctionModel()                        { return malfunctionModel; }
    public void        setMalfunctionModel(Malfunction m)           { this.malfunctionModel = m; }

    public int  getPositionX()             { return positionX; }
    public void setPositionX(int x)        { this.positionX = x; }

    public int  getPositionY()             { return positionY; }
    public void setPositionY(int y)        { this.positionY = y; }

    public int  getStartPositionX()        { return startPositionX; }
    public void setStartPositionX(int x)   { this.startPositionX = x; }

    public int  getStartPositionY()        { return startPositionY; }
    public void setStartPositionY(int y)   { this.startPositionY = y; }

    public int  getDestinationPositionX()  { return destinationPositionX; }
    public void setDestinationPositionX(int x) { this.destinationPositionX = x; }

    public int  getDestinationPositionY()  { return destinationPositionY; }
    public void setDestinationPositionY(int y) { this.destinationPositionY = y; }

    public int              getNumberOfPassengers()                      { return numberOfPassengers; }
    public void             setNumberOfPassengers(int n)                 { this.numberOfPassengers = n; }
    public List<Passenger>  getPassengers()                              { return passengers; }
    public void             setPassengers(List<Passenger> passengers)    { this.passengers = passengers; }
    public void             addPassenger(Passenger p)                    { this.passengers.add(p); }

    public long getDuration()               { return tripDuration; }
    public void setDuration(long duration)  { this.tripDuration = duration; }

    /** Returns the color this vehicle should be displayed as on the map. */
    public abstract java.awt.Color getMapColor();

    // -------------------------------------------------------------------------
    // toString
    // -------------------------------------------------------------------------

    @Override
    public String toString() {
        String passengerList = passengers.isEmpty()
                ? MSG_UNKNOWN
                : passengers.stream().map(Object::toString).reduce("", (a, b) -> a + b + " ");

        return "Vehicle ID   : " + vehicleId + "\n"
             + "Manufacturer : " + manufacturer + "\n"
             + "Model        : " + model + "\n"
             + "Purchased    : " + (purchaseDate != null ? purchaseDate : MSG_UNKNOWN) + "\n"
             + "Price        : " + purchasePrice + "\n"
             + "Range/Speed  : " + autonomyOrMaxSpeed + "\n"
             + "Max Speed    : " + maxSpeed + "\n"
             + "Description  : " + description + "\n"
             + "Type         : " + vehicleType + "\n"
             + battery + "\n"
             + "Start        : [" + startPositionX + "][" + startPositionY + "]\n"
             + "Position     : [" + positionX + "][" + positionY + "]\n"
             + "Destination  : [" + destinationPositionX + "][" + destinationPositionY + "]\n"
             + "Passengers   : " + passengerList + "\n";
    }
}
