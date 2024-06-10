package passenger;

import bill.Bill;
import utility.RandomStringGenerator;

import java.io.Serializable;

/**
 * @author AT95
 * @verison 1
 * The {@code Passenger} class represents a generic passenger in the transportation system.
 * It is an abstract class providing basic attributes and methods for passengers.
 */
public abstract class Passenger implements Serializable{
    protected String id;
    protected String name;
    protected String address;
    protected String drivingLicence;
    public Bill bill;
    
    /**
     * Constructs a new instance of {@code Passenger} with the specified details.
     * 
     * @param id the unique identifier of the passenger
     * @param name the name of the passenger
     * @param address the address of the passenger
     */
    public Passenger(String id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.drivingLicence = RandomStringGenerator.generateRandomString(9);
    }
    
    /**
     * Gets the unique identifier of the passenger.
     * 
     * @return the ID of the passenger
     */
    public String getId() {
        return id;
    }
    
    /**
     * Gets the name of the passenger.
     * 
     * @return the name of the passenger
     */
    public String getName() {
        return name;
    }
    
    /**
     * Gets the address of the passenger.
     * 
     * @return the address of the passenger
     */
    public String getAddress() {
        return address;
    }

    // Abstract method to be implemented by subclasses
    /**
     * Abstract method to be implemented by subclasses for returning the identification document of the passenger.
     * 
     * @return a string representing the identification document of the passenger
     */
    public abstract String getIdentificationDocument();
    
    /**
     * Returns a string representation of the passenger.
     * 
     * @return a string containing the details of the passenger
     */
    @Override
    public String toString() {
    	return "User: " + this.id + "\n"
    			+ "Name: " + this.name + "\n"
    			+ "Address: " + this.address;
    }
}
