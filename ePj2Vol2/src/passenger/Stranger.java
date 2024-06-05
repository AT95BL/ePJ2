package passenger;

import utility.*;

public class Stranger extends Passenger {
    private String passportNumber;

    // Constructor
    public Stranger(String id, String name, String address) {
        super(id, name, address);
        this.passportNumber = RandomStringGenerator.generateRandomString(11);
    }

    // Getter
    public String getPassportNumber() {
        return passportNumber;
    }
    
    @Override
    public String getIdentificationDocument() {
        return "Passport Number: " + passportNumber;
    }
    
    @Override
    public String toString() {
    	return super.toString() 
    			+ "\n"
    			+ "Passport Number: " + this.passportNumber;
    }
}
