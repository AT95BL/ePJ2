package models;

import java.util.Date;

public class Car extends Vehicle{

public Car() {super();}
	
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
	}
}
