package main;

import citymap.CityMap;
import models.*;
import data.*;
import rental.Rental;

import java.util.List;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

public class Main {
	
	public static void main(String[] args) {
		
		VehicleDataLoader vehicleDataLoader = new VehicleDataLoader();
		RentalDataLoader rentalDataLoader = new RentalDataLoader();
		
		List<Vehicle> listOfVehicles = null;
		List<Rental> listOfRentals = null;
		
		CityMap javaCityMap = new CityMap();
		
		try {
            listOfVehicles = vehicleDataLoader.loadVehicles("C:\\Users\\Korisnik.DESKTOP-JVOQTMK\\Desktop\\pj2_project\\PJ2 - projektni zadatak 2024 - Prevozna sredstva.csv");
            for (Vehicle vehicle : listOfVehicles) {
                System.out.println(vehicle);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
		
		try {
            listOfRentals = rentalDataLoader.loadRentals("C:\\Users\\Korisnik.DESKTOP-JVOQTMK\\Desktop\\pj2_project\\PJ2 - projektni zadatak 2024 - Iznajmljivanja.csv");
            for (Rental rental : listOfRentals) {
                System.out.println(rental);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
		
		
		
		Map<String, Vehicle> mapOfVehiclesID = new HashMap<>();
		for(var vl:listOfVehicles) {
			mapOfVehiclesID.put(vl.id,vl);
		}
		
		for(var temp1: listOfRentals) {
			
			String id = temp1.getVehicleID();
			
			for(var temp2: listOfVehicles) {
				// ISKORISTI MAPU ZA PRETRAŽIVANJE!! -UBRZAJ PROGRAM!
				if(temp2.id.equals(id)) {
					temp2.setStartingPositionX(temp1.getStartX());
					temp2.setStartingPositionY(temp1.getStartY());
					temp2.setPositionX(temp1.getStartX());
					temp2.setPositionY(temp1.getStartY());
					temp2.setDestinationPositionX(temp1.getEndX());
					temp2.setDestinationPositionY(temp1.getEndY());
					temp2.setDuration(temp1.getDuration());
					temp2.start();
					try {
						temp2.join();
					}catch(InterruptedException ex) {
						ex.printStackTrace();
					}
				}
			}			
		}
	}
}
