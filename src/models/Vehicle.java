package models;

import java.io.Serializable;

// import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import citymap.CityMap;
import user.*;

public abstract class Vehicle extends Thread implements Serializable{
	// system messages
	public static final String MESSAGE_LEFT = "Cannot move left. Position is out of bounds.";
	public static final String MESSAGE_RIGHT = "Cannot move right. Position is out of bounds.";
	public static final String MESSAGE_UP = "Cannot move upward. Position is out of bounds.";
	public static final String MESSAGE_DOWN = "Cannot move downward. Position is out of bounds.";
	
    String id;
    String manufacturer;					//	proizvodjac
    String model;
    Date purchaseDate;
    double purchasePrice;
    double autonomyOrMaxSpeed;				//	autonomija
    double maxSpeed;
    String description;
    String type;
    double currentBatteryLevel = 100; 		// samo priveremeno dok ne osmislim klasu baterija..
	
    //	vehicle map-coordinates
	int positionX;							//	rows
	int positionY;							//	columns
	int startingPositionX;
	int startingPositionY;
	int destinationPositionX;
	int destinationPositionY;
	
	public boolean rentFree=true;			//	mozda zatreba
	
	ArrayList<User> passengers = new ArrayList<>();
	int numberOfPassengers=0;
	
	// Constructor
	public Vehicle() {}
	
	// Constructor
	public Vehicle(
			String id, 
			String manufacturer, 
			String model,
			Date purchaseDate,
			double purchasePrice,
			double autonomyOrMaxSpeed,
			double maxSpeed,
			String description,
			String type
			) {
		this.id=id; 
		this.manufacturer=manufacturer; 
		this.model=model;
		this.purchaseDate=purchaseDate;
		this.purchasePrice=purchasePrice;
		this.autonomyOrMaxSpeed=autonomyOrMaxSpeed;
		this.maxSpeed=maxSpeed;
		this.description=description;
		this.type=type;
	}
	
	//	O SVIM OGRANICENJIMA MAPE -POBRINUCU SE U MAPI!!
	
	//  current position getters and setters 
	public int getPositionX() {return this.positionX;}
	public void setPositionX(int x) {this.positionX=x;}
	public int getPositionY() {return this.positionY;}
	public void setPositionY(int y) {this.positionY=y;}
	
	// public void setLocation(int x, int y) {	setPositionX(x); setPositionY(y); }
	//	getLocation()?
	
	// start-postion getters/setters -rename them!!
	public int getStartingPositionX() {return this.startingPositionX;}
	public void setStartingPositionX(int x) {this.startingPositionX=x;}
	public int getStartingPositionY() {return this.startingPositionY;}
	public void setStartingPositionY(int y) {this.startingPositionY=y;}
	
	// destination-position getters/setters -rename them!!
	public int getDestinationPositionX() {return this.destinationPositionX;}
	public void setDestinationPositionX(int x) {this.destinationPositionX=x;}
	public int getDestinationPositionY() {return this.destinationPositionY;}
	public void setDestinationPositionY(int y) {this.destinationPositionY=y;}
	
	
	// moving methods --just a basics for now, 	WATCH FOR INDEX OUT OF BOUNDS ERROR!!
	public void moveLeft() {
        if (this.positionX == 0) {
            throw new IndexOutOfBoundsException(MESSAGE_LEFT);
        }
        if (CityMap.isCellClear(this.positionX - 1, this.positionY)) {
            CityMap.clearCell(this.positionX, this.positionY);
            this.positionX--;
            CityMap.updateCell(this.positionX, this.positionY, this);
        }
    }

    public void moveRight() {
        if (this.positionX == CityMap.NUMBER_OF_COLUMNS - 1) {
            throw new IndexOutOfBoundsException(MESSAGE_RIGHT);
        }
        if (CityMap.isCellClear(this.positionX + 1, this.positionY)) {
            CityMap.clearCell(this.positionX, this.positionY);
            this.positionX++;
            CityMap.updateCell(this.positionX, this.positionY, this);
        }
    }

    public void moveUp() {
        if (this.positionY == 0) {
            throw new IndexOutOfBoundsException(MESSAGE_UP);
        }
        if (CityMap.isCellClear(this.positionX, this.positionY - 1)) {
            CityMap.clearCell(this.positionX, this.positionY);
            this.positionY--;
            CityMap.updateCell(this.positionX, this.positionY, this);
        }
    }

    public void moveDown() {
        if (this.positionY == CityMap.NUMBER_OF_ROWS - 1) {
            throw new IndexOutOfBoundsException(MESSAGE_DOWN);
        }
        if (CityMap.isCellClear(this.positionX, this.positionY + 1)) {
            CityMap.clearCell(this.positionX, this.positionY);
            this.positionY++;
            CityMap.updateCell(this.positionX, this.positionY, this);
        }
    }
	
	// add/remove passengers methods:
    public synchronized void addPassenger(User person) {this.passengers.add(person); }
    
    public synchronized void removePassenger(User person) {
        Iterator<User> iterator = this.passengers.iterator();
        while (iterator.hasNext()) {
            User p = iterator.next();
            if (p.getId() == person.getId()) { 	// Assuming getId() returns an int
                iterator.remove();
                break; 							// Assuming IDs are unique, we can exit the loop after removing the person
            }
        }
    }
     
    // Overrides ..
    @Override
    public void start() { 
    	System.out.println("Till I cry myself to sleep ...\n");
    }
    
	@Override
	public String toString() {
		return  
				"Vehicle: " + this.id + "\n" 
				+ "Manufacturer" + this.manufacturer + "\n"
				+ "Model: " + this.model + "\n"
				+ "Date of purchase: " + this.purchaseDate + "\n"
				+ "Purchase Price: " + this.purchasePrice + "\n"
				+ "Autonomy or Max Speed: " + this.autonomyOrMaxSpeed + "\n"
				+ "Max Speed: " + this.maxSpeed + "\n"
				+ "Description: " + this.description + "\n"
				+ "Type: " + this.type + "\n"
				+ "Battery Status: " + this.currentBatteryLevel + "\n";
	}
}
