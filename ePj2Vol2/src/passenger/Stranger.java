package passenger;

import utility.*;

/**
 * @author AT95
 * @version 1
 * The {@code Stranger} class represents a passenger whose identity is not known.
 * It extends the {@code Passenger} class and provides specific attributes and methods for strangers.
 */
public class Stranger extends Passenger {
    private String passportNumber;

    /**
     * Constructs a new instance of {@code Stranger} with the specified details.
     * 
     * @param id the unique identifier of the stranger
     * @param name the name of the stranger
     * @param address the address of the stranger
     */
    public Stranger(String id, String name, String address) {
        super(id, name, address);
        this.passportNumber = RandomStringGenerator.generateRandomString(11);
    }

    /**
     * Gets the passport number of the stranger.
     * 
     * @return the passport number of the stranger
     */
    public String getPassportNumber() {
        return passportNumber;
    }
    
    /**
     * Returns the identification document of the stranger.
     * 
     * @return a string representing the passport number of the stranger
     */
    @Override
    public String getIdentificationDocument() {
        return "Passport Number: " + passportNumber;
    }
    
    /**
     * Returns a string representation of the stranger.
     * 
     * @return a string containing the details of the stranger
     */
    @Override
    public String toString() {
    	return super.toString() 
    			+ "\n"
    			+ "Passport Number: " + this.passportNumber;
    }
}
