package monitor;

public class RentalRepairmentCostsMonitor {
    public static final double CAR_REPAIR_COST = 0.07;
    public static final double BIKE_REPAIR_COST = 0.04;
    public static final double SCOOTER_REPAIR_COST = 0.02;

    public static double carRepairsTotal;
    public static double bikeRepairsTotal;
    public static double scooterRepairsTotal;

    public static double getCarRepairsTotal() {
        return carRepairsTotal;
    }

    public static void setCarRepairsTotal(double carRepairsTotal) {
        RentalRepairmentCostsMonitor.carRepairsTotal = carRepairsTotal;
    }

    public static double getBikeRepairsTotal() {
        return bikeRepairsTotal;
    }

    public static void setBikeRepairsTotal(double bikeRepairsTotal) {
        RentalRepairmentCostsMonitor.bikeRepairsTotal = bikeRepairsTotal;
    }

    public static double getScooterRepairsTotal() {
        return scooterRepairsTotal;
    }

    public static void setScooterRepairsTotal(double scooterRepairsTotal) {
        RentalRepairmentCostsMonitor.scooterRepairsTotal = scooterRepairsTotal;
    }

    @Override
    public String toString() {
        return "Rental Repairment Costs Monitor:\n" +
                "Car Repairs Total: $" + String.format("%.2f", carRepairsTotal) + "\n" +
                "Bike Repairs Total: $" + String.format("%.2f", bikeRepairsTotal) + "\n" +
                "Scooter Repairs Total: $" + String.format("%.2f", scooterRepairsTotal);
    }
}

