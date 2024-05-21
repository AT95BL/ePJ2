package user;

public class Stranger extends User {
    private String passportNumber;

    // Constructor
    public Stranger(String id, String name, String address, String passportNumber) {
        super(id, name, address);
        this.passportNumber = passportNumber;
    }

    @Override
    public String getIdentificationDocument() {
        return "Passport Number: " + passportNumber;
    }
    
    // Getter
    public String getPassportNumber() {
        return passportNumber;
    }
}
