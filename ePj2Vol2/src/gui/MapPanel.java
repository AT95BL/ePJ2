package gui;

import javacitymap.JavaCityMap;
import model.Car;
import model.Bike;
import model.Scooter;
import model.Vehicle;

import javax.swing.*;
import java.awt.*;

public class MapPanel extends JPanel {
    private static final int CELL_SIZE = 30;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
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

    public void updateMap() {
        repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(JavaCityMap.NUMBER_OF_COLUMNS * CELL_SIZE, JavaCityMap.NUMBER_OF_ROWS * CELL_SIZE);
    }
}
