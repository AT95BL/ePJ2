package javacitymap;

import model.Vehicle;

import java.util.concurrent.locks.ReentrantLock;

public class JavaCityMap {
    public static final int NUMBER_OF_ROWS = 20;
    public static final int NUMBER_OF_COLUMNS = 20;
    public static final int WIDE_PART_OF_THE_CITY_LOWER_BOUND = 0;
    public static final int WIDE_PART_OF_THE_CITY_UPPER_BOUND = 10;
    
    public static Object[][] map;
    public static ReentrantLock[][] cellLocks;  // Locks for each cell
    
    // Constructor
    public JavaCityMap() {
        map = new Object[NUMBER_OF_ROWS][NUMBER_OF_COLUMNS];
        cellLocks = new ReentrantLock[NUMBER_OF_ROWS][NUMBER_OF_COLUMNS];
        for (int i = 0; i < NUMBER_OF_ROWS; i++) {
            for (int j = 0; j < NUMBER_OF_COLUMNS; j++) {
                cellLocks[i][j] = new ReentrantLock();
            }
        }
    }

    // to check if the map-cell is clear(free) call the following method:
    public static boolean isCellClear(int x, int y) {
    	//return map[x][y] == null;
        cellLocks[x][y].lock();
        try {
            return map[x][y] == null;
        } finally {
            cellLocks[x][y].unlock();
        }
    }

    // to clean the cell-map call the following method:
    public static void clearCell(int x, int y) {
    	//map[x][y] = null;
        cellLocks[x][y].lock();
        try {
            map[x][y] = null;
        } finally {
            cellLocks[x][y].unlock();
        }
    }

    // Metoda za ažuriranje ćelije mape
    public static void updateCell(int x, int y, Vehicle vehicle) {
    	//map[x][y] = vehicle;
        cellLocks[x][y].lock();
        try {
            map[x][y] = vehicle;
        } finally {
            cellLocks[x][y].unlock();
        }
    }

	
	// to check if the object has reached maximum left limit of the map, call the following method:
	public static synchronized boolean checkLeftBoundaryMaxLimit(int positionY) {return positionY == NUMBER_OF_COLUMNS-1; }
	
	// to check if the object has reached maximum right limit of the map, call the following method:
	public static synchronized boolean checkRightBoundaryMaxLimit(int positionY) {return positionY == 0;}
	
	// to check if the object has reached maximum upper limit of the map, call the following method:
	public  static synchronized boolean checkUpperBoundaryMaxLimit(int positionX) {return positionX == 0;}
	
	// to check if object has reached the bottom of the map, call the following method:
	public static synchronized boolean checkLowerBoundaryMaxLimit(int positionX) {return positionX == NUMBER_OF_ROWS-1;}
	
	// to check if the cell-indexes are valid, call the following method:
	public static synchronized boolean checkValidCell(int x, int y) {return x>=0 && x <=NUMBER_OF_ROWS-1 && y>=0 && y <= NUMBER_OF_COLUMNS-1; }
	
	// to check if the cell-indexes are inside wider part of the JavaCity, call the following method:
	public  static synchronized boolean checkWidePartOfTheJavaCity(int x, int y) { 
		return x >= WIDE_PART_OF_THE_CITY_LOWER_BOUND 
				&& x <= WIDE_PART_OF_THE_CITY_UPPER_BOUND 
				|| y >= WIDE_PART_OF_THE_CITY_LOWER_BOUND 
				&& y<= WIDE_PART_OF_THE_CITY_UPPER_BOUND; }
	
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

