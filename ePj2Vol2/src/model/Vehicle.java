package model;

// std java library
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;

import java.util.concurrent.locks.ReentrantLock;

import battery.*;
import utility.ConfigFileReader;
import javacitymap.JavaCityMap;
import malfunction.*;
import monitor.*;
import passenger.*;
import rental.Rental;
import bill.Bill;

/**
 * @author AT95
 * @version 1
 * The {@code Vehicle} class represents an abstract vehicle with various attributes
 * and methods to manage its state and behavior.
 * <p>
 * This class extends {@code Thread} and implements {@code Serializable}, allowing
 * vehicles to run as separate threads and be serialized.
 * </p>
 */
public abstract class Vehicle extends Thread implements Serializable {
    // system messages --NE OBRAĆAJ PAŽNJU!!
    private static final String MESSAGE_LEFT = "Cannot move left. Position is out of bounds.";
    private static final String MESSAGE_RIGHT = "Cannot move right. Position is out of bounds.";
    private static final String MESSAGE_UP = "Cannot move upward. Position is out of bounds.";
    private static final String MESSAGE_DOWN = "Cannot move downward. Position is out of bounds.";
    private static final String MESSAGE_UNKNOWN = "UNKNOWN";
    private static final int	MESSAGE_BATTERY_LEAK = 2;

    // class attributes according to the given-task
    public String vehicleId;               	   		 // 1
    public String manufacturer;             		 // 2 proizvodjac
    public String model;                    		 // 3 model of car/bike/scooter
    public Date purchaseDate;                		 // 4 datum nabavke
    public double purchasePrice;            		 // 5 cijena nabavke
    public double autonomyOrMaxSpeed;        		 // 6 domet sa jednim punjenjem -autonomija
    public double maxSpeed;                    		 // 7
    public String description;                		 // 8 opis
    public String type;                              // 9 tekstualni opis tipa --> car/bike/scooter
    public Battery battery;                        // baterija -status, kapacitet
    public boolean malfunction = false;   	       // malfunction detector
    public Malfunction malfunctionModel = null;    // malfunction model

    long duration;                           	   // VRIJEME TRAJANJA KRETANJA A->B

    // vehicle map-coordinates/coordinating
    int positionX;                                        // row
    int positionY;                                        // column
    int startPositionX;                                   // start row
    int startPositionY;                                   // start column
    int destinationPositionX;                             // final row
    int destinationPositionY;                             // final column

    // passengers info
    public int numberOfPassengers;
    public List<Passenger> listOfPassengers = new ArrayList<>();
    
    private static final ReentrantLock classLock = new ReentrantLock();
    
    ConfigFileReader configFileReader = new ConfigFileReader(); // for bills ..
    
    /**
     * Default constructor for the {@code Vehicle} class.
     */
    public Vehicle() {}

    /**
     * Parameterized constructor for the {@code Vehicle} class.
     *
     * @param vehicleId           the ID of the vehicle
     * @param manufacturer        the manufacturer of the vehicle
     * @param model               the model of the vehicle
     * @param purchaseDate        the purchase date of the vehicle
     * @param purchasePrice       the purchase price of the vehicle
     * @param autonomyOrMaxSpeed  the autonomy or max speed of the vehicle
     * @param maxSpeed            the maximum speed of the vehicle
     * @param description         the description of the vehicle
     * @param type                the type of the vehicle (car, bike, scooter)
     */
    public Vehicle(
            String vehicleId,
            String manufacturer,
            String model,
            Date purchaseDate,
            double purchasePrice,
            double autonomyOrMaxSpeed,
            double maxSpeed,
            String description,
            String type
    ) {
        this.vehicleId = vehicleId;
        this.manufacturer = manufacturer;
        this.model = model;
        this.purchaseDate = purchaseDate;
        this.purchasePrice = purchasePrice;
        this.autonomyOrMaxSpeed = autonomyOrMaxSpeed;
        this.maxSpeed = maxSpeed;
        this.description = description;
        this.type = type;
    }

    /**
     * Retrieves the vehicle ID.
     *
     * @return The vehicle ID.
     */
    public String getVehicleId() {
        return this.vehicleId;
    }
    
    /**
     * Sets the vehicle ID.
     *
     * @param id The vehicle ID to set.
     */
    public void setVehicleId(String id) {
        this.vehicleId = id;
    }

    /**
     * Retrieves the manufacturer.
     *
     * @return The manufacturer.
     */
    public String getManufacturer() {
        return manufacturer;
    }
    
    /**
     * Sets the manufacturer.
     *
     * @param manufacturer The manufacturer to set.
     */
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    /**
     * Retrieves the model.
     *
     * @return The model.
     */
    public String getModel() {
        return model;
    }
    
    /**
     * Sets the model.
     *
     * @param model The model to set.
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * Retrieves the purchase date.
     *
     * @return The purchase date.
     */
    public Date getpurchaseDate() {
        return purchaseDate;
    }
    
    /**
     * Sets the purchase date.
     *
     * @param purchaseDate The purchase date to set.
     */
    public void setpurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    /**
     * Retrieves the purchase price.
     *
     * @return The purchase price.
     */
    public double getPurchasePrice() {
        return purchasePrice;
    }
    
    /**
     * Sets the purchase price.
     *
     * @param purchasePrice The purchase price to set.
     */
    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    /**
     * Retrieves the autonomy or maximum speed.
     *
     * @return The autonomy or maximum speed.
     */
    public double getAutonomyOrMaxSpeed() {
        return autonomyOrMaxSpeed;
    }
    
    /**
     * Sets the autonomy or maximum speed.
     *
     * @param autonomyOrMaxSpeed The autonomy or maximum speed to set.
     */
    public void setAutonomyOrMaxSpeed(double autonomyOrMaxSpeed) {
        this.autonomyOrMaxSpeed = autonomyOrMaxSpeed;
    }

    /**
     * Retrieves the maximum speed.
     *
     * @return The maximum speed.
     */
    public double getMaxSpeed() {
        return maxSpeed;
    }
    
    /**
     * Sets the maximum speed.
     *
     * @param maxSpeed The maximum speed to set.
     */
    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    /**
     * Retrieves the description.
     *
     * @return The description.
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Sets the description.
     *
     * @param description The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Retrieves the type.
     *
     * @return The type.
     */
    public String getType() {
        return this.type;
    }
    
    /**
     * Sets the type.
     *
     * @param type The type to set.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Retrieves the battery.
     *
     * @return The battery.
     */
    public Battery getBattery() {
        return this.battery;
    }
    
    /**
     * Sets the battery.
     *
     * @param battery The battery to set.
     */
    public void setBattery(Battery battery) {
        this.battery = battery;
    }

    /**
     * Retrieves the malfunction status.
     *
     * @return true if the vehicle has a malfunction, false otherwise.
     */
    public boolean isMalfunction() {
        return malfunction;
    }
    
    /**
     * Sets the malfunction status.
     *
     * @param malfunction The malfunction status to set.
     */
    public void setMalfunction(boolean malfunctione) {
        this.malfunction = malfunctione;
    }

    /**
     * Retrieves the malfunction model.
     *
     * @return The malfunction model.
     */
    public Malfunction getMalfunctionModel() {
        return this.malfunctionModel;
    }
    
    /**
     * Sets the malfunction model.
     *
     * @param malfunctionModel The malfunction model to set.
     */
    public void setMalfunctionModel(Malfunction malfunctionModel) {
        this.malfunctionModel = malfunctionModel;
    }

    /**
     * Retrieves the X position.
     *
     * @return The X position.
     */
    public int getPositionX() {
        return positionX;
    }
    
    /**
     * Sets the X position.
     *
     * @param positionX The X position to set.
     */
    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    /**
     * Retrieves the Y position.
     *
     * @return The Y position.
     */
    public int getPositionY() {
        return positionY;
    }
    
    /**
     * Sets the Y position.
     *
     * @param positionY The Y position to set.
     */
    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    /**
     * Retrieves the start X position.
     *
     * @return The start X position.
     */
    public int getStartPositionX() {
        return startPositionX;
    }
    
    /**
     * Sets the start X position.
     *
     * @param startPositionX The start X position to set.
     */
    public void setStartPositionX(int startPositionX) {
        this.startPositionX = startPositionX;
    }

    /**
     * Retrieves the start Y position.
     *
     * @return The start Y position.
     */
    public int getStartPositionY() {
        return startPositionY;
    }
    
    /**
     * Sets the start Y position.
     *
     * @param startPositionY The start Y position to set.
     */
    public void setStartPositionY(int startPositionY) {
        this.startPositionY = startPositionY;
    }

    /**
     * Retrieves the destination X position.
     *
     * @return The destination X position.
     */
    public int getDestinationPositionX() {
        return destinationPositionX;
    }
    
    /**
     * Sets the destination X position.
     *
     * @param destinationPositionX The destination X position to set.
     */
    public void setDestinationPositionX(int destinationPositionX) {
        this.destinationPositionX = destinationPositionX;
    }

    /**
     * Retrieves the destination Y position.
     *
     * @return The destination Y position.
     */
    public int getDestinationPositionY() {
        return destinationPositionY;
    }
    
    /**
     * Sets the destination Y position.
     *
     * @param destinationPositionX The destination Y position to set.
     */
    public void setDestinationPositionY(int destinationPositionY) {
        this.destinationPositionY = destinationPositionY;
    }

    /**
     * Retrieves the number of passengers.
     *
     * @return The number of passengers.
     */
    public int getNumberOfPassengers() {
        return numberOfPassengers;
    }
    
    /**
     * Sets the number of passengers.
     *
     * @param numberOfPassengers The number of passengers to set.
     */
    public void setNumberOfPassengers(int numberOfPassengers) {
        this.numberOfPassengers = numberOfPassengers;
    }

    /**
     * Retrieves the list of passengers.
     *
     * @return The list of passengers.
     */
    public List<Passenger> getListOfPassengers() {
        return listOfPassengers;
    }
    
    /**
     * Sets the list of passengers.
     *
     * @param listOfPassengers The list of passengers to set.
     */
    public void setListOfPassengers(List<Passenger> listOfPassengers) {
        this.listOfPassengers = listOfPassengers;
    }
    
    /**
     * Adds a passenger to the list of passengers.
     *
     * @param passenger The passenger to add.
     */
    public void addPassenger(Passenger passenger) {
    	this.listOfPassengers.add(passenger);
    }
    
    /**
     * Retrieves the duration.
     *
     * @return The duration.
     */
    public long getDuration() {
        return this.duration;
    }
    
    /**
     * Sets the duration.
     *
     * @param duration The duration to set.
     */
    public void setDuration(long duration) {
        this.duration = duration;
    }
    
    /**
     * Moves the vehicle downwards on the map.
     */
    public void moveDown() {
        classLock.lock();
        try {
            if (this.positionX == JavaCityMap.NUMBER_OF_ROWS - 1) {
                throw new IndexOutOfBoundsException(MESSAGE_DOWN);
            }
            if (JavaCityMap.isCellClear(this.positionX + 1, this.positionY)) {
                JavaCityMap.clearCell(this.positionX, this.positionY);
                this.positionX++;
                JavaCityMap.updateCell(this.positionX, this.positionY, this);
            } else {
                System.err.println("Cannot move down, cell is not clear.");
            }
        } finally {
            classLock.unlock();
        }
    }
    
    /**
     * Moves the vehicle upwards on the map.
     */
    public void moveUp() {
        classLock.lock();
        try {
            if (this.positionX == 0) {
                throw new IndexOutOfBoundsException(MESSAGE_UP);
            }
            if (JavaCityMap.isCellClear(this.positionX - 1, this.positionY)) {
                JavaCityMap.clearCell(this.positionX, this.positionY);
                this.positionX--;
                JavaCityMap.updateCell(this.positionX, this.positionY, this);
            } else {
                System.err.println("Cannot move up, cell is not clear.");
            }
        } finally {
            classLock.unlock();
        }
    }
    
    /**
     * Moves the vehicle left on the map.
     */
    public void moveLeft() {
        classLock.lock();
        try {
            if (this.positionY == 0) {
                throw new IndexOutOfBoundsException(MESSAGE_LEFT);
            }
            if (JavaCityMap.isCellClear(this.positionX, this.positionY - 1)) {
                JavaCityMap.clearCell(this.positionX, this.positionY);
                this.positionY--;
                JavaCityMap.updateCell(this.positionX, this.positionY, this);
            } else {
                System.err.println("Cannot move left, cell is not clear.");
            }
        } finally {
            classLock.unlock();
        }
    }
    
    /**
     * Moves the vehicle right on the map.
     */
    public void moveRight() {
        classLock.lock();
        try {
            if (this.positionY == JavaCityMap.NUMBER_OF_COLUMNS - 1) {
                throw new IndexOutOfBoundsException(MESSAGE_RIGHT);
            }
            if (JavaCityMap.isCellClear(this.positionX, this.positionY + 1)) {
                JavaCityMap.clearCell(this.positionX, this.positionY);
                this.positionY++;
                JavaCityMap.updateCell(this.positionX, this.positionY, this);
            } else {
                System.err.println("Cannot move right, cell is not clear.");
            }
        } finally {
            classLock.unlock();
        }
    }
    
    
    // Overrides:
    
    /**
     * Overrides the default {@code toString} method to provide a string representation
     * of the vehicle object.
     *
     * @return a string representation of the vehicle object
     */
    @Override
    public String toString() {
    	
    	String passengers = "";
    	for(var passenger:this.listOfPassengers) {
    		passengers += passenger + " ";
    	}
    	
        return
                "Vehicle: " + this.vehicleId + "\n"
                        + "Manufacturer: " + this.manufacturer + "\n"
                        + "Model: " + this.model + "\n"
                        + "Date of purchase: " + (this.purchaseDate != null ? this.purchaseDate : MESSAGE_UNKNOWN) + "\n"
                        + "Purchase Price: " + this.purchasePrice + "\n"
                        + "Autonomy or Max Speed: " + this.autonomyOrMaxSpeed + "\n"
                        + "Max Speed: " + this.maxSpeed + "\n"
                        + "Description: " + this.description + "\n"
                        + "Type: " + this.type + "\n"
                        + this.battery + "\n"
                        + "Start Position X: " + this.startPositionX + "\n"
                        + "Start Position Y: " + this.startPositionY + "\n"
                        + "Current Position [X][Y]: " + "[" + this.positionX + "][" + this.positionY + "]" + "\n"
                        + "Destination Position X: " + this.destinationPositionX + "\n"
                        + "Destination Position Y: " + this.destinationPositionY + "\n"
                        + "Passenger: " + (this.listOfPassengers.isEmpty() == true ? MESSAGE_UNKNOWN : passengers)
                        + "\n";
    }

    @Override
    /**
     * Overrides the default {@code run} method to define the behavior of the vehicle
     * when it is running as a thread.
     */
    public void run() {
        // Check if the destination is the same as the current position
        if (this.positionX == this.destinationPositionX && this.positionY == this.destinationPositionY) {
        	System.out.println("EXITING run() METHOD FROM THE START ..\n");
            return; // No movement needed
        }

        // Calculate the total number of steps needed to reach the destination
        int totalStepsX = Math.abs(this.destinationPositionX - this.positionX);
        int totalStepsY = Math.abs(this.destinationPositionY - this.positionY);
        int totalSteps = totalStepsX + totalStepsY;
        System.out.println("Total Steps: " + totalSteps);

        // Ensure totalSteps is greater than zero to avoid division by zero
        if (totalSteps == 0) {
            System.err.println("Error: Total steps calculated to be zero.");
            return;
        }

        // Calculate the duration of each step in milliseconds
        long stepDuration = (this.duration * 1000) / totalSteps;
        System.out.println("stepDuration: " + stepDuration);
        
        int iWillDoTheCounting=0;
        
        // Move towards the destination
        try {
        	System.out.println("Vehicle: " + this.vehicleId);
            // Move on the X axis first
            while (this.positionX != this.destinationPositionX) {
                if (this.positionX < this.destinationPositionX) {
                	System.out.println("MOVING DOWN \n" + (++iWillDoTheCounting));
                	// System.out.println(JavaCityMap.map[this.positionX][this.positionY]);
                    moveDown();
                    this.battery.decreasing((double)MESSAGE_BATTERY_LEAK);
                } else {
                	System.out.println("MOVING UP \n" + (++iWillDoTheCounting));
                	// System.out.println(JavaCityMap.map[this.positionX][this.positionY]);
                    moveUp();
                    this.battery.decreasing((double)MESSAGE_BATTERY_LEAK);
                }
                Thread.sleep(stepDuration);
            }
            
            // Move on the Y axis next
            while (this.positionY != this.destinationPositionY) {
                if (this.positionY < this.destinationPositionY) {
                	System.out.println("MOVING RIGHT \n" +(++iWillDoTheCounting));
                	// System.out.println(JavaCityMap.map[this.positionX][this.positionY]);
                    moveRight();
                    this.battery.decreasing((double)MESSAGE_BATTERY_LEAK);
                } else {
                	System.out.println("MOVING LEFT \n" + (++iWillDoTheCounting));
                	// System.out.println(JavaCityMap.map[this.positionX][this.positionY]);
                    moveLeft();
                    this.battery.decreasing((double)MESSAGE_BATTERY_LEAK);
                }
                Thread.sleep(stepDuration);
            }
            
            //---------------------------------------------------------------------------------------------------------------
            double priceForThatType; // price for car/bike/scooter
            if(this.type.equals("automobil")) {
                priceForThatType = Double.parseDouble(configFileReader.getProperty("CAR_UNIT_PRICE"));
                RentalSalesMonitor.carRentalIncrement();
            }
            else if(this.type.equals("bicikl")) {
                priceForThatType = Double.parseDouble(configFileReader.getProperty("BIKE_UNIT_PRICE"));
                RentalSalesMonitor.bikeRentalIncrement();
            }
            else {
                priceForThatType = Double.parseDouble(configFileReader.getProperty("SCOOTER_UNIT_PRICE"));
                RentalSalesMonitor.scooterRentalIncrement();
            }

            double standardPrice = priceForThatType * stepDuration;

            boolean widerPartOfTheCityCheck = JavaCityMap.checkWidePartOfTheJavaCity(this.positionX, this.positionY);
            double priceForCityPart;

            if(widerPartOfTheCityCheck) {
                priceForCityPart = Double.parseDouble(configFileReader.getProperty("DISTANCE_WIDE"));
            } else {
                priceForCityPart = Double.parseDouble(configFileReader.getProperty("DISTANCE_NARROW"));
            }

            standardPrice *= priceForCityPart; // Iznos (osnovna cijena * udaljenost)

            double discountPrice = 0;
            double discountPromotionPrice = 0;

            if (this.type.equals("automobil") && this.isMalfunction()) {
                standardPrice = 0;
            } else {
                discountPrice = Double.parseDouble(configFileReader.getProperty("DISCOUNT"));
                RentalSalaryMonitor.discountSalary += standardPrice * discountPrice;

                if(Rental.RENTAL_COUNTER % 10 == 0) {
                    discountPromotionPrice = Double.parseDouble(configFileReader.getProperty("DISCOUNT_PROM"));
                    RentalSalaryMonitor.promotionSalary += standardPrice * discountPromotionPrice;
                }

                standardPrice = standardPrice - (standardPrice * discountPrice) - (standardPrice * discountPromotionPrice);
            }

            RentalSalaryMonitor.totalSalary += standardPrice;

            
            this.listOfPassengers.get(0).bill = new Bill(
            		standardPrice, 
            		this.type,
            		this.listOfPassengers.get(0),  
            		this.malfunction);
            //---------------------------------------------------------------------------------------------------------------
            
        } catch (InterruptedException ex) {
            System.err.println(ex);
            Thread.currentThread().interrupt(); // Restore the interrupt status
        } catch (IndexOutOfBoundsException ex) {
            System.err.println("Movement out of bounds: " + ex.getMessage());
        } finally {
            if (this.positionX == this.destinationPositionX && this.positionY == this.destinationPositionY) {
                JavaCityMap.clearCell(this.positionX, this.positionY);
            }
        }
    }
}