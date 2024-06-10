package javacitymap;

import model.Vehicle;
import gui.*;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author AT95
 * @version 1
 * The {@code JavaCityMap} class represents a map of a city with a grid layout. It manages
 * the placement and movement of vehicles within the city, ensuring thread safety for concurrent
 * updates to the map.
 * 
 * <p>
 * Example usage:
 * <pre>
 * {@code
 * MapPanel mapPanel = new MapPanel();
 * JavaCityMap cityMap = new JavaCityMap(mapPanel);
 * Vehicle vehicle = new Vehicle();
 * cityMap.updateCell(0, 0, vehicle);
 * System.out.println(cityMap);
 * }
 * </pre>
 * </p>
 * 
 * @see Vehicle
 * @see MapPanel
 */
public class JavaCityMap {
    public static final int NUMBER_OF_ROWS = 20;
    public static final int NUMBER_OF_COLUMNS = 20;
    public static final int WIDE_PART_OF_THE_CITY_LOWER_BOUND = 0;
    public static final int WIDE_PART_OF_THE_CITY_UPPER_BOUND = 10;
    
    public static Object[][] map;
    public static ReentrantLock[][] cellLocks;  // Locks for each cell
    public static MapPanel mapPanel;
    
    /**
     * Constructs a new {@code JavaCityMap} with the specified map panel.
     * Initializes the map grid and cell locks.
     * 
     * @param mapPanel the {@code MapPanel} used to update the GUI representation of the map
     */
    public JavaCityMap(MapPanel mapPanel) {
        map = new Object[NUMBER_OF_ROWS][NUMBER_OF_COLUMNS];
        cellLocks = new ReentrantLock[NUMBER_OF_ROWS][NUMBER_OF_COLUMNS];
        for (int i = 0; i < NUMBER_OF_ROWS; i++) {
            for (int j = 0; j < NUMBER_OF_COLUMNS; j++) {
                cellLocks[i][j] = new ReentrantLock();
            }
        }
        JavaCityMap.mapPanel = mapPanel;
    }

    /**
     * Checks if the specified map cell is clear (i.e., unoccupied).
     * 
     * @param x the row index
     * @param y the column index
     * @return {@code true} if the cell is clear, {@code false} otherwise
     */
    public static boolean isCellClear(int x, int y) {
    	//return map[x][y] == null;
        cellLocks[x][y].lock();
        try {
            return map[x][y] == null;
        } finally {
            cellLocks[x][y].unlock();
        }
    }

    /**
     * Clears the specified map cell.
     * 
     * @param x the row index
     * @param y the column index
     */
    public static void clearCell(int x, int y) {
    	//map[x][y] = null;
        cellLocks[x][y].lock();
        try {
            map[x][y] = null;
            mapPanel.updateMap();
        } finally {
            cellLocks[x][y].unlock();
        }
    }

    /**
     * Updates the specified map cell with a {@code Vehicle}.
     * 
     * @param x the row index
     * @param y the column index
     * @param vehicle the {@code Vehicle} to place in the cell
     */
    public static void updateCell(int x, int y, Vehicle vehicle) {
    	//map[x][y] = vehicle;
        cellLocks[x][y].lock();
        try {
            map[x][y] = vehicle;
            mapPanel.updateMap();
        } finally {
            cellLocks[x][y].unlock();
        }
    }

	
    /**
     * Checks if the specified position has reached the maximum left limit of the map.
     * 
     * @param positionY the column index
     * @return {@code true} if the position is at the maximum left limit, {@code false} otherwise
     */
	public static synchronized boolean checkLeftBoundaryMaxLimit(int positionY) {return positionY == NUMBER_OF_COLUMNS-1; }
	
	/**
     * Checks if the specified position has reached the maximum right limit of the map.
     * 
     * @param positionY the column index
     * @return {@code true} if the position is at the maximum right limit, {@code false} otherwise
     */
	public static synchronized boolean checkRightBoundaryMaxLimit(int positionY) {return positionY == 0;}
	
	/**
     * Checks if the specified position has reached the maximum upper limit of the map.
     * 
     * @param positionX the row index
     * @return {@code true} if the position is at the maximum upper limit, {@code false} otherwise
     */
	public  static synchronized boolean checkUpperBoundaryMaxLimit(int positionX) {return positionX == 0;}
	
	/**
     * Checks if the specified position has reached the bottom of the map.
     * 
     * @param positionX the row index
     * @return {@code true} if the position is at the bottom of the map, {@code false} otherwise
     */
	public static synchronized boolean checkLowerBoundaryMaxLimit(int positionX) {return positionX == NUMBER_OF_ROWS-1;}
	
	/**
     * Checks if the specified cell indexes are valid.
     * 
     * @param x the row index
     * @param y the column index
     * @return {@code true} if the cell indexes are valid, {@code false} otherwise
     */
	public static synchronized boolean checkValidCell(int x, int y) {return x>=0 && x <=NUMBER_OF_ROWS-1 && y>=0 && y <= NUMBER_OF_COLUMNS-1; }
	
	/**
     * Checks if the specified cell indexes are inside the wider part of JavaCity.
     * 
     * @param x the row index
     * @param y the column index
     * @return {@code true} if the cell indexes are in the wider part of JavaCity, {@code false} otherwise
     */
	public  static synchronized boolean checkWidePartOfTheJavaCity(int x, int y) { 
		return x >= WIDE_PART_OF_THE_CITY_LOWER_BOUND 
				&& x <= WIDE_PART_OF_THE_CITY_UPPER_BOUND 
				|| y >= WIDE_PART_OF_THE_CITY_LOWER_BOUND 
				&& y<= WIDE_PART_OF_THE_CITY_UPPER_BOUND; }
	
	/**
     * Returns a string representation of the map, showing which cells are occupied by vehicles.
     * 
     * @return a string representation of the map
     */
	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < NUMBER_OF_ROWS; i++) {
            for (int j = 0; j < NUMBER_OF_COLUMNS; j++) {
                if (map[i][j] == null) {
                    sb.append("[ ]");
                } else {
                    sb.append("[V]"); // Assuming 'V' stands for a Vehicle object
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
