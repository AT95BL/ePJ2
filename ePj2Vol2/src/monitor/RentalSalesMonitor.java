package monitor;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Tracks the total number of completed rentals for each vehicle type.
 *
 * <p>All counters use {@link AtomicLong} so that concurrent vehicle threads
 * can increment them without explicit synchronization.
 */
public class RentalSalesMonitor {

    private static final AtomicLong carRentalCount     = new AtomicLong(0);
    private static final AtomicLong bikeRentalCount    = new AtomicLong(0);
    private static final AtomicLong scooterRentalCount = new AtomicLong(0);

    public static void incrementCarRentals()     { carRentalCount.incrementAndGet(); }
    public static void incrementBikeRentals()    { bikeRentalCount.incrementAndGet(); }
    public static void incrementScooterRentals() { scooterRentalCount.incrementAndGet(); }

    public static long getCarRentalCount()     { return carRentalCount.get(); }
    public static long getBikeRentalCount()    { return bikeRentalCount.get(); }
    public static long getScooterRentalCount() { return scooterRentalCount.get(); }

    /** Returns a description of whichever vehicle type has the most rentals. */
    public static String getTopPerformer() {
        long cars     = carRentalCount.get();
        long bikes    = bikeRentalCount.get();
        long scooters = scooterRentalCount.get();

        if (cars >= bikes && cars >= scooters)         return "Cars: "    + cars;
        else if (bikes >= cars && bikes >= scooters)   return "Bikes: "   + bikes;
        else                                           return "Scooters: " + scooters;
    }

    @Override
    public String toString() {
        return "=== Rental Sales Monitor ===\n"
             + "Cars    : " + carRentalCount.get() + "\n"
             + "Bikes   : " + bikeRentalCount.get() + "\n"
             + "Scooters: " + scooterRentalCount.get();
    }
}
