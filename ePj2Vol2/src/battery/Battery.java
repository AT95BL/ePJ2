package battery;

public class Battery {
	private static final String MAXIMUM_CAPACITY_MESSAGE = "Battery capacity: " + "\n" + 
            "100 for Scooters" + "\n" +
            "200 for Bikes" + "\n" +
            "300 for Cars" + "\n";
	private static final String BATTERY_STATUS_MESSAGE = "Battery status must var from 0.0 to 100!!";

	// attributes:
	private double capacity;       	// maximum capacity, ..for now.. is 100/200/300 scooter/bike/car
	private double status = 100;    // battery percentage, let's say it's fully charged
	
	// Default Constructor
	public Battery() {}
	
	// Constructor
	public Battery(double capacity) throws BatteryException{
		if(capacity != 100 && capacity != 200 && capacity != 300) {
			throw new BatteryException(MAXIMUM_CAPACITY_MESSAGE);
		}
		this.capacity=capacity;
	}
	
	//	Battery charging simulation method
	public void charging(double amount) {
		if(amount < 0) {
			throw new IllegalArgumentException("Charging amount cannot be negative!!");
		}
		
		this.status+=100;
		if(this.status > 100) {
			this.status=100;
		}
	}
	
	//	Battery decreasing simulation method
	public void decreasing(double amount) {
		if(amount < 0) {
			throw new IllegalArgumentException("Decreasing amount cannot be negative.");
		}
		this.status-=amount;
		if(this.status < 0) {
			this.status = 0; 	// Ensures battery status does not go below 0%
		}
	}
	
	// getters/setters: 
	public double getBatteryCapacity() {return this.capacity; }
	
	public void resetBatteryCapacity(double capacity)throws BatteryException {
		if(capacity != 100 && capacity != 200 && capacity != 300) {
			throw new BatteryException(MAXIMUM_CAPACITY_MESSAGE);
		}
		this.capacity=capacity;
	}
	
	public double getBatteryStatus() {return this.status;} 
	
	public void setBatteryStatus(double status)throws BatteryException {
		if(0 < status && status >= 100) {
			this.status = status;
		}
	}
	
	// Overrides:
	public String toString() {
		return "Battery Status: " + this.status;
	}
}
