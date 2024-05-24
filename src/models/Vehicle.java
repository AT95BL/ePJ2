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
	private static final String MESSAGE_LEFT = "Cannot move left. Position is out of bounds.";
	private static final String MESSAGE_RIGHT = "Cannot move right. Position is out of bounds.";
	private static final String MESSAGE_UP = "Cannot move upward. Position is out of bounds.";
	private static final String MESSAGE_DOWN = "Cannot move downward. Position is out of bounds.";
	
    public String id;
    public String manufacturer;						//	proizvodjac
    public String model;
    public Date purchaseDate;
    public double purchasePrice;
    public double autonomyOrMaxSpeed;				//	autonomija
    public double maxSpeed;
    public String description;
    public String type;
    public double currentBatteryLevel = 100; 		// samo priveremeno dok ne osmislim klasu baterija..
	
    //	vehicle map-coordinates
	int positionX;							//	rows
	int positionY;							//	columns
	int startingPositionX;
	int startingPositionY;
	int destinationPositionX;
	int destinationPositionY;
	
	public Move moveDirection;						//	move-direction
	
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
	
	//  vehicle current position getters and setters 
	public int getPositionX() {return this.positionX;}
	public void setPositionX(int x) {this.positionX=x;}
	public int getPositionY() {return this.positionY;}
	public void setPositionY(int y) {this.positionY=y;}
	
	// 	public void setLocation(int x, int y) {	setPositionX(x); setPositionY(y); }
	//	getLocation()?
	
	// vehicle start-postion getters/setters -rename them!!
	public int getStartingPositionX() {return this.startingPositionX;}
	public void setStartingPositionX(int x) {this.startingPositionX=x;}
	public int getStartingPositionY() {return this.startingPositionY;}
	public void setStartingPositionY(int y) {this.startingPositionY=y;}
	
	// vehicle destination-position getters/setters -rename them!!
	public int getDestinationPositionX() {return this.destinationPositionX;}
	public void setDestinationPositionX(int x) {this.destinationPositionX=x;}
	public int getDestinationPositionY() {return this.destinationPositionY;}
	public void setDestinationPositionY(int y) {this.destinationPositionY=y;}
	
	
	// moving methods --just a basics for now, 	WATCH FOR INDEX OUT OF BOUNDS ERROR!!
	public synchronized void moveLeft() {
        if (this.positionX == 0) {
            throw new IndexOutOfBoundsException(MESSAGE_LEFT);
        }
        if (CityMap.isCellClear(this.positionX - 1, this.positionY)) {
            CityMap.clearCell(this.positionX, this.positionY);
            this.positionX--;
            CityMap.updateCell(this.positionX, this.positionY, this);
        }
    }

    public synchronized void moveRight() {
        if (this.positionX == CityMap.NUMBER_OF_COLUMNS - 1) {
            throw new IndexOutOfBoundsException(MESSAGE_RIGHT);
        }
        if (CityMap.isCellClear(this.positionX + 1, this.positionY)) {
            CityMap.clearCell(this.positionX, this.positionY);
            this.positionX++;
            CityMap.updateCell(this.positionX, this.positionY, this);
        }
    }

    public synchronized void moveUp() {
        if (this.positionY == 0) {
            throw new IndexOutOfBoundsException(MESSAGE_UP);
        }
        if (CityMap.isCellClear(this.positionX, this.positionY - 1)) {
            CityMap.clearCell(this.positionX, this.positionY);
            this.positionY--;
            CityMap.updateCell(this.positionX, this.positionY, this);
        }
    }

    public synchronized void moveDown() {
        if (this.positionY == CityMap.NUMBER_OF_ROWS - 1) {
            throw new IndexOutOfBoundsException(MESSAGE_DOWN);
        }
        if (CityMap.isCellClear(this.positionX, this.positionY + 1)) {
            CityMap.clearCell(this.positionX, this.positionY);
            this.positionY++;
            CityMap.updateCell(this.positionX, this.positionY, this);
        }
    }
    
    public synchronized void move(int x1, int y1, int x2, int y2) {
        
    	/*
    	if (!CityMap.arePointsOpposite(x1, y1, x2, y2)) {
            throw new IllegalArgumentException("Tačke nisu jedna naspram druge!");
            
            Mislio sam da se vozila smiju kretati iskljucivo H/V tako da sam dijagonalne 
            putanje u prvoj verziji aplikacije tretirao kao izuzetke..
        }
        */

        // Postavljanje početne pozicije vozila, višak?
        this.positionX = x1;
        this.positionY = y1;

        // Horizontalno kretanje
        while (this.positionX != x2) {
            if (x2 > this.positionX) {
                moveRight();
            } else {
                moveLeft();
            }
        }

        // Vertikalno kretanje
        while (this.positionY != y2) {
            if (y2 > this.positionY) {
                moveDown();
            } else {
                moveUp();
            }
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
    public void run() {
        // Odredišnoj tački je stiglo vozilo, ne treba se kretati
        if (positionX == destinationPositionX && positionY == destinationPositionY) {
            System.out.println("Vozilo " + id + " je već stiglo na odredišnu tačku.");
            return;
        }

        // Pokretanje vozila od početne tačke prema odredišnoj tački tokom određenog vremena
        int remainingDuration = duration;
        while (remainingDuration > 0) {
            // Kretnja vozila prema odredišnoj tački
            move(startingPositionX, startingPositionY, destinationPositionX, destinationPositionY);

            // Smanjenje preostalog trajanja za jednu sekundu
            remainingDuration--;

            // Pauza od jedne sekunde prije nastavka kretanja
            try {
                Thread.sleep(1000); // Spavanje od jedne sekunde
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Vozilo " + id + " je stiglo na odredišnu tačku.");
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
	
	// getters && setters
	public String getID() {return this.id;}
	
	
	// nove ideje
	int duration;
	public void setDuration(int duration) {this.duration=duration;}
}
