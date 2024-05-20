package models;

import java.io.Serializable;

// import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import simulation.Simulation; // because of Simulation.map
import person.*;

public abstract class Vehicle implements Serializable{
    String id;
    String manufacturer;
    String model;
    Date purchaseDate;
    double purchasePrice;
    double autonomyOrMaxSpeed;
    double maxSpeed;
    String description;
    String type;
    double currentBatteryLevel = 100; // samo priveremeno dok ne osmislim klasu baterija..
	
	int positionX;	//	rows
	int positionY;	//	columns
	
	public static final String MESSAGE_LEFT = "Cannot move left. Position is out of bounds.";
	public static final String MESSAGE_RIGHT = "Cannot move right. Position is out of bounds.";
	public static final String MESSAGE_UP = "Cannot move upward. Position is out of bounds.";
	public static final String MESSAGE_DOWN = "Cannot move downward. Position is out of bounds.";
	
	public boolean rentFree=true;
	
	ArrayList<Person> passengers = new ArrayList<>();
	
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
	
	// getters and setters
	public int getPositionX() {return this.positionX;}
	public void setPositionX(int x) {this.positionX=x;}
	public int getPositionY() {return this.positionY;}
	public void setPositionY(int y) {this.positionY=y;}
	public void setLocation(int x, int y) {	setPositionX(x); setPositionY(y); }
	//	getLocation()?
	
	// moving methods --just a basics for now, 	WATCH FOR INDEX OUT OF BOUNDS ERROR!!
	public void moveLeft() {
        if (this.positionX == 0) {
            throw new IndexOutOfBoundsException(MESSAGE_LEFT);
        }
        if (Simulation.isCellClear(this.positionX - 1, this.positionY)) {
            Simulation.clearCell(this.positionX, this.positionY);
            this.positionX--;
            Simulation.updateCell(this.positionX, this.positionY, this);
        }
    }

    public void moveRight() {
        if (this.positionX == Simulation.NUMBER_OF_COLUMNS - 1) {
            throw new IndexOutOfBoundsException(MESSAGE_RIGHT);
        }
        if (Simulation.isCellClear(this.positionX + 1, this.positionY)) {
            Simulation.clearCell(this.positionX, this.positionY);
            this.positionX++;
            Simulation.updateCell(this.positionX, this.positionY, this);
        }
    }

    public void moveUp() {
        if (this.positionY == 0) {
            throw new IndexOutOfBoundsException(MESSAGE_UP);
        }
        if (Simulation.isCellClear(this.positionX, this.positionY - 1)) {
            Simulation.clearCell(this.positionX, this.positionY);
            this.positionY--;
            Simulation.updateCell(this.positionX, this.positionY, this);
        }
    }

    public void moveDown() {
        if (this.positionY == Simulation.NUMBER_OF_ROWS - 1) {
            throw new IndexOutOfBoundsException(MESSAGE_DOWN);
        }
        if (Simulation.isCellClear(this.positionX, this.positionY + 1)) {
            Simulation.clearCell(this.positionX, this.positionY);
            this.positionY++;
            Simulation.updateCell(this.positionX, this.positionY, this);
        }
    }
	
	// add/remove passenger methods
    public void addPassenger(Person person) {this.passengers.add(person); }
    
    public synchronized void removePassenger(Person person) {
        Iterator<Person> iterator = this.passengers.iterator();
        while (iterator.hasNext()) {
            Person p = iterator.next();
            if (p.getId() == person.getId()) { // Assuming getId() returns an int
                iterator.remove();
                break; // Assuming IDs are unique, we can exit the loop after removing the person
            }
        }
    }
    
    
    // Overrides ..
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
