package rental;

import java.util.Date;

import malfunction.Malfunction;
import model.Vehicle;

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
	
	// Constructor
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
    
	// Getter i setter metode za 'date'
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    //  Getter i setter metode za 'userName'		
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    // Getter i setter metode za 'vehicleId'	
    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    // Getter i setter metode za 'startLocation'
    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    // Getter i setter metode za 'endLocation'
    public String getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    // Getter i setter metode za 'promotion'
    public boolean isPromotion() {
        return promotion;
    }

    public void setPromotion(boolean promotion) {
        this.promotion = promotion;
    }

    public int getVehicleStartPositionX() {
        return vehicleStartPositionX;
    }

    public void setVehicleStartPositionX(int vehicleStartPositionX) {
        this.vehicleStartPositionX = vehicleStartPositionX;
    }

    // Getter i setter metode za 'vehicleStartPositionY'
    public int getVehicleStartPositionY() {
        return vehicleStartPositionY;
    }

    public void setVehicleStartPositionY(int vehicleStartPositionY) {
        this.vehicleStartPositionY = vehicleStartPositionY;
    }

    // Getter i setter metode za 'vehicleDestinationPositionX'
    public int getVehicleDestinationPositionX() {
        return vehicleDestinationPositionX;
    }

    public void setVehicleDestinationPositionX(int vehicleDestinationPositionX) {
        this.vehicleDestinationPositionX = vehicleDestinationPositionX;
    }

    // Getter i setter metode za 'vehicleDestinationPositionY'
    public int getVehicleDestinationPositionY() {
        return vehicleDestinationPositionY;
    }

    public void setVehicleDestinationPositionY(int vehicleDestinationPositionY) {
        this.vehicleDestinationPositionY = vehicleDestinationPositionY;
    }
    
    public long getDuration() {
    	return this.duration;
    }
    
    public void setDuration(long duration) {
    	this.duration=duration;
    }
    
    // Getter i setter metode za 'malfunction'
    public boolean isMalfunction() {
        return malfunction;
    }

    public void setMalfunction(boolean malfunction) {
        this.malfunction = malfunction;
    }
    							
    public Malfunction getmalfunctionModel() {
    	return this.malfunctionModel;
    }
    
    public void setMalfunctionModel(Malfunction malfunctionModel) {
    	this.malfunctionModel = malfunctionModel;
    }
    
    //	maybe useless
    public Malfunction createMalfunctionModel(String message, Date date, Vehicle vehicle) {
    	return new Malfunction(message, date, vehicle);
    }
	
    //@Overrides
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
