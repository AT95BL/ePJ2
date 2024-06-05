/*
package proba;

import model.*;
import rental.*;
import javacitymap.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Proba2 {

	public static void main(String[] args) {
		
		Vehicle vehicle = new Car();
		
		vehicle.setVehicleId("V1");
		vehicle.setStartPositionX(1);
		vehicle.setStartPositionY(1);
		vehicle.setPositionX(vehicle.getStartPositionX());
		vehicle.setPositionY(vehicle.getStartPositionY());
		vehicle.setDestinationPositionX(15);
		vehicle.setDestinationPositionY(15);
		vehicle.setDuration(15);
		
		System.out.println(vehicle);
		
		JavaCityMap javaCityMap = new JavaCityMap();
		JavaCityMap.updateCell(vehicle.getPositionX(), vehicle.getPositionY(), vehicle);
		System.out.println(javaCityMap);
		
		vehicle.start();
		try {
			vehicle.join();
		}catch(InterruptedException ex) {
			System.err.println(ex);
		}
		System.out.println(javaCityMap);
		System.out.println("[" + vehicle.getPositionX() + "][" + vehicle.getPositionY() + "]");
		
	}
}
*/
