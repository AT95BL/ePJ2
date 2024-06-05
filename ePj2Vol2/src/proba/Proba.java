package proba;

import javacitymap.*;
import malfunction.Malfunction;
import rental.*;
import model.*;
import data.*;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.io.IOException;
import java.text.ParseException;

public class Proba {
	public static void main(String[] args) {
		VehicleDataLoader vehicleDataLoader = new VehicleDataLoader();
		RentalDataLoader rentalDataLoader = new RentalDataLoader();
		
		List<Vehicle> listOfVehicles = null;
		List<Rental> listOfRentals = null;
		
		JavaCityMap javaCityMap = new JavaCityMap();
		
		try {
			listOfVehicles = vehicleDataLoader.loadVehicles("PJ2 - projektni zadatak 2024 - Prevozna sredstva.csv");
			for(var vehicle:listOfVehicles) {
				System.out.println(vehicle);
			}
			
		}catch(IOException | ParseException ex){
			System.err.println(ex);
		}
		
		try {
			listOfRentals = rentalDataLoader.loadRentals("PJ2 - projektni zadatak 2024 - Iznajmljivanja.csv");
			for(var rental:listOfRentals) {
				System.out.println(rental);
			}
			
		}catch(IOException | ParseException ex){
			System.err.println(ex);
		}
		
		Map<String, Vehicle> vehicleIDtoVehicle = new HashMap<>();
		for (var vehicle : listOfVehicles) {
		    vehicleIDtoVehicle.put(vehicle.getVehicleId(), vehicle);
		}
		
		
		for (var rental : listOfRentals) {
		    String idFromRental = rental.getVehicleId();
		    
		    // Umesto pretrage kroz listu, koristimo mapu
		    Vehicle vehicle = vehicleIDtoVehicle.get(idFromRental);
		    
		    if (vehicle != null) {
		        vehicle.setStartPositionX(rental.getVehicleStartPositionX());
		        vehicle.setStartPositionY(rental.getVehicleStartPositionY());
		        vehicle.setPositionX(rental.getVehicleStartPositionX());
		        vehicle.setPositionY(rental.getVehicleStartPositionY());
		        vehicle.setDestinationPositionX(rental.getVehicleDestinationPositionX());
		        vehicle.setDestinationPositionY(rental.getVehicleDestinationPositionY());
		        vehicle.setDuration(rental.getDuration());
		        vehicle.setMalfunction(rental.isMalfunction());
		        if (vehicle.isMalfunction()) {
		            System.out.println("Malfunctioned Vehicle!! \n");
		            vehicle.setMalfunctionModel(new Malfunction(Malfunction.MALFUNCTION_MESSAGE, rental.getDate(), vehicle));
		        }

		        if (vehicle.getState() == Thread.State.NEW) {
		        	  JavaCityMap.updateCell(vehicle.getPositionX(), vehicle.getPositionY(), vehicle);
				      System.out.println(vehicle);
				      vehicle.start();
		        } else {
		            System.err.println("Vehicle thread already started or finished: " + vehicle.getVehicleId());
		        }

		        try {
		            vehicle.join();
		        } catch (InterruptedException ex) {
		            System.err.println(ex);
		        }
		    }
		}
	}
}

