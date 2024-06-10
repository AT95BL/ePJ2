package gui;

import javacitymap.JavaCityMap;
import model.Vehicle;

import javax.swing.*;
import java.awt.*;

/**
 * @author AT95
 * @version 1
 * The JavaCityMapPanel class is responsible for rendering a city map on a JPanel.
 * Each cell in the map can either be empty or contain a vehicle.
 * The cells are colored based on their position and content.
 */
public class JavaCityMapPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private static final int CELL_SIZE = 30; // Veličina ćelije u pikselima
    
    /**
     * Constructs a JavaCityMapPanel and sets its preferred size based on the number of columns
     * and rows in the JavaCityMap and the defined cell size.
     */
    public JavaCityMapPanel() {
        // Postavljamo preferiranu veličinu panela tako da može prikazati celu mapu
        setPreferredSize(new Dimension(JavaCityMap.NUMBER_OF_COLUMNS * CELL_SIZE, JavaCityMap.NUMBER_OF_ROWS * CELL_SIZE));
    }
    
    /**
     * Paints the component by iterating through the cells of the JavaCityMap.
     * Each cell is colored based on its position and whether it contains a vehicle.
     *
     * @param g the Graphics object used for drawing
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i < JavaCityMap.NUMBER_OF_ROWS; i++) {
            for (int j = 0; j < JavaCityMap.NUMBER_OF_COLUMNS; j++) {
            	// Color cells based on their row index
                if (i <= JavaCityMap.WIDE_PART_OF_THE_CITY_UPPER_BOUND) {
                    g.setColor(Color.BLUE); // First color
                } else {
                    g.setColor(Color.GREEN); // Second color
                }
                g.fillRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);

                // Display vehicles
                Vehicle vehicle = (Vehicle)JavaCityMap.map[i][j];
                if (vehicle != null) {
                    g.setColor(Color.RED); // Color for vehicles
                    g.fillRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }

                // Draw cell borders
                g.setColor(Color.BLACK);
                g.drawRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }
    }
}
