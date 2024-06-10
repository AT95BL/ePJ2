package passenger;

import utility.*;

/**
 * @author AT95
 * @version 1
 * The {@code Local} class represents a local passenger in the transportation system.
 * It extends the {@code Passenger} class and provides specific attributes and methods for local passengers.
 */
public class Local extends Passenger {
    private String nationalId;

    /**
     * Constructs a new instance of {@code Local} with the specified details.
     * 
     * @param id the unique identifier of the passenger
     * @param name the name of the passenger
     * @param address the address of the passenger
     */
    public Local(String id, String name, String address) {
        super(id, name, address);
        this.nationalId = RandomStringGenerator.generateRandomString(10);
    }
    
    /**
     * Gets the national ID of the local passenger.
     * 
     * @return the national ID
     */
    public String getNationalId() {
        return nationalId;
    }
    
    /**
     * Returns the identification document of the local passenger.
     * 
     * @return a string representing the national ID of the passenger
     */
    @Override
    public String getIdentificationDocument() {
        return "National ID: " + nationalId;
    }
    
    /**
     * Returns a string representation of the local passenger.
     * 
     * @return a string containing the details of the local passenger
     */
    @Override
    public String toString() {
    	return super.toString() 
    			+ "\n"
    			+ "National ID: " + this.nationalId;
    }
}
