package user;

public class Stranger extends User {
    private String passportNumber;

    // Constructor
    public Stranger(String id, String name, String address, String passportNumber) {
        super(id, name, address);
        this.passportNumber = passportNumber;
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
