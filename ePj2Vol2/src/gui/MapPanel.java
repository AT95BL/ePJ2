package gui;

import javacitymap.JavaCityMap;
import model.Vehicle;

import javax.swing.*;
import java.awt.*;

/**
 * Dark tactical map panel.
 */
public class MapPanel extends JPanel {

    private static final int CELL           = 28;
    private static final int DOWNTOWN_LIMIT = 10;

    public MapPanel() {
        setBackground(AppTheme.BG_DEEP);
        setOpaque(true);
        // Repaint at ~30 fps so vehicle movement is visually smooth
        new Timer(33, e -> repaint()).start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        for (int row = 0; row < JavaCityMap.NUMBER_OF_ROWS; row++) {
            for (int col = 0; col < JavaCityMap.NUMBER_OF_COLUMNS; col++) {
                int px = col * CELL;
                int py = row * CELL;

                // --- OVDJE JE IZMJENA ZA VIDLJIVOST ZONA ---
                boolean downtown = row <= DOWNTOWN_LIMIT && col <= DOWNTOWN_LIMIT;
                if (downtown) {
                    // Svjetlija plava boja za Downtown zonu
                    g2.setColor(new Color(0x0A, 0x1A, 0x3A)); 
                } else {
                    // Skoro crna boja za periferiju (Suburban)
                    g2.setColor(AppTheme.BG_DEEP); 
                }
                g2.fillRect(px, py, CELL, CELL);

                // Vehicle cell with glow
                Object occupant = JavaCityMap.map[row][col];
                if (occupant instanceof Vehicle vehicle) {
                    paintVehicleCell(g2, vehicle, px, py);
                }

                // Grid lines (suptilne linije mreže)
                g2.setColor(new Color(255, 255, 255, 15)); 
                g2.drawRect(px, py, CELL, CELL);
            }
        }

        paintZoneBoundary(g2);
        g2.dispose();
    }

    private void paintVehicleCell(Graphics2D g2, Vehicle vehicle, int px, int py) {
        Color base = vehicle.getMapColor();

        // Glow rings
        for (int layer = 3; layer >= 1; layer--) {
            int spread = layer * 3;
            g2.setColor(withAlpha(base, 28));
            g2.fillRect(px - spread, py - spread, CELL + spread * 2, CELL + spread * 2);
        }

        // Filled body
        g2.setColor(withAlpha(base, 200));
        g2.fillRect(px + 2, py + 2, CELL - 4, CELL - 4);

        // Top-half highlight
        g2.setColor(withAlpha(Color.WHITE, 55));
        g2.fillRect(px + 4, py + 4, CELL - 8, (CELL - 8) / 2);

        // Crisp border
        g2.setColor(base);
        g2.setStroke(new BasicStroke(1.2f));
        g2.drawRect(px + 2, py + 2, CELL - 5, CELL - 5);
        g2.setStroke(new BasicStroke(1f));
    }

    private void paintZoneBoundary(Graphics2D g2) {
        int bx = (DOWNTOWN_LIMIT + 1) * CELL;
        int by = (DOWNTOWN_LIMIT + 1) * CELL;
        float[] dash = {4f, 4f};
        g2.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1f, dash, 0f));
        // Svijetlo cijan linija razgraničenja
        g2.setColor(AppTheme.ACCENT_CYAN); 
        g2.drawLine(bx, 0,  bx, JavaCityMap.NUMBER_OF_ROWS    * CELL);
        g2.drawLine(0,  by, JavaCityMap.NUMBER_OF_COLUMNS * CELL, by);
        g2.setStroke(new BasicStroke(1f));
    }

    private static Color withAlpha(Color c, int alpha) {
        return new Color(c.getRed(), c.getGreen(), c.getBlue(), alpha);
    }

    public void updateMap() { repaint(); }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(
                JavaCityMap.NUMBER_OF_COLUMNS * CELL,
                JavaCityMap.NUMBER_OF_ROWS    * CELL);
    }
}