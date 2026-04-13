package monitor;

import model.Vehicle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.DoubleAdder;

/**
 * Tracks repair costs incurred by malfunctioning vehicles.
 *
 * <p>Note: this class was previously called {@code RentalRepairmentCostsMonitor}
 * ("repairment" is not a standard English word — corrected to {@code RentalRepairCostsMonitor}).
 */
public class RentalRepairCostsMonitor {

    public static final double CAR_REPAIR_COST     = 0.07;
    public static final double BIKE_REPAIR_COST    = 0.04;
    public static final double SCOOTER_REPAIR_COST = 0.02;

    private static final DoubleAdder carRepairTotal     = new DoubleAdder();
    private static final DoubleAdder bikeRepairTotal    = new DoubleAdder();
    private static final DoubleAdder scooterRepairTotal = new DoubleAdder();

    // Synchronized lists so vehicle threads can safely add entries
    public static final List<Vehicle> cars     = Collections.synchronizedList(new ArrayList<>());
    public static final List<Vehicle> bikes    = Collections.synchronizedList(new ArrayList<>());
    public static final List<Vehicle> scooters = Collections.synchronizedList(new ArrayList<>());

    public static void addCarRepairCost(double cost)     { carRepairTotal.add(cost); }
    public static void addBikeRepairCost(double cost)    { bikeRepairTotal.add(cost); }
    public static void addScooterRepairCost(double cost) { scooterRepairTotal.add(cost); }

    public static double getCarRepairTotal()     { return carRepairTotal.sum(); }
    public static double getBikeRepairTotal()    { return bikeRepairTotal.sum(); }
    public static double getScooterRepairTotal() { return scooterRepairTotal.sum(); }

    /**
     * Returns the list of vehicles belonging to the type that has incurred
     * the highest total repair costs.
     */
    public static List<Vehicle> getMostLossMakingVehicleType() {
        double carCost     = carRepairTotal.sum();
        double bikeCost    = bikeRepairTotal.sum();
        double scooterCost = scooterRepairTotal.sum();

        if (carCost >= bikeCost && carCost >= scooterCost)         return cars;
        else if (bikeCost >= carCost && bikeCost >= scooterCost)   return bikes;
        else                                                        return scooters;
    }

    @Override
    public String toString() {
        return "=== Rental Repair Costs Monitor ===\n"
             + String.format("Cars    : $%.2f%n", carRepairTotal.sum())
             + String.format("Bikes   : $%.2f%n", bikeRepairTotal.sum())
             + String.format("Scooters: $%.2f%n", scooterRepairTotal.sum());
    }
}
