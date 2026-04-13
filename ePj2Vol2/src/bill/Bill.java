package bill;

import passenger.Passenger;

import java.io.Serializable;

/**
 * An itemised receipt for a single trip.
 *
 * <p>If {@code malfunction} is {@code true} the fare is waived (set to zero).
 */
public class Bill implements Serializable {

    private final double    fare;
    private final String    vehicleType;
    private final Passenger passenger;
    private final boolean   malfunction;

    /**
     * @param fare        computed trip cost (will be set to 0 if malfunction is true)
     * @param vehicleType display label of the vehicle type
     * @param passenger   the passenger being billed
     * @param malfunction whether a malfunction occurred during the trip
     */
    public Bill(double fare, String vehicleType, Passenger passenger, boolean malfunction) {
        this.fare        = malfunction ? 0 : fare;
        this.vehicleType = vehicleType;
        this.passenger   = passenger;
        this.malfunction = malfunction;
    }

    public double  getFare()         { return fare; }
    public String  getVehicleType()  { return vehicleType; }
    public boolean isMalfunction()   { return malfunction; }

    @Override
    public String toString() {
        return "=== Bill ===\n"
             + String.format("Fare         : $%.2f%n", fare)
             + "Vehicle type : " + vehicleType + "\n"
             + "Passenger    : " + passenger.getName() + "\n"
             + "Malfunction  : " + (malfunction ? "Yes — fare waived" : "No");
    }
}
