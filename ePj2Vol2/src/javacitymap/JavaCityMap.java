package javacitymap;

import gui.MapPanel;
import model.Vehicle;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Represents the city grid on which vehicles move.
 *
 * <p>The grid is {@value #NUMBER_OF_ROWS} × {@value #NUMBER_OF_COLUMNS}.
 * Rows 0–{@value #DOWNTOWN_UPPER_ROW} form the "wide" (downtown) zone;
 * the remaining rows form the narrower suburban zone.
 *
 * <p>Thread safety: every cell has its own {@link ReentrantLock}, so concurrent
 * vehicle threads can read/write different cells without contention.
 */
public class JavaCityMap {

    public static final int NUMBER_OF_ROWS    = 20;
    public static final int NUMBER_OF_COLUMNS = 20;

    /** Inclusive upper-row index of the downtown (wide) zone. */
    public static final int DOWNTOWN_LOWER_ROW = 0;
    public static final int DOWNTOWN_UPPER_ROW = 10;

    public static Object[][]        map;
    public static ReentrantLock[][] cellLocks;
    public static MapPanel          mapPanel;

    /**
     * Initialises the grid and registers the panel that will be repainted
     * whenever the map changes.
     *
     * @param mapPanel the Swing panel responsible for rendering the grid
     */
    public JavaCityMap(MapPanel mapPanel) {
        map       = new Object[NUMBER_OF_ROWS][NUMBER_OF_COLUMNS];
        cellLocks = new ReentrantLock[NUMBER_OF_ROWS][NUMBER_OF_COLUMNS];
        for (int row = 0; row < NUMBER_OF_ROWS; row++) {
            for (int col = 0; col < NUMBER_OF_COLUMNS; col++) {
                cellLocks[row][col] = new ReentrantLock();
            }
        }
        JavaCityMap.mapPanel = mapPanel;
    }

    // -------------------------------------------------------------------------
    // Cell operations
    // -------------------------------------------------------------------------

    /** Returns {@code true} if the cell at (row, col) is unoccupied. */
    public static boolean isCellClear(int row, int col) {
        cellLocks[row][col].lock();
        try { return map[row][col] == null; }
        finally { cellLocks[row][col].unlock(); }
    }

    /** Removes any occupant from the cell at (row, col) and repaints. */
    public static void clearCell(int row, int col) {
        cellLocks[row][col].lock();
        try { map[row][col] = null; mapPanel.updateMap(); }
        finally { cellLocks[row][col].unlock(); }
    }

    /** Places {@code vehicle} in the cell at (row, col) and repaints. */
    public static void updateCell(int row, int col, Vehicle vehicle) {
        cellLocks[row][col].lock();
        try { map[row][col] = vehicle; mapPanel.updateMap(); }
        finally { cellLocks[row][col].unlock(); }
    }

    // -------------------------------------------------------------------------
    // Boundary checks
    // -------------------------------------------------------------------------

    public static synchronized boolean isAtEasternEdge(int col)  { return col == NUMBER_OF_COLUMNS - 1; }
    public static synchronized boolean isAtWesternEdge(int col)  { return col == 0; }
    public static synchronized boolean isAtNorthernEdge(int row) { return row == 0; }
    public static synchronized boolean isAtSouthernEdge(int row) { return row == NUMBER_OF_ROWS - 1; }

    /** Returns {@code true} if (row, col) is a valid grid coordinate. */
    public static synchronized boolean isValidCell(int row, int col) {
        return row >= 0 && row < NUMBER_OF_ROWS
            && col >= 0 && col < NUMBER_OF_COLUMNS;
    }

    /**
     * Returns {@code true} if (row, col) falls within the downtown (wide) zone.
     * The zone covers rows {@value #DOWNTOWN_LOWER_ROW}–{@value #DOWNTOWN_UPPER_ROW}
     * OR columns 0–{@value #DOWNTOWN_UPPER_ROW}.
     */
    public static synchronized boolean checkWidePartOfTheJavaCity(int row, int col) {
        return (row >= DOWNTOWN_LOWER_ROW && row <= DOWNTOWN_UPPER_ROW)
            || (col >= DOWNTOWN_LOWER_ROW && col <= DOWNTOWN_UPPER_ROW);
    }

    // -------------------------------------------------------------------------
    // Debug
    // -------------------------------------------------------------------------

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < NUMBER_OF_ROWS; row++) {
            for (int col = 0; col < NUMBER_OF_COLUMNS; col++) {
                sb.append(map[row][col] == null ? "[ ]" : "[V]");
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}
