package gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import javacitymap.JavaCityMap;
import model.Vehicle;
import rental.Rental;
import data.RentalDataLoader;
import data.VehicleDataLoader;
import utility.ConfigFileCreator;
import utility.Serializer;
import utility.Deserializer;
import monitor.RentalSalesMonitor;
import monitor.RentalSalaryMonitor;
import monitor.RentalRepairmentCostsMonitor;
import passenger.Local;
import passenger.Passenger;
import passenger.Stranger;
import malfunction.Malfunction;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Random;

public class MainWindow extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextArea textArea;
    private JButton btnStartSimulation;
    private static int passengerCounter = 1;

    // Paneli za monitore
    private JPanel salesMonitorPanel;
    private JPanel salaryMonitorPanel;
    private JPanel repairCostsMonitorPanel;

    private JTextArea salesMonitorTextArea;
    private JTextArea salaryMonitorTextArea;
    private JTextArea repairCostsMonitorTextArea;

    private RentalSalesMonitor rentalSalesMonitor = new RentalSalesMonitor();
    private RentalSalaryMonitor rentalSalaryMonitor = new RentalSalaryMonitor();
    private RentalRepairmentCostsMonitor rentalRepairementCostsMonitor = new RentalRepairmentCostsMonitor();

    private MapPanel mapPanel;

    // Launch the application.
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainWindow frame = new MainWindow();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // Create the frame.
    public MainWindow() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1200, 800);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));

        mapPanel = new MapPanel();
        JScrollPane mapScrollPane = new JScrollPane(mapPanel);
        contentPane.add(mapScrollPane, BorderLayout.CENTER);

        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(300, 800));
        contentPane.add(scrollPane, BorderLayout.EAST);

        btnStartSimulation = new JButton("Start Simulation");
        btnStartSimulation.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Thread(() -> startSimulation()).start();
            }
        });

        contentPane.add(btnStartSimulation, BorderLayout.SOUTH);

        // Paneli za monitore
        salesMonitorPanel = new JPanel();
        salaryMonitorPanel = new JPanel();
        repairCostsMonitorPanel = new JPanel();

        salesMonitorPanel.setBorder(BorderFactory.createTitledBorder("Sales Monitor"));
        salaryMonitorPanel.setBorder(BorderFactory.createTitledBorder("Salary Monitor"));
        repairCostsMonitorPanel.setBorder(BorderFactory.createTitledBorder("Repair Costs Monitor"));

        salesMonitorTextArea = new JTextArea();
        salaryMonitorTextArea = new JTextArea();
        repairCostsMonitorTextArea = new JTextArea();

        salesMonitorTextArea.setEditable(false);
        salaryMonitorTextArea.setEditable(false);
        repairCostsMonitorTextArea.setEditable(false);

        salesMonitorPanel.setLayout(new BorderLayout());
        salaryMonitorPanel.setLayout(new BorderLayout());
        repairCostsMonitorPanel.setLayout(new BorderLayout());

        salesMonitorPanel.add(new JScrollPane(salesMonitorTextArea), BorderLayout.CENTER);
        salaryMonitorPanel.add(new JScrollPane(salaryMonitorTextArea), BorderLayout.CENTER);
        repairCostsMonitorPanel.add(new JScrollPane(repairCostsMonitorTextArea), BorderLayout.CENTER);

        JPanel monitorsPanel = new JPanel();
        monitorsPanel.setLayout(new GridLayout(3, 1));
        monitorsPanel.add(salesMonitorPanel);
        monitorsPanel.add(salaryMonitorPanel);
        monitorsPanel.add(repairCostsMonitorPanel);
        monitorsPanel.setPreferredSize(new Dimension(300, 400));
        
        contentPane.add(monitorsPanel, BorderLayout.WEST);

        setContentPane(contentPane);

        JavaCityMap javaCityMap = new JavaCityMap(mapPanel); // Initialize the map with the panel
    }

    private void startSimulation() {
        VehicleDataLoader vehicleDataLoader = new VehicleDataLoader();
        RentalDataLoader rentalDataLoader = new RentalDataLoader();

        List<Vehicle> listOfVehicles = null;
        List<Rental> listOfRentals = null;
        List<Passenger> listOfPassengers = new ArrayList<>();

        Random random = new Random();

        ConfigFileCreator configFileCreator = new ConfigFileCreator();
        configFileCreator.createConfigFile();

        try {
            listOfVehicles = vehicleDataLoader.loadVehicles("PJ2 - projektni zadatak 2024 - Prevozna sredstva.csv");
            for (var vehicle : listOfVehicles) {
                appendToTextArea(vehicle.toString());
            }

        } catch (IOException | ParseException ex) {
            appendToTextArea(ex.toString());
        }

        try {
            listOfRentals = rentalDataLoader.loadRentals("PJ2 - projektni zadatak 2024 - Iznajmljivanja.csv");
            for (var rental : listOfRentals) {
                appendToTextArea(rental.toString());
            }

        } catch (IOException | ParseException ex) {
            appendToTextArea(ex.toString());
        }

        Map<String, Vehicle> vehicleIDtoVehicle = new HashMap<>();
        for (var vehicle : listOfVehicles) {
            vehicleIDtoVehicle.put(vehicle.getVehicleId(), vehicle);
        }

        for (var rental : listOfRentals) {
            String idFromRental = rental.getVehicleId();
            Vehicle vehicle = vehicleIDtoVehicle.get(idFromRental);

            if (vehicle != null) {
                vehicle.setStartPositionX(rental.getVehicleStartPositionX());
                vehicle.setStartPositionY(rental.getVehicleStartPositionY());
                vehicle.setPositionX(rental.getVehicleStartPositionX());
                vehicle.setPositionY(rental.getVehicleStartPositionY());
                vehicle.setDestinationPositionX(rental.getVehicleDestinationPositionX());
                vehicle.setDestinationPositionY(rental.getVehicleDestinationPositionY());
                vehicle.setDuration(rental.getDuration());
                vehicle.setMalfunction(rental.isMalfunction());

                if (vehicle.isMalfunction()) {
                    appendToTextArea("Malfunctioned Vehicle!!");
                    vehicle.setMalfunctionModel(new Malfunction(Malfunction.MALFUNCTION_MESSAGE, rental.getDate(), vehicle));
                    if ("automobil".equals(vehicle.type)) {
                        RentalRepairmentCostsMonitor.carRepairsTotal += 
                                RentalRepairmentCostsMonitor.CAR_REPAIR_COST * vehicle.getPurchasePrice();
                                RentalRepairmentCostsMonitor.cars.add(vehicle); // u skladu sa brojem indeksa
                    } else if ("bicikl".equals(vehicle.type)) {
                        RentalRepairmentCostsMonitor.bikeRepairsTotal += 
                                RentalRepairmentCostsMonitor.BIKE_REPAIR_COST * vehicle.getPurchasePrice();
                        		RentalRepairmentCostsMonitor.bikes.add(vehicle); // u skladu sa brojem indeksa 
                    } else {
                        RentalRepairmentCostsMonitor.scooterRepairsTotal += 
                                RentalRepairmentCostsMonitor.SCOOTER_REPAIR_COST * vehicle.getPurchasePrice();
                        		RentalRepairmentCostsMonitor.scooters.add(vehicle); // u skladu sa brojem indeksa
                    }
                }

                if (vehicle.getState() == Thread.State.NEW) {
                    Passenger passenger = null;
                    int temp = random.nextInt(100);
                    if (temp % 2 == 0) {
                        passenger = new Local(rental.getUserName(), "PASSENGER" + passengerCounter, "ADDRESS" + passengerCounter);
                        listOfPassengers.add(passenger);
                    } else {
                        passenger = new Stranger(rental.getUserName(), "PASSENGER" + passengerCounter, "ADDRESS" + passengerCounter);
                        listOfPassengers.add(passenger);
                    }
                    vehicle.addPassenger(passenger);
                    JavaCityMap.updateCell(vehicle.getPositionX(), vehicle.getPositionY(), vehicle);
                    appendToTextArea(vehicle.toString());
                    vehicle.start();
                } else {
                    appendToTextArea("Vehicle thread already started or finished: " + vehicle.getVehicleId());
                }

                try {
                    vehicle.join();
                } catch (InterruptedException ex) {
                    appendToTextArea(ex.toString());
                }
            }
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            appendToTextArea(ex.toString());
        }

        appendToTextArea("Bills: ");
        for (var passenger : listOfPassengers) {
            appendToTextArea(passenger.bill + "\n");
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            appendToTextArea(ex.toString());
        }

        updateMonitorPanels();
    }

    private void appendToTextArea(String text) {
        EventQueue.invokeLater(() -> textArea.append(text + "\n"));
    }

    private void updateMonitorPanels() {
        EventQueue.invokeLater(() -> {
            salesMonitorTextArea.setText("Sales Monitor: " + rentalSalesMonitor);
            salaryMonitorTextArea.setText("Salary Monitor: " + rentalSalaryMonitor);
            repairCostsMonitorTextArea.setText("Repair Costs Monitor: " + rentalRepairementCostsMonitor);

            List<Vehicle> listOfMostLossMakingVehicleType = RentalRepairmentCostsMonitor.getmostLossMakingVehicleType();
            Serializer.serializeVehicleList(listOfMostLossMakingVehicleType);

            // Deserijalizacija liste vozila
            listOfMostLossMakingVehicleType = Deserializer.deserializeVehicleList();

            // Prikaz novog prozora sa deserijalizovanim podacima
            LossMakingVehiclesWindow lossMakingVehiclesWindow = new LossMakingVehiclesWindow(listOfMostLossMakingVehicleType);
            lossMakingVehiclesWindow.setVisible(true);
        });
    }
}
