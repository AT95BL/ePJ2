package malfunction;

import java.util.Date;

import model.Vehicle;

/**
 * @author AT95
 * @version 1
 * The {@code Malfunction} class represents a malfunction event that occurs in a vehicle.
 * It includes details about the malfunction description, the date it happened, and the
 * vehicle that is broken.
 * 
 * <p>
 * Example usage:
 * <pre>
 * {@code
 * Vehicle vehicle = new Vehicle();
 * Malfunction malfunction = new Malfunction("Engine failure", new Date(), vehicle);
 * System.out.println(malfunction);
 * }
 * </pre>
 * </p>
 * 
 * @see Vehicle
 */
public class Malfunction {
	public static final String MALFUNCTION_MESSAGE = "MALFUNCTION OCCURED!!";
	
	public String malfunctionDescription;	//	opis kvara
	public Date malfunctionHappenDate;		//	datum javljanja-detekcije kvara
	public Vehicle brokenVehicle;			//	pokvareno vozilo
	
	/**
     * Constructs a new {@code Malfunction} with default values.
     * All fields are initialized to {@code null}.
     */
	public Malfunction() {
		this.malfunctionDescription=null;
		this.malfunctionHappenDate=null;
		this.brokenVehicle=null;
	}
	
	/**
     * Constructs a new {@code Malfunction} with the specified description, date, and vehicle.
     * 
     * @param malfunctionDescription the description of the malfunction
     * @param malfunctionHappenDate the date the malfunction was detected
     * @param brokenVehicle the vehicle that is broken
     */
	public Malfunction(String malfunctionDescription, Date malfunctionHappenDate, Vehicle brokenVehicle) {
		this.malfunctionDescription=malfunctionDescription;
		this.malfunctionHappenDate=malfunctionHappenDate;
		this.brokenVehicle=brokenVehicle;
	}
	
	// getters/setters
	
	/**
     * Sets the description of the malfunction.
     * 
     * @param malfunctionDescription the description of the malfunction
     */
    public void setMalfunctionDescription(String malfunctionDescription) {
        this.malfunctionDescription = malfunctionDescription;
    }

    /**
     * Returns the date the malfunction was detected.
     * 
     * @return the date the malfunction was detected
     */
    public Date getMalfunctionHappenDate() {
        return malfunctionHappenDate;
    }

    /**
     * Sets the date the malfunction was detected.
     * 
     * @param malfunctionHappenDate the date the malfunction was detected
     */
    public void setMalfunctionHappenDate(Date malfunctionHappenDate) {
        this.malfunctionHappenDate = malfunctionHappenDate;
    }

    /**
     * Returns the vehicle that is broken.
     * 
     * @return the vehicle that is broken
     */
    public Vehicle getBrokenVehicle() {
        return brokenVehicle;
    }

    /**
     * Sets the vehicle that is broken.
     * 
     * @param brokenVehicle the vehicle that is broken
     */
    public void setBrokenVehicle(Vehicle brokenVehicle) {
        this.brokenVehicle = brokenVehicle;
    }
	
    /**
     * Returns a string representation of the malfunction.
     * 
     * @return a string representation of the malfunction
     */
    
	@Override
	public String toString() {
		return "Malfunction Description: " + this.malfunctionDescription + "\n"
				+ "Date Occured: " + this.malfunctionHappenDate + "\n"
				+ this.brokenVehicle;
	}
}

