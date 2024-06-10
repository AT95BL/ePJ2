package model;

import java.util.Date;

import battery.*;

/**
 * @author AT95
 * @version 1
 * The {@code Bike} class represents a bicycle in the transportation system.
 * It extends the {@code Vehicle} class and provides specific attributes and methods for bicycles.
 */
public class Bike extends Vehicle {
	private static final int MAX_NUMBER_OF_PASSENGERS=2;
	private static final double BATTERY_POWER=200;
	
	/**
     * Constructs a new instance of {@code Bike} with default values.
     */
	public Bike() {super();}
	
	/**
     * Constructs a new instance of {@code Bike} with the specified details.
     * 
     * @param id the unique identifier of the bike
     * @param manufacturer the manufacturer of the bike
     * @param model the model of the bike
     * @param purchaseDate the date when the bike was purchased
     * @param purchasePrice the purchase price of the bike
     * @param autonomyOrMaxSpeed the autonomy or maximum speed of the bike
     * @param maxSpeed the maximum speed of the bike
     * @param description the description of the bike
     * @param type the type of the bike
     */
	public Bike(
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
