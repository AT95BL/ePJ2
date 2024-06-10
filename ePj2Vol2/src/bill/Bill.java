package bill;

import passenger.Passenger;

import java.io.Serializable;

/**
 * The {@code Bill} class represents a bill for a passenger's trip, including the total cost, 
 * vehicle type, passenger information, and whether there was a malfunction during the trip.
 * If there was a malfunction, the total cost is set to 0.
 * 
 * <p>
 * Example usage:
 * <pre>
 * {@code
 * Passenger passenger = new Passenger("John Doe");
 * Bill bill = new Bill(100.0, "Car", passenger, false);
 * System.out.println(bill);
 * }
 * </pre>
 * </p>
 */
public class Bill implements Serializable{
    private double total;
    private String vehicleType;
    private Passenger passenger;
    private boolean malfunction;
    
    /**
     * Constructs a new {@code Bill} with the specified total, vehicle type, passenger, and malfunction status.
     * If there is a malfunction, the total cost is set to 0.
     * 
     * @param total the total cost of the trip
     * @param vehicleType the type of vehicle used for the trip
     * @param passenger the passenger associated with the trip
     * @param malfunction whether there was a malfunction during the trip
     */
    public Bill(double total, String vehicleType, Passenger passenger, boolean malfunction) {
        this.total = malfunction ? 0 : total;
        this.vehicleType = vehicleType;
        this.passenger = passenger;
        this.malfunction = malfunction;
    }
    
    /**
     * Returns a string representation of the bill, including the total cost, vehicle type,
     * passenger name, and malfunction status.
     * 
     * @return a string representation of the bill
     */
    @Override
    public String toString() {
        return "Bill Information:\n" +
                "Total: $" + String.format("%.2f", total) + "\n" +
                "Vehicle Type: " + vehicleType + "\n" +
                "Passenger: " + passenger.getName() + "\n" +
                "Malfunction: " + (malfunction ? "Yes" : "No") + 
                "\n"; 
    }
}
