package gui;

import javacitymap.JavaCityMap;
import model.Car;
import model.Bike;
import model.Scooter;
import model.Vehicle;

import javax.swing.*;
import java.awt.*;

/**
 * @author AT95
 * @version 1
 * The MapPanel class represents a custom JPanel that visualizes the city map and the vehicles on it.
 * The panel displays a grid where different types of vehicles are represented by different colors.
 */
public class MapPanel extends JPanel {
    private static final int CELL_SIZE = 30;
    
    /**
     * Paints the component. This method is overridden to provide custom painting code.
     * It draws the grid and fills cells with different colors based on the type of vehicle present.
     *
     * @param g the Graphics object used to draw the component
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Set color based on position
        for (int i = 0; i < JavaCityMap.NUMBER_OF_ROWS; i++) {
            for (int j = 0; j < JavaCityMap.NUMBER_OF_COLUMNS; j++) {
                if (i <= 10 && j <= 10) {
                    g.setColor(Color.BLUE);
                } else {
                    g.setColor(Color.GRAY);
                }
                g.fillRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);

                // Draw grid lines
                g.setColor(Color.BLACK);
                g.drawRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                
                // Determine the type of vehicle and set color accordingly
                Object obj = JavaCityMap.map[i][j];
                if (obj instanceof Car) {
                    g.setColor(Color.RED);
                    g.fillRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                } else if (obj instanceof Bike) {
                    g.setColor(Color.YELLOW);
                    g.fillRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                } else if (obj instanceof Scooter) {
                    g.setColor(Color.GREEN);
                    g.fillRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }
            }
        }
    }

    /**
     * Requests a repaint of the map. This method can be called to refresh the display
     * when the underlying data changes.
     */
    public void updateMap() {
        repaint();
    }
    
    /**
     * Gets the preferred size of the panel. This is overridden to ensure the panel
     * is large enough to display the entire map.
     *
     * @return the preferred size of the panel
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(JavaCityMap.NUMBER_OF_COLUMNS * CELL_SIZE, JavaCityMap.NUMBER_OF_ROWS * CELL_SIZE);
    }
}
