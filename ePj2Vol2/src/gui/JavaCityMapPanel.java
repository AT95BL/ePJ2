package gui;

import javacitymap.JavaCityMap;
import model.Vehicle;

import javax.swing.*;
import java.awt.*;

public class JavaCityMapPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private static final int CELL_SIZE = 30; // Veličina ćelije u pikselima

    public JavaCityMapPanel() {
        // Postavljamo preferiranu veličinu panela tako da može prikazati celu mapu
        setPreferredSize(new Dimension(JavaCityMap.NUMBER_OF_COLUMNS * CELL_SIZE, JavaCityMap.NUMBER_OF_ROWS * CELL_SIZE));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i < JavaCityMap.NUMBER_OF_ROWS; i++) {
            for (int j = 0; j < JavaCityMap.NUMBER_OF_COLUMNS; j++) {
                // Bojenje ćelija zavisno od indeksa
                if (i <= JavaCityMap.WIDE_PART_OF_THE_CITY_UPPER_BOUND) {
                    g.setColor(Color.BLUE); // Prva boja
                } else {
                    g.setColor(Color.GREEN); // Druga boja
                }
                g.fillRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);

                // Prikazivanje vozila
                Vehicle vehicle = (Vehicle)JavaCityMap.map[i][j];
                if (vehicle != null) {
                    g.setColor(Color.RED); // Boja za vozila
                    g.fillRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }

                // Crtanje ivica ćelija
                g.setColor(Color.BLACK);
                g.drawRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }
    }
}
