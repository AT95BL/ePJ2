package model;

import java.util.Date;

import battery.*;

public class Bike extends Vehicle {
	private static final int MAX_NUMBER_OF_PASSENGERS=2;
	private static final double BATTERY_POWER=200;

	public Bike() {super();}
	
	// Constructor;
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
