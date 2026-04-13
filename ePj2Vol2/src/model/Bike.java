package model;

import java.awt.Color;
import java.util.Date;
import battery.Battery;
import battery.BatteryException;

/** An electric bicycle. Capacity: 2 passengers, battery 200. */
public class Bike extends Vehicle {

    private static final double BATTERY_CAPACITY = 200;

    public Bike() { super(); }

    public Bike(String id, String manufacturer, String model, Date purchaseDate,
                double purchasePrice, double autonomyOrMaxSpeed, double maxSpeed,
                String description, VehicleType vehicleType) {
        super(id, manufacturer, model, purchaseDate, purchasePrice,
              autonomyOrMaxSpeed, maxSpeed, description, vehicleType);
        try {
            this.battery = new Battery(BATTERY_CAPACITY);
        } catch (BatteryException ex) {
            System.err.println("Failed to initialise Bike battery: " + ex.getMessage());
        }
    }

    /** Bikes appear YELLOW on the map. */
    @Override
    public Color getMapColor() { return Color.YELLOW; }
}
