package person;

public class Local extends Person {
    private String nationalId;

    // Constructor
    public Local(String id, String name, String address, String nationalId) {
        super(id, name, address);
        this.nationalId = nationalId;
    }

    @Override
    public String getIdentificationDocument() {
        return "National ID: " + nationalId;
    }
    
    // Getter
    public String getNationalId() {
        return nationalId;
    }
}
