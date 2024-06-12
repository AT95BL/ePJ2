package monitor;

import java.util.List;
import java.util.ArrayList;

import model.*;

/**@author AT95
 * @version 1
 * Monitor class for tracking rental repairment costs for different types of vehicles.
 */
public class RentalRepairmentCostsMonitor {
	/** Repair cost per unit for cars. */
    public static final double CAR_REPAIR_COST = 0.07;
    
    /** Repair cost per unit for bikes. */
    public static final double BIKE_REPAIR_COST = 0.04;
    
    /** Repair cost per unit for scooters. */
    public static final double SCOOTER_REPAIR_COST = 0.02;
    
    /** Total repair costs for cars. */
    public static double carRepairsTotal;
    
    /** Total repair costs for bikes. */
    public static double bikeRepairsTotal;
    
    /** Total repair costs for scooters. */
    public static double scooterRepairsTotal;
    
    public static List<Vehicle> cars = new ArrayList<>();
    public static List<Vehicle> bikes = new ArrayList<>();
    public static List<Vehicle> scooters = new ArrayList<>();
    
    /**
     * Retrieves the total repair costs for cars.
     *
     * @return The total repair costs for cars.
     */
    public static double getCarRepairsTotal() {
        return carRepairsTotal;
    }
    
    /**
     * Sets the total repair costs for cars.
     *
     * @param carRepairsTotal The total repair costs for cars to set.
     */
    public static void setCarRepairsTotal(double carRepairsTotal) {
        RentalRepairmentCostsMonitor.carRepairsTotal = carRepairsTotal;
    }
    
    /**
     * Retrieves the total repair costs for bikes.
     *
     * @return The total repair costs for bikes.
     */
    public static double getBikeRepairsTotal() {
        return bikeRepairsTotal;
    }
    
    /**
     * Sets the total repair costs for bikes.
     *
     * @param bikeRepairsTotal The total repair costs for bikes to set.
     */
    public static void setBikeRepairsTotal(double bikeRepairsTotal) {
        RentalRepairmentCostsMonitor.bikeRepairsTotal = bikeRepairsTotal;
    }
    
    /**
     * Retrieves the total repair costs for scooters.
     *
     * @return The total repair costs for scooters.
     */
    public static double getScooterRepairsTotal() {
        return scooterRepairsTotal;
    }
    
    /**
     * Sets the total repair costs for scooters.
     *
     * @param scooterRepairsTotal The total repair costs for scooters to set.
     */
    public static void setScooterRepairsTotal(double scooterRepairsTotal) {
        RentalRepairmentCostsMonitor.scooterRepairsTotal = scooterRepairsTotal;
    }
    
    /**
     * Returns a string representation of the rental repairment costs monitor.
     *
     * @return A string representation of the rental repairment costs monitor.
     */
    @Override
    public String toString() {
        return "Rental Repairment Costs Monitor:\n" +
                "Car Repairs Total: $" + String.format("%.2f", carRepairsTotal) + "\n" +
                "Bike Repairs Total: $" + String.format("%.2f", bikeRepairsTotal) + "\n" +
                "Scooter Repairs Total: $" + String.format("%.2f", scooterRepairsTotal);
    }
    
    /**
     * Determines the most loss-making vehicle type based on the total repair costs.
     *
     * @return A list of vehicles of the most loss-making type.
     */
    public static List<Vehicle> getmostLossMakingVehicleType(){
    	if(getCarRepairsTotal() > getBikeRepairsTotal() 
    			&& getCarRepairsTotal() > getScooterRepairsTotal())
    		return cars;
    	else if(getBikeRepairsTotal() > getCarRepairsTotal() 
    			&& getBikeRepairsTotal() > getScooterRepairsTotal() )
    		return bikes;
    	return scooters;
    }
}

