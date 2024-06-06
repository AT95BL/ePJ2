package passenger;

import bill.Bill;
import utility.RandomStringGenerator;

public abstract class Passenger {
    protected String id;
    protected String name;
    protected String address;
    protected String drivingLicence;
    public Bill bill;
    
    // Constructor
    public Passenger(String id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.drivingLicence = RandomStringGenerator.generateRandomString(9);
    }
    
    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    // Abstract method to be implemented by subclasses
    public abstract String getIdentificationDocument();
    
    @Override
    public String toString() {
    	return "User: " + this.id + "\n"
    			+ "Name: " + this.name + "\n"
    			+ "Address: " + this.address;
    }
}
