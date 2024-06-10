package battery;

/**
 * @author AT95
 * @version 1
 * The {@code Battery} class represents a battery with a specific capacity and status.
 * The capacity can be one of three values: 100, 200, or 300, corresponding to scooters,
 * bikes, and cars respectively. The status of the battery is represented as a percentage.
 * The class includes methods for charging and decreasing the battery status, as well as
 * getter and setter methods for the battery's attributes.
 * 
 * <p>
 * Example usage:
 * <pre>
 * {@code
 * Battery scooterBattery = new Battery(100);
 * scooterBattery.charging(10);
 * scooterBattery.decreasing(5);
 * System.out.println(scooterBattery);
 * }
 * </pre>
 * </p>
 * 
 * @see BatteryException
 */
public class Battery {
	private static final String MAXIMUM_CAPACITY_MESSAGE = "Battery capacity: " + "\n" + 
            "100 for Scooters" + "\n" +
            "200 for Bikes" + "\n" +
            "300 for Cars" + "\n";
	private static final String BATTERY_STATUS_MESSAGE = "Battery status must var from 0.0 to 100!!";

	// attributes:
	private double capacity;       	// maximum capacity, ..for now.. is 100/200/300 scooter/bike/car
	private double status = 100;    // battery percentage, let's say it's fully charged
	
	/**
     * Default constructor for the {@code Battery} class.
     */
	public Battery() {}
	
	/**
     * Constructor that initializes the battery with a specific capacity.
     * 
     * @param capacity the capacity of the battery (must be 100, 200, or 300)
     * @throws BatteryException if the capacity is not 100, 200, or 300
     */
	public Battery(double capacity) throws BatteryException{
		if(capacity != 100 && capacity != 200 && capacity != 300) {
			throw new BatteryException(MAXIMUM_CAPACITY_MESSAGE);
		}
		this.capacity=capacity;
	}
	
	/**
     * Simulates charging the battery by increasing the battery status.
     * 
     * @param amount the amount to increase the battery status
     * @throws IllegalArgumentException if the amount is negative
     */
	public void charging(double amount) {
		if(amount < 0) {
			throw new IllegalArgumentException("Charging amount cannot be negative!!");
		}
		
		this.status+=100;
		if(this.status > 100) {
			this.status=100;
		}
	}
	
	/**
     * Simulates decreasing the battery by decreasing the battery status.
     * 
     * @param amount the amount to decrease the battery status
     * @throws IllegalArgumentException if the amount is negative
     */
	public void decreasing(double amount) {
		if(amount < 0) {
			throw new IllegalArgumentException("Decreasing amount cannot be negative.");
		}
		this.status-=amount;
		if(this.status < 0) {
			this.status = 0; 	// Ensures battery status does not go below 0%
		}
	}
	
	/**
     * Gets the battery capacity.
     * 
     * @return the battery capacity
     */
	public double getBatteryCapacity() {return this.capacity; }
	
	/**
     * Resets the battery capacity.
     * 
     * @param capacity the new capacity of the battery (must be 100, 200, or 300)
     * @throws BatteryException if the capacity is not 100, 200, or 300
     */
	public void resetBatteryCapacity(double capacity)throws BatteryException {
		if(capacity != 100 && capacity != 200 && capacity != 300) {
			throw new BatteryException(MAXIMUM_CAPACITY_MESSAGE);
		}
		this.capacity=capacity;
	}
	
	/**
     * Gets the battery status.
     * 
     * @return the battery status
     */
	public double getBatteryStatus() {return this.status;} 
	
	/**
     * Sets the battery status.
     * 
     * @param status the new status of the battery
     * @throws BatteryException if the status is not between 0 and 100
     */
	public void setBatteryStatus(double status)throws BatteryException {
		if(0 < status && status >= 100) {
			this.status = status;
		}
	}
	
	// Overrides:
	
	/**
     * Returns a string representation of the battery status.
     * 
     * @return a string representation of the battery status
    */
	public String toString() {
		return "Battery Status: " + this.status;
	}
}
