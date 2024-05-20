package simulation;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import java.io.IOException;
import java.text.ParseException;

import models.*;
import data.*;

public class Simulation {
	
	public static final int NUMBER_OF_ROWS=20;
	public static final int NUMBER_OF_COLUMNS=20;
	public static Object[][] map;						//	static object!! Use Simulation.map for calling it!!
	
	public List<Vehicle> vehicles;
	public VehicleDataLoader vdl ;						// short/nice name
	
	Random random = new Random();
	
	public Simulation() {
		map = new Object[NUMBER_OF_ROWS][NUMBER_OF_COLUMNS];
		this.vehicles = new ArrayList<>();
		this.vdl = new VehicleDataLoader();
	}
	
	//	method to initialize a list ..
	public void importVehicles(String path) {
		try {
			this.vehicles=vdl.loadVehicles(path);
			System.out.println("Successfully imported vehicles!!");
		}catch(IOException | ParseException ex) {
			ex.printStackTrace();
		}
	}
	
	// post those vehicles to the map ..
	public void putVehiclesRandom() {
		
		for(var vehicle:this.vehicles) {
			
			int positionX=random.nextInt(NUMBER_OF_ROWS);
			int positionY=random.nextInt(NUMBER_OF_COLUMNS);
			
			while(map[positionX][positionY] != null) {
				 positionX=random.nextInt(NUMBER_OF_ROWS);
				 positionY=random.nextInt(NUMBER_OF_COLUMNS);
			}
			
			vehicle.setPositionX(positionX);
			vehicle.setPositionY(positionY);
			map[positionX][positionY] = vehicle;
		}
	}
	
	
	public void printMatrix() {
		
		for(int i=0; i<this.NUMBER_OF_ROWS; i++)
			for(int j=0; j<this.NUMBER_OF_COLUMNS; j++) {
				if(map[i][j] != null) {
					System.out.println("[" + i + "][" + j + "] = " + (Vehicle)map[i][j]);
				}
			}
	}
	
	
	public static boolean isCellClear(int x, int y) { return map[x][y] == null; }
	public static void clearCell(int x, int y) { map[x][y] = null; }
	public static void updateCell(int x, int y, Vehicle vehicle) { map[x][y]=vehicle; }
	
	
	
	/*
	public static void main(String[] args) {
		Simulation sim = new Simulation();
		sim.importVehicles("C:\\Users\\Korisnik.DESKTOP-JVOQTMK\\Desktop\\pj2_project\\PJ2 - projektni zadatak 2024 - Prevozna sredstva.csv");
		sim.putVehiclesRandom();
		sim.printMatrix();
	}
	*/
	 
}

