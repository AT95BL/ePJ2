package model;

// std java library
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;

import java.util.concurrent.locks.ReentrantLock;

// user library
import user.*;
import battery.*;
import javacitymap.JavaCityMap;
import malfunction.*;

public abstract class Vehicle extends Thread implements Serializable {
    // system messages --NE OBRAĆAJ PAŽNJU!!
    private static final String MESSAGE_LEFT = "Cannot move left. Position is out of bounds.";
    private static final String MESSAGE_RIGHT = "Cannot move right. Position is out of bounds.";
    private static final String MESSAGE_UP = "Cannot move upward. Position is out of bounds.";
    private static final String MESSAGE_DOWN = "Cannot move downward. Position is out of bounds.";
    private static final String MESSAGE_UNKNOWN = "UNKNOWN";
    private static final int	MESSAGE_BATTERY_LEAK = 2;

    // class attributes according to the given-task
    public String vehicleId;                // 1
    public String manufacturer;                // 2 proizvodjac
    public String model;                    // 3 model of car/bike/scooter
    public Date purchaseDate;                // 4 datum nabavke
    public double purchasePrice;            // 5 cijena nabavke
    public double autonomyOrMaxSpeed;        // 6 domet sa jednim punjenjem -autonomija
    public double maxSpeed;                    // 7
    public String description;                // 8 opis
    public String type;                        // 9 tekstualni opis tipa --> car/bike/scooter
    public Battery battery;                    // baterija -status, kapacitet
    public boolean malfunction = false;        // malfunction detector
    public Malfunction malfunctionModel = null;    // malfunction model

    long duration;                            // VRIJEME TRAJANJA KRETANJA A->B

    // vehicle map-coordinates/coordinating
    int positionX;                                        // row
    int positionY;                                        // column
    int startPositionX;                                    // start row
    int startPositionY;                                    // start column
    int destinationPositionX;                            // final row
    int destinationPositionY;                            // final column

    // passengers info
    public int numberOfPassengers;
    public List<User> listOfPassengers = new ArrayList<>();
    
    private static final ReentrantLock classLock = new ReentrantLock();
    
    // Default Constructor
    public Vehicle() {}

    // Constructor
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

    // get/set methods for class attributes ..
    // Getter i setter metode za 'id'
    public String getVehicleId() {
        return this.vehicleId;
    }

    public void setVehicleId(String id) {
        this.vehicleId = id;
    }

    // Getter i setter metode za 'manufacturer'
    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    // Getter i setter metode za 'model'
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    // Getter i setter metode za 'purchaseDate'
    public Date getpurchaseDate() {
        return purchaseDate;
    }

    public void setpurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    // Getter i setter metode za 'purchasePrice'
    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    // Getter i setter metode za 'autonomyOrMaxSpeed'
    public double getAutonomyOrMaxSpeed() {
        return autonomyOrMaxSpeed;
    }

    public void setAutonomyOrMaxSpeed(double autonomyOrMaxSpeed) {
        this.autonomyOrMaxSpeed = autonomyOrMaxSpeed;
    }

    // Getter i setter metode za 'maxSpeed'
    public double getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    // Getter i setter metode za 'description'
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Getter i setter metode za 'type'
    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    // Getter i setter metode za 'battery'
    public Battery getBattery() {
        return this.battery;
    }

    public void setBattery(Battery battery) {
        this.battery = battery;
    }

    // Getter i setter metode za 'malfunction'
    public boolean isMalfunction() {
        return malfunction;
    }

    public void setMalfunction(boolean malfunctione) {
        this.malfunction = malfunctione;
    }

    // Getter i setter metode za 'MalfunctionModel'
    public Malfunction getMalfunctionModel() {
        return this.malfunctionModel;
    }

    public void setMalfunctionModel(Malfunction malfunctionModel) {
        this.malfunctionModel = malfunctionModel;
    }

    // Getter i setter metode za 'positionX'
    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    // Getter i setter metode za 'positionY'
    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    // Getter i setter metode za 'startPositionX'
    public int getStartPositionX() {
        return startPositionX;
    }

    public void setStartPositionX(int startPositionX) {
        this.startPositionX = startPositionX;
    }

    // Getter i setter metode za 'startPositionY'
    public int getStartPositionY() {
        return startPositionY;
    }

    public void setStartPositionY(int startPositionY) {
        this.startPositionY = startPositionY;
    }

    // Getter i setter metode za 'destinationPositionX'
    public int getDestinationPositionX() {
        return destinationPositionX;
    }

    public void setDestinationPositionX(int destinationPositionX) {
        this.destinationPositionX = destinationPositionX;
    }

    // Getter i setter metode za 'destinationPositionY'
    public int getDestinationPositionY() {
        return destinationPositionY;
    }

    public void setDestinationPositionY(int destinationPositionY) {
        this.destinationPositionY = destinationPositionY;
    }

    // Getter i setter metode za 'numberOfPassengers'
    public int getNumberOfPassengers() {
        return numberOfPassengers;
    }

    public void setNumberOfPassengers(int numberOfPassengers) {
        this.numberOfPassengers = numberOfPassengers;
    }

    // Getter i setter metode za 'listOfPassengers'
    public List<User> getListOfPassengers() {
        return listOfPassengers;
    }

    public void setListOfPassengers(List<User> listOfPassengers) {
        this.listOfPassengers = listOfPassengers;
    }

    public long getDuration() {
        return this.duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
    
    /*
    public synchronized void moveDown() {
            if (this.positionX == JavaCityMap.NUMBER_OF_ROWS - 1) { // check if the vehicle is already on the far left
                throw new IndexOutOfBoundsException(MESSAGE_DOWN);
            }
            if (JavaCityMap.isCellClear(this.positionX + 1, this.positionY)) { // synchronized?
                JavaCityMap.clearCell(this.positionX, this.positionY);
                this.positionX++;
                JavaCityMap.updateCell(this.positionX, this.positionY, this); // move the vehicle
            } else {
                System.err.println("Cannot move down, cell is not clear.");
            }
    }

    public synchronized void moveUp() {
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
    }

    public synchronized void moveLeft() {
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
    }

    public synchronized void moveRight() {

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
    }
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
    @Override
    public String toString() {
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
                        + "\n";
    }

    @Override
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
