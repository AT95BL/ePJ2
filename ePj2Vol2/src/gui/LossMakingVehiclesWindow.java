package gui;

import javax.swing.*;
import model.Vehicle;
import java.awt.*;
import java.util.List;

public class LossMakingVehiclesWindow extends JFrame {
    private JTextArea textArea;

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
