package model;

import java.util.Date;

import battery.*;

/**
 * @author AT95
 * @version 1
 * The {@code Scooter} class represents a scooter in the transportation system.
 * It extends the {@code Vehicle} class and provides specific attributes and methods for scooters.
 */
public class Scooter extends Vehicle{
	private static final int MAX_NUMBER_OF_PASSENGERS=1;
	private static final double BATTERY_POWER=100;
	
	/**
     * Constructs a new instance of {@code Scooter} with default values.
     */
	public Scooter() {super();}
	
	/**
     * Constructs a new instance of {@code Scooter} with the specified details.
     * 
     * @param id the unique identifier of the scooter
     * @param manufacturer the manufacturer of the scooter
     * @param model the model of the scooter
     * @param purchaseDate the date when the scooter was purchased
     * @param purchasePrice the purchase price of the scooter
     * @param autonomyOrMaxSpeed the autonomy or maximum speed of the scooter
     * @param maxSpeed the maximum speed of the scooter
     * @param description the description of the scooter
     * @param type the type of the scooter
     */
	public Scooter(
			String id, 
			String manufacturer, 
			String model,
			Date purchaseDate,
			double purchasePrice,
			double autonomyOrMaxSpeed,
			double maxSpeed,
			String description,
			String type
			) {
		super(
				id,
				manufacturer,
				model,
				purchaseDate,
				purchasePrice,
				autonomyOrMaxSpeed,
				maxSpeed,
				description,
				type);
		try {
			this.battery = new Battery(BATTERY_POWER);
		}catch(BatteryException ex) {
			System.err.println(ex);
		}
	}
}
