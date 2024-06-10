package data;

import model.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author AT95
 * @version 1
 * The {@code VehicleDataLoader} class is responsible for loading vehicle data from a CSV file.
 * It parses the file and creates a list of {@code Vehicle} objects, which can be of type {@code Car},
 * {@code Bike}, or {@code Scooter}, based on the type specified in the file.
 * 
 * <p>
 * Example usage:
 * <pre>
 * {@code
 * VehicleDataLoader loader = new VehicleDataLoader();
 * List<Vehicle> vehicles = loader.loadVehicles("vehicles.csv");
 * for (Vehicle vehicle : vehicles) {
 *     System.out.println(vehicle);
 * }
 * }
 * </pre>
 * </p>
 * 
 * @see Vehicle
 * @see Car
 * @see Bike
 * @see Scooter
 */
public class VehicleDataLoader {
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy.");
	
	/**
     * Loads vehicle data from the specified CSV file.
     * 
     * @param filePath the path to the CSV file containing vehicle data
     * @return a list of {@code Vehicle} objects
     * @throws IOException if an I/O error occurs reading from the file
     * @throws ParseException if a date in the file cannot be parsed
     */
	public List<Vehicle> loadVehicles(String filePath)throws IOException, ParseException{
		List<Vehicle> vehicles = new ArrayList<>();
		
		try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
			String line = br.readLine(); // read header
			while((line = br.readLine()) != null) {
				String[] record = line.split(",");
				String id = record[0];																		//	1
				String manufacturer = record[1];															//	2
				String model = record[2];																	//	3
				Date purchaseDate = record[3].isEmpty() ? null : DATE_FORMAT.parse(record[3]);				//	4
				double purchasePrice = record[4].isEmpty() ? 0 : Double.parseDouble(record[4]);				//	5
				double autonomyOrMaxSpeed = record[5].isEmpty() ? 0 : Double.parseDouble(record[5]);		//	6
				double maxSpeed = record[6].isEmpty() ? 0 : Double.parseDouble(record[6]);					//	7
				String description = record[7];																//	8
				String type = record[8];																	//	9
				
				switch(type.toLowerCase()) {
					case "automobil":
						vehicles.add(
								new Car(
										id,
										manufacturer,
										model,
										purchaseDate,
										purchasePrice,
										autonomyOrMaxSpeed,
										maxSpeed,
										description,
										type));
						break;
					case "bicikl":
						vehicles.add(
								new Bike(
										id,
										manufacturer,
										model,
										purchaseDate,
										purchasePrice,
										autonomyOrMaxSpeed,
										maxSpeed,
										description,
										type));
						break;
					case "trotinet":
						vehicles.add(
								new Scooter(
										id,
										manufacturer,
										model,
										purchaseDate,
										purchasePrice,
										autonomyOrMaxSpeed,
										maxSpeed,
										description,
										type));
						break;
				}
			}
		}
		return vehicles;
	}
	
	/*
	public static void main(String[] args) {
		VehicleDataLoader loader = new VehicleDataLoader();
		List<Vehicle> list = new ArrayList<>();
		try {
			list = loader.loadVehicles("PJ2 - projektni zadatak 2024 - Prevozna sredstva.csv");
		}catch(IOException | ParseException ex) {
			System.err.println(ex);
		}
		for(var v:list) {
			System.out.println(v);
		}
	}
	*/
}
