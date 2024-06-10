package rental;

import java.util.Date;

import malfunction.Malfunction;
import model.Vehicle;

/**
 * @author AT95
 * @version 1
 * The {@code Rental} class represents a rental transaction of a vehicle by a user.
 * It contains information about the rental such as the date, user name, vehicle ID, start and end locations,
 * duration, malfunction status, and promotion status.
 */
public class Rental {
	public static long RENTAL_COUNTER = 1; // every 10th gets a discount
	// Attributes according to the text of the given task
	public Date date;				//	1	DATUM IZNAJMLJIVANJA
	public String userName; 		//	2 	korisnikovo ime
	public String vehicleId;		//	3
	public String startLocation;	//	4	ODNOSI SE NA VOZILO 
	public String endLocation;		//	5	VOZILO SE NA VOZILO
	public long duration;			//	6
	public boolean malfunction;		// 	7	ODNOSI SE NA VOZILO 
	public boolean promotion;		//	8	ODNOSI SE NA VOZILO 
	
	// will be parsed separately
	public int vehicleStartPositionX;
	public int vehicleStartPositionY;
	public int vehicleDestinationPositionX;
	public int vehicleDestinationPositionY;
	
	public Malfunction malfunctionModel=null;
	
	// Default Constructor
	public Rental() {}
	
	/**
     * Constructs a new instance of {@code Rental} with the specified details.
     * 
     * @param date the date of the rental
     * @param userName the name of the user renting the vehicle
     * @param vehicleId the ID of the rented vehicle
     * @param startLocation the starting location of the rental
     * @param endLocation the ending location of the rental
     * @param duration the duration of the rental
     * @param malfunction the malfunction status of the vehicle during the rental
     * @param promotion the promotion status of the rental
     * @throws IllegalArgumentException if the start or end location is invalid
     */
	public Rental(
            Date date, 
            String userName, 
            String vehicleId, 
            String startLocation, 
            String endLocation, 
            long duration, 
            boolean malfunction, 
            boolean promotion) throws IllegalArgumentException {
        this.date = date;
        this.userName = userName;
        this.vehicleId = vehicleId;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.duration = duration;
        this.malfunction = malfunction;
        this.promotion = promotion;
        
        // Validacija i parsiranje koordinata
        int[] startCoords = parseCoordinate(startLocation, "start");
        this.vehicleStartPositionX = startCoords[0];
        this.vehicleStartPositionY = startCoords[1];
        
        int[] endCoords = parseCoordinate(endLocation, "end");
        this.vehicleDestinationPositionX = endCoords[0];
        this.vehicleDestinationPositionY = endCoords[1];
    }
	
	/**
	 * Parses the coordinate string into X and Y coordinates.
	 * 
	 * @param location the location string to be parsed
	 * @param type the type of location (start or end)
	 * @return an array containing the X and Y coordinates
	 * @throws IllegalArgumentException if the location string is invalid or cannot be parsed
	 */
    public int[] parseCoordinate(String location, String type) throws IllegalArgumentException {
        String[] coords = location.split(",");
        if (coords.length != 2) {
            throw new IllegalArgumentException("Invalid " + type + " location: " + location);
        }
        try {
            int x = Integer.parseInt(coords[0].trim());
            int y = Integer.parseInt(coords[1].trim());
            return new int[] {x, y};
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid coordinates in " + type + " location: " + location, e);
        }
    }
    
    
	// get/set methods: 
    // ZNAČI, objekt-RENTAL-klase MOŽE DA PODEŠAVA ATRIBUTE ZA objekt-VEHICLE-klase!!
    
    /**
     * Returns the date of the rental.
     * 
     * @return the date of the rental
     */
    public Date getDate() {
        return date;
    }
    
    /**
     * Sets the date of the rental.
     * 
     * @param date the date of the rental
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Returns the name of the user.
     * 
     * @return the name of the user
     */		
    public String getUserName() {
        return userName;
    }
    
    /**
     * Sets the name of the user.
     * 
     * @param userName the name of the user
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Returns the ID of the vehicle.
     * 
     * @return the ID of the vehicle
     */	
    public String getVehicleId() {
        return vehicleId;
    }
    
    /**
     * Sets the ID of the vehicle.
     * 
     * @param vehicleId the ID of the vehicle
     */
    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    /**
     * Returns the start location of the rental.
     * 
     * @return the start location of the rental
     */
    public String getStartLocation() {
        return startLocation;
    }
    
    /**
     * Sets the start location of the rental.
     * 
     * @param startLocation the start location of the rental
     */
    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    /**
     * Returns the end location of the rental.
     * 
     * @return the end location of the rental
     */
    public String getEndLocation() {
        return endLocation;
    }
    
    /**
     * Sets the end location of the rental.
     * 
     * @param endLocation the end location of the rental
     */
    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    /**
     * Checks if the rental has a promotion.
     * 
     * @return true if the rental has a promotion, otherwise false
     */
    public boolean isPromotion() {
        return promotion;
    }
    
    /**
     * Sets the promotion status of the rental.
     * 
     * @param promotion true if the rental has a promotion, otherwise false
     */
    public void setPromotion(boolean promotion) {
        this.promotion = promotion;
    }
    
    /**
     * Returns the X coordinate of the vehicle's start position.
     * 
     * @return the X coordinate of the vehicle's start position
     */
    public int getVehicleStartPositionX() {
        return vehicleStartPositionX;
    }
    
    /**
     * Sets the X coordinate of the vehicle's start position.
     * 
     * @param vehicleStartPositionX the X coordinate of the vehicle's start position
     */
    public void setVehicleStartPositionX(int vehicleStartPositionX) {
        this.vehicleStartPositionX = vehicleStartPositionX;
    }

    /**
     * Returns the Y coordinate of the vehicle's start position.
     * 
     * @return the Y coordinate of the vehicle's start position
     */
    public int getVehicleStartPositionY() {
        return vehicleStartPositionY;
    }
    
    /**
     * Sets the Y coordinate of the vehicle's start position.
     * 
     * @param vehicleStartPositionY the Y coordinate of the vehicle's start position
     */
    public void setVehicleStartPositionY(int vehicleStartPositionY) {
        this.vehicleStartPositionY = vehicleStartPositionY;
    }

    /**
     * Returns the X coordinate of the vehicle's destination position.
     * 
     * @return the X coordinate of the vehicle's destination position
     */
    public int getVehicleDestinationPositionX() {
        return vehicleDestinationPositionX;
    }
    
    /**
     * Sets the X coordinate of the vehicle's destination position.
     * 
     * @param vehicleDestinationPositionX the X coordinate of the vehicle's destination position
     */
    public void setVehicleDestinationPositionX(int vehicleDestinationPositionX) {
        this.vehicleDestinationPositionX = vehicleDestinationPositionX;
    }

    /**
     * Returns the Y coordinate of the vehicle's destination position.
     * 
     * @return the Y coordinate of the vehicle's destination position
     */
    public int getVehicleDestinationPositionY() {
        return vehicleDestinationPositionY;
    }
    
    /**
     * Sets the Y coordinate of the vehicle's destination position.
     * 
     * @param vehicleDestinationPositionY the Y coordinate of the vehicle's destination position
     */
    public void setVehicleDestinationPositionY(int vehicleDestinationPositionY) {
        this.vehicleDestinationPositionY = vehicleDestinationPositionY;
    }
    
    /**
     * Returns the duration of the rental.
     * 
     * @return the duration of the rental
     */
    public long getDuration() {
    	return this.duration;
    }
    
    /**
     * Sets the duration of the rental.
     * 
     * @param duration the duration of the rental
     */
    public void setDuration(long duration) {
    	this.duration=duration;
    }
    
    /**
     * Checks if the vehicle experienced a malfunction during the rental.
     * 
     * @return true if the vehicle experienced a malfunction, otherwise false
     */
    public boolean isMalfunction() {
        return malfunction;
    }
    
    /**
     * Sets the malfunction status of the vehicle during the rental.
     * 
     * @param malfunction true if the vehicle experienced a malfunction, otherwise false
     */
    public void setMalfunction(boolean malfunction) {
        this.malfunction = malfunction;
    }
    
    /**
     * Returns the malfunction model associated with the rental.
     * 
     * @return the malfunction model associated with the rental
     */
    public Malfunction getmalfunctionModel() {
    	return this.malfunctionModel;
    }
    
    /**
     * Sets the malfunction model associated with the rental.
     * 
     * @param malfunctionModel the malfunction model associated with the rental
     */
    public void setMalfunctionModel(Malfunction malfunctionModel) {
    	this.malfunctionModel = malfunctionModel;
    }
    
    /**
     * Creates a new malfunction model with the given details.
     * 
     * @param message the message describing the malfunction
     * @param date the date when the malfunction occurred
     * @param vehicle the vehicle associated with the malfunction
     * @return a new malfunction model
     */
    public Malfunction createMalfunctionModel(String message, Date date, Vehicle vehicle) {
    	return new Malfunction(message, date, vehicle);
    }
	
    //@Overrides
    
    /**
     * Returns a string representation of the rental.
     * 
     * @return a string representation of the rental
     */
    @Override
    public String toString() {
        return "Rental{" +
                "date=" + date +
                ", user='" + userName + '\'' +
                ", vehicleId='" + vehicleId + '\'' +
                ", startLocation='" + startLocation + '\'' +
                ", endLocation='" + endLocation + '\'' +
                ", duration=" + duration +
                ", malfunction=" + malfunction +
                ", promotion=" + promotion +
                ", startX=" + vehicleStartPositionX +
                ", startY=" + vehicleStartPositionY +
                ", destinationX=" + vehicleDestinationPositionX +
                ", destinationY=" + vehicleDestinationPositionY +
                '}';
    }
}
