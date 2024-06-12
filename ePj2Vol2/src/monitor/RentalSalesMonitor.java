package monitor;

/**
 * @author AT95
 * @version 1
 * Monitor class for tracking rental sales of different types of vehicles.
 */
public class RentalSalesMonitor {
	/** Total number of car rentals. */
    public static long CAR_RENTAL_TOTAL;
    
    /** Total number of bike rentals. */
    public static long BIKE_RENTAL_TOTAL;
    
    /** Total number of scooter rentals. */
    public static long SCOOTER_RENTAL_TOTAL;
    
    /**
     * Increments the total number of car rentals.
     */
    public static void carRentalIncrement() {
        ++CAR_RENTAL_TOTAL;
    }
    
    /**
     * Increments the total number of bike rentals.
     */
    public static void bikeRentalIncrement() {
        ++BIKE_RENTAL_TOTAL;
    }
    
    /**
     * Increments the total number of scooter rentals.
     */
    public static void scooterRentalIncrement() {
        ++SCOOTER_RENTAL_TOTAL;
    }
    
    /**
     * Determines the best rental seller based on the total rental counts.
     *
     * @return A string indicating the best rental seller.
     */
    public static String getBestRentalSeller() {
        if (CAR_RENTAL_TOTAL > BIKE_RENTAL_TOTAL && CAR_RENTAL_TOTAL > SCOOTER_RENTAL_TOTAL)
            return "CAR_RENTAL_TOTAL: " + CAR_RENTAL_TOTAL;
        else if (BIKE_RENTAL_TOTAL > CAR_RENTAL_TOTAL && BIKE_RENTAL_TOTAL > SCOOTER_RENTAL_TOTAL)
            return "BIKE_RENTAL_TOTAL: " + BIKE_RENTAL_TOTAL;
        else
            return "SCOOTER_RENTAL_TOTAL: " + SCOOTER_RENTAL_TOTAL;
    }
    
    /**
     * Returns a string representation of the rental sales monitor.
     *
     * @return A string representation of the rental sales monitor.
     */
    @Override
    public String toString() {
        return "Rental Sales Monitor:\n" +
                "Car Rental Total: " + CAR_RENTAL_TOTAL + "\n" +
                "Bike Rental Total: " + BIKE_RENTAL_TOTAL + "\n" +
                "Scooter Rental Total: " + SCOOTER_RENTAL_TOTAL;
    }
}
