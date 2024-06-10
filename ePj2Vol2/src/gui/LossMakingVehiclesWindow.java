package gui;

import javax.swing.*;
import model.Vehicle;
import java.awt.*;
import java.util.List;

/**
 * @author AT95
 * @version 1
 * The LossMakingVehiclesWindow class represents a window that displays the most loss-making vehicles.
 * It extends JFrame and contains a JTextArea to show the vehicle information.
 */
public class LossMakingVehiclesWindow extends JFrame {
    private JTextArea textArea;
    
    /**
     * Constructs a new LossMakingVehiclesWindow.
     * Initializes the window with a title, size, and a JTextArea to display vehicle information.
     * 
     * @param vehicles the list of vehicles that are most loss-making
     */
    public LossMakingVehiclesWindow(List<Vehicle> vehicles) {
        setTitle("Most Loss Making Vehicles");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        StringBuilder sb = new StringBuilder("Most Loss Making Vehicles:\n");
        if (vehicles != null) {
            for (Vehicle vehicle : vehicles) {
                sb.append(vehicle.toString()).append("\n");
            }
        } else {
            sb.append("No data available.");
        }
        textArea.setText(sb.toString());
    }
}
