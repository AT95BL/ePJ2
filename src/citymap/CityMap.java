package citymap;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import java.io.IOException;
import java.text.ParseException;

import models.*;
import data.*;

public class CityMap {
	
	public static final int NUMBER_OF_ROWS=20;
	public static final int NUMBER_OF_COLUMNS=20;
	public static final int WIDER_PART_OF_THE_CITY_LOWER_BOUND=0;
	public static final int WIDER_PART_OF_THE_CITY_UPPER_BOUND=10;
	
	public static Object[][] map;										//	static object!! Use Simulation.map for calling it!!
	
	public CityMap() {
		map = new Object[NUMBER_OF_ROWS][NUMBER_OF_COLUMNS];
	}
	
	
	// checkClear/clear a citymap-cell methods:
	public static boolean isCellClear(int x, int y) { return map[x][y] == null; }
	public static void clearCell(int x, int y) { map[x][y] = null; }
	
	// cell update method
	public static void updateCell(int x, int y, Vehicle vehicle) { map[x][y]=vehicle; }
	
	// map boundaries-check methods:
	public boolean checkLeftBoundaryMaxLimit(int positionX) {return positionX == 0; }
	public boolean checkRightBoundaryMaxLimit(int positionX) {return positionX == NUMBER_OF_COLUMNS-1;}
	public boolean checkUpperBoundaryMaxLimit(int positionY) {return positionY == 0;}
	public boolean checkLowerBoundaryMaxLimit(int positionY) {return positionY == NUMBER_OF_ROWS-1;}
	public boolean validCellPosition(int x, int y) {return x>=0 && x <=NUMBER_OF_COLUMNS-1 && y>=0 && y <= NUMBER_OF_ROWS-1; }
	
	// can Vehicle from point A(x1,y1) reach to point B(x2,y2) according to task-specs given!? 
	public static boolean arePointsOpposite(int x1, int y1, int x2, int y2) {
	        return (x1 == x2 || y1 == y2);
	}
	
}