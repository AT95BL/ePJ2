package models;

import java.io.Serializable;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Vehicle implements Serializable{
    String id;
    String manufacturer;
    String model;
    Date purchaseDate;
    double purchasePrice;
    double autonomyOrMaxSpeed;
    double maxSpeed;
    String description;
    String type;
	protected double currentBatteryLevel = 100; // samo priveremeno dok ne osmislim klasu baterija..
	
	public Vehicle() {}
	
	public Vehicle(
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
		this.id=id; 
		this.manufacturer=manufacturer; 
		this.model=model;
		this.purchaseDate=purchaseDate;
		this.purchasePrice=purchasePrice;
		this.autonomyOrMaxSpeed=autonomyOrMaxSpeed;
		this.maxSpeed=maxSpeed;
		this.description=description;
		this.type=type;
	}
	
	@Override
	public String toString() {
		return  
				"Vehicle: " + this.id + "\n" 
				+ "Manufacturer" + this.manufacturer + "\n"
				+ "Model: " + this.model + "\n"
				+ "Date of purchase: " + this.purchaseDate + "\n"
				+ "Purchase Price: " + this.purchasePrice + "\n"
				+ "Autonomy or Max Speed: " + this.autonomyOrMaxSpeed + "\n"
				+ "Max Speed: " + this.maxSpeed + "\n"
				+ "Description: " + this.description + "\n"
				+ "Type: " + this.type + "\n"
				+ "Battery Status: " + this.currentBatteryLevel + "\n";
	}
	
}
