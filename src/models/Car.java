package models;

import java.util.Date;

import user.*;

import java.util.ArrayList;

public class Car extends Vehicle{
	
	private static final int MAX_NUMBER_OF_PASSENGERS=5;
	
	public Car() {super();}
	
	//Constructor
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
