package person;

public abstract class Person {
    protected String id;
    protected String name;
    protected String address;
    
    // Constructor
    public Person(String id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
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
}
