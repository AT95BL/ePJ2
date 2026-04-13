package model;

import java.awt.Color;
import java.util.Date;
import battery.Battery;
import battery.BatteryException;

/** An electric scooter. Capacity: 1 passenger, battery 100. */
public class Scooter extends Vehicle {

    private static final double BATTERY_CAPACITY = 100;

    public Scooter() { super(); }

    public Scooter(String id, String manufacturer, String model, Date purchaseDate,
                   double purchasePrice, double autonomyOrMaxSpeed, double maxSpeed,
                   String description, VehicleType vehicleType) {
        super(id, manufacturer, model, purchaseDate, purchasePrice,
              autonomyOrMaxSpeed, maxSpeed, description, vehicleType);
        try {
            this.battery = new Battery(BATTERY_CAPACITY);
        } catch (BatteryException ex) {
            System.err.println("Failed to initialise Scooter battery: " + ex.getMessage());
        }
    }

    /** Scooters appear GREEN on the map. */
    @Override
    public Color getMapColor() { return Color.GREEN; }
}
