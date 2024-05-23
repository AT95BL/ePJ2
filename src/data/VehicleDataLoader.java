package data;

import models.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VehicleDataLoader {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy.");

    public List<Vehicle> loadVehicles(String filePath) throws IOException, ParseException {
        List<Vehicle> vehicles = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // Read header
            while ((line = br.readLine()) != null) {
                String[] record = line.split(",");		//	Splitting the input line so that we can parse the values..
                String id = record[0];					
                String manufacturer = record[1];	
                String model = record[2];				
                Date purchaseDate = record[3].isEmpty() ? null : DATE_FORMAT.parse(record[3]);	
                double purchasePrice = record[4].isEmpty() ? 0 : Double.parseDouble(record[4]);
                double autonomyOrMaxSpeed = record[5].isEmpty() ? 0 : Double.parseDouble(record[5]);
                double maxSpeed = record[6].isEmpty() ? 0 : Double.parseDouble(record[6]);
                String description = record[7];
                String type = record[8];

                switch (type.toLowerCase()) {
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
                        		type)
                        		);
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
                        		type)
                        		);
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
                        		type)
                        		);
                        break;
                }
            }
        }
        return vehicles;
    }
}
