package battery;

public class Battery {
    
    private static final String MAXIMUM_CAPACITY_MESSAGE = "Battery capacity: " + "\n" + 
                                                        "100 for Scooters" + "\n" +
                                                        "200 for Bikes" + "\n" +
                                                        "300 for Cars" + "\n";
    
    private int capacity;       // maximum capacity, for now is 100/200/300 scooter/bike/car
    private double status = 100;    // battery percentage, let's say it's fully charged
    
    // Constructor
    public Battery(int capacity) throws BatteryException {
        if (capacity != 100 && capacity != 200 && capacity != 300) {
            throw new BatteryException(MAXIMUM_CAPACITY_MESSAGE);
        }
        this.capacity = capacity;
    }
    
    // Method to charge the battery
    public void charging(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Charging amount cannot be negative.");
        }
        this.status += amount;
        if (this.status > 100) {
            this.status = 100; // Ensures battery status does not exceed 100%
        }
    }
    
    // Method to decrease the battery level
    public void decreasing(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Decreasing amount cannot be negative.");
        }
        this.status -= amount;
        if (this.status < 0) {
            this.status = 0; // Ensures battery status does not go below 0%
        }
    }

    // Getter for the battery status
    public double getStatus() {
        return status;
    }
    
    // Getter for the battery capacity
    public int getCapacity() {
        return capacity;
    }
}
