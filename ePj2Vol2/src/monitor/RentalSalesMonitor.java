package monitor;

public class RentalSalesMonitor {
    public static long CAR_RENTAL_TOTAL;
    public static long BIKE_RENTAL_TOTAL;
    public static long SCOOTER_RENTAL_TOTAL;

    public static void carRentalIncrement() {
        ++CAR_RENTAL_TOTAL;
    }

    public static void bikeRentalIncrement() {
        ++BIKE_RENTAL_TOTAL;
    }

    public static void scooterRentalIncrement() {
        ++SCOOTER_RENTAL_TOTAL;
    }

    public static String getBestRentalSeller() {
        if (CAR_RENTAL_TOTAL > BIKE_RENTAL_TOTAL && CAR_RENTAL_TOTAL > SCOOTER_RENTAL_TOTAL)
            return "CAR_RENTAL_TOTAL: " + CAR_RENTAL_TOTAL;
        else if (BIKE_RENTAL_TOTAL > CAR_RENTAL_TOTAL && BIKE_RENTAL_TOTAL > SCOOTER_RENTAL_TOTAL)
            return "BIKE_RENTAL_TOTAL: " + BIKE_RENTAL_TOTAL;
        else
            return "SCOOTER_RENTAL_TOTAL: " + SCOOTER_RENTAL_TOTAL;
    }

    @Override
    public String toString() {
        return "Rental Sales Monitor:\n" +
                "Car Rental Total: " + CAR_RENTAL_TOTAL + "\n" +
                "Bike Rental Total: " + BIKE_RENTAL_TOTAL + "\n" +
                "Scooter Rental Total: " + SCOOTER_RENTAL_TOTAL;
    }
}
