package model;

import java.awt.Color;
import java.util.Date;
import battery.Battery;
import battery.BatteryException;

/** A four-wheeled electric car. Capacity: 5 passengers, battery 300. */
public class Car extends Vehicle {

    private static final double BATTERY_CAPACITY = 300;

    public Car() { super(); }

    public Car(String id, String manufacturer, String model, Date purchaseDate,
               double purchasePrice, double autonomyOrMaxSpeed, double maxSpeed,
               String description, VehicleType vehicleType) {
        super(id, manufacturer, model, purchaseDate, purchasePrice,
              autonomyOrMaxSpeed, maxSpeed, description, vehicleType);
        try {
            this.battery = new Battery(BATTERY_CAPACITY);
        } catch (BatteryException ex) {
            System.err.println("Failed to initialise Car battery: " + ex.getMessage());
        }
    }

    /** Cars appear RED on the map. */
    @Override
    public Color getMapColor() { return Color.RED; }
}
