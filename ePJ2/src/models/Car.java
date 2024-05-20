package models;

import java.util.Date;
import java.util.ArrayList;

import person.*;

public class Car extends Vehicle{
	private static final int MAX_NUMBER_OF_PASSENGERS=5;
	int numberOfPassengers=0;
	ArrayList<Person> passengers = new ArrayList<>();

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
