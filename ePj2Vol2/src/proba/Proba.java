package proba;

import javacitymap.*;
import malfunction.Malfunction;
import rental.*;
import model.*;
import monitor.*;
import passenger.*;
import data.*;
import utility.*;
import bill.Bill;

import java.util.Map;
import java.util.List;
import java.util.Random;
import java.util.HashMap;
import java.util.ArrayList;
import java.io.IOException;
import java.text.ParseException;

public class Proba {
	
	private static int passengerCounter=1;
	
	public static void main(String[] args) {
		VehicleDataLoader vehicleDataLoader = new VehicleDataLoader();
		RentalDataLoader rentalDataLoader = new RentalDataLoader();
		
		List<Vehicle> listOfVehicles = null;
		List<Rental> listOfRentals = null;
		List<Passenger> listOfPassengers = new ArrayList<>();
		
		JavaCityMap javaCityMap = new JavaCityMap();
		
		Random random = new Random();
		
		ConfigFileCreator configFileCreator = new ConfigFileCreator();
		configFileCreator.createConfigFile();
		
		RentalSalesMonitor rentalSalesMonitor = new RentalSalesMonitor();
		RentalSalaryMonitor rentalSalaryMonitor = new RentalSalaryMonitor();
		RentalRepairmentCostsMonitor rentalRepairementCostsMonitor = new RentalRepairmentCostsMonitor();
		
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
		            if("automobil".equals(vehicle.type)) {
		            	RentalRepairmentCostsMonitor.carRepairsTotal += 
		            			RentalRepairmentCostsMonitor.CAR_REPAIR_COST * vehicle.getPurchasePrice();
		            }
		            else if("bicikl".equals(vehicle.type)) {
		            	RentalRepairmentCostsMonitor.bikeRepairsTotal += 
		            			RentalRepairmentCostsMonitor.BIKE_REPAIR_COST * vehicle.getPurchasePrice();
		            }
		            else {
		            	RentalRepairmentCostsMonitor.scooterRepairsTotal += 
		            			RentalRepairmentCostsMonitor.SCOOTER_REPAIR_COST * vehicle.getPurchasePrice();
		            }
		        }

		        if (vehicle.getState() == Thread.State.NEW) {
		        	  Passenger passenger = null;
		        	  int temp = random.nextInt(100);
		        	  if( temp % 2 == 0) {
		        		  passenger = new Local(rental.getUserName(), "PASSENGER" + passengerCounter, "ADDRESS" + passengerCounter);
		        		  listOfPassengers.add(passenger);
		        	  }
		        	  else {
		        		  passenger = new Stranger(rental.getUserName(), "PASSENGER" + passengerCounter, "ADDRESS" + passengerCounter);
		        		  listOfPassengers.add(passenger);
		        	  }
		        	  vehicle.addPassenger(passenger);
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
		
		try {
			Thread.sleep(2000);
		}catch(InterruptedException ex) {
			ex.printStackTrace();
		}
		
		System.out.println("Bills: ");
		for(var passenger:listOfPassengers) {
			System.out.println(passenger.bill + "\n");
		}
		
		try {
			Thread.sleep(2000);
		}catch(InterruptedException ex) {
			ex.printStackTrace();
		}
		
		System.out.println("\n Sales Monitor: " + rentalSalesMonitor);
		System.out.println("\n Salary Monitor: " + rentalSalaryMonitor);
		System.out.println("\n Repair Costs Monitor: " + rentalRepairementCostsMonitor);
	}
}

