package model;

import java.util.Date;

import battery.*;
import passenger.*;

import java.util.ArrayList;


/**
 * @author AT95
 * @version 1
 * The {@code Car} class represents a car in the transportation system.
 * It extends the {@code Vehicle} class and provides specific attributes and methods for cars.
 */
public class Car extends Vehicle{
	private static final int MAX_NUMBER_OF_PASSENGERS=5;
	private static final double BATTERY_POWER=300;
	
	/**
     * Constructs a new instance of {@code Car} with default values.
     */
	public Car() {super();}
	
	/**
     * Constructs a new instance of {@code Car} with the specified details.
     * 
     * @param id the unique identifier of the car
     * @param manufacturer the manufacturer of the car
     * @param model the model of the car
     * @param purchaseDate the date when the car was purchased
     * @param purchasePrice the purchase price of the car
     * @param autonomyOrMaxSpeed the autonomy or maximum speed of the car
     * @param maxSpeed the maximum speed of the car
     * @param description the description of the car
     * @param type the type of the car
     */
	public Car(
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
