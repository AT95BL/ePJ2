package passenger;

import utility.*;

public class Local extends Passenger {
    private String nationalId;

    // Constructor
    public Local(String id, String name, String address) {
        super(id, name, address);
        this.nationalId = RandomStringGenerator.generateRandomString(10);
    }
    
    // Getter
    public String getNationalId() {
        return nationalId;
    }
    
    @Override
    public String getIdentificationDocument() {
        return "National ID: " + nationalId;
    }
    
    @Override
    public String toString() {
    	return super.toString() 
    			+ "\n"
    			+ "National ID: " + this.nationalId;
    }
}
