package gui;

import data.RentalDataLoader;
import data.VehicleDataLoader;
import javacitymap.JavaCityMap;
import malfunction.Malfunction;
import model.Vehicle;
import monitor.RentalRepairCostsMonitor;
import monitor.RentalSalaryMonitor;
import monitor.RentalSalesMonitor;
import passenger.Local;
import passenger.Passenger;
import passenger.Stranger;
import rental.Rental;
import utility.ConfigFileCreator;
import utility.Deserializer;
import utility.Serializer;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

/**
 * Main window — dark tactical HUD.
 *
 * Layout (no gaps, containers flush against each other):
 *
 * ┌─────────────────────────────────────────────────────────────────┐
 * │■  JAVACITY  FLEET  OPERATIONS                        HH:mm:ss  │  ← title bar
 * ├──────────────┬──────────────────────────┬─────────────────────-┤
 * │■ SALES       │■ CITY MAP                │■ SIM LOG             │
 * ├──────────────┤                          │                      │
 * │■ SALARY      │   20×20 dark grid        │  scrolling log       │
 * ├──────────────┤   vehicles glow          │                      │
 * │■ REPAIRS     │   centred in area        │                      │
 * ├──────────────┴──────────────────────────┴──────────────────────┤
 * │  ● Car  ● Bike  ● Scooter          STATUS     [▶ START]        │  ← bottom bar
 * └─────────────────────────────────────────────────────────────────┘
 */
public class MainWindow extends JFrame {

    private static final long serialVersionUID = 1L;

    private final JTextArea logArea    = AppTheme.logArea();
    private final JTextArea salesArea  = AppTheme.logArea();
    private final JTextArea salaryArea = AppTheme.logArea();
    private final JTextArea repairArea = AppTheme.logArea();
    private final JLabel    statusLabel = new JLabel("READY");
    private final MapPanel  mapPanel    = new MapPanel();

    private static int passengerCounter = 1;

    // ── Entry point ───────────────────────────────────────────────────────────
    public static void main(String[] args) {
        AppTheme.apply();
        EventQueue.invokeLater(() -> {
            try { new MainWindow().setVisible(true); }
            catch (Exception ex) { ex.printStackTrace(); }
        });
    }

    // ── Constructor ───────────────────────────────────────────────────────────
    public MainWindow() {
        AppTheme.apply();
        setTitle("JavaCity — Fleet Operations Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1500, 900);
        setLocationRelativeTo(null);
        setBackground(AppTheme.BG_DEEP);

        JPanel root = new JPanel(new BorderLayout(0, 0));
        root.setBackground(AppTheme.BG_DEEP);

        root.add(buildTitleBar(),  BorderLayout.NORTH);
        root.add(buildCentre(),    BorderLayout.CENTER);
        root.add(buildBottomBar(), BorderLayout.SOUTH);

        setContentPane(root);
        new JavaCityMap(mapPanel);
        initPlaceholders();
    }

    // ── Title bar ─────────────────────────────────────────────────────────────
    private Component buildTitleBar() {
        JPanel bar = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                g.setColor(AppTheme.BG_HEADER);
                g.fillRect(0, 0, getWidth(), getHeight());
                // Bottom accent line
                g.setColor(AppTheme.ACCENT_CYAN_DIM);
                g.fillRect(0, getHeight() - 1, getWidth(), 1);
            }
        };
        bar.setOpaque(false);
        bar.setPreferredSize(new Dimension(0, 44));
        bar.setBorder(new EmptyBorder(0, 18, 0, 18));

        JLabel appTitle = new JLabel("◈   JAVACITY  FLEET  OPERATIONS");
        appTitle.setFont(AppTheme.FONT_HEADING);
        appTitle.setForeground(AppTheme.ACCENT_CYAN);

        JLabel clock = new JLabel();
        clock.setFont(AppTheme.FONT_LABEL);
        clock.setForeground(AppTheme.TEXT_SECONDARY);
        // Tick every second
        new Timer(1000, e ->
            clock.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")))
        ).start();

        bar.add(appTitle, BorderLayout.WEST);
        bar.add(clock,    BorderLayout.EAST);
        return bar;
    }

    // ── Centre area: monitors | map | log ─────────────────────────────────────
    private Component buildCentre() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBackground(AppTheme.BG_DEEP);
        // NO EmptyBorder, NO insets — everything sits flush

        GridBagConstraints g = new GridBagConstraints();
        g.fill    = GridBagConstraints.BOTH;
        g.weighty = 1.0;
        g.insets  = new Insets(0, 0, 0, 0);

        // Left: monitor column (fixed width, no weight)
        g.gridx   = 0;
        g.weightx = 0.0;
        p.add(buildMonitors(), g);

        // Centre: map (takes all remaining width)
        g.gridx   = 1;
        g.weightx = 1.0;
        p.add(buildMapArea(), g);

        // Right: log (fixed width, no weight)
        g.gridx   = 2;
        g.weightx = 0.0;
        p.add(buildLog(), g);

        return p;
    }

    // ── Left column: three monitor panels stacked flush ───────────────────────
    private Component buildMonitors() {
        JPanel col = new JPanel(new GridLayout(3, 1, 0, 0));  // 0 vgap — no gaps
        col.setBackground(AppTheme.BG_DEEP);
        col.setPreferredSize(new Dimension(290, 0));

        col.add(AppTheme.hudPanel("SALES",   AppTheme.scrollPane(salesArea)));
        col.add(AppTheme.hudPanel("SALARY",  AppTheme.scrollPane(salaryArea)));
        col.add(AppTheme.hudPanel("REPAIRS", AppTheme.scrollPane(repairArea)));
        return col;
    }

    // ── Centre: map with centring viewport and thin border ───────────────────
    private Component buildMapArea() {
        // Viewport centres the map grid when the window is bigger than the grid
        JScrollPane scroll = AppTheme.scrollPane(mapPanel);
        scroll.getViewport().setLayout(new CentreLayout());

        // Outer container: hudPanel gives us the "CITY MAP" title header
        return AppTheme.hudPanel("CITY MAP", scroll);
    }

    /**
     * A simple LayoutManager that centres its single child in the available space,
     * expanding the child to fill if the viewport is smaller than the preferred size.
     */
    private static class CentreLayout implements LayoutManager {
        @Override public void addLayoutComponent(String n, Component c) {}
        @Override public void removeLayoutComponent(Component c) {}
        @Override public Dimension preferredLayoutSize(Container p) { return child(p).getPreferredSize(); }
        @Override public Dimension minimumLayoutSize(Container p)   { return child(p).getMinimumSize(); }
        @Override public void layoutContainer(Container p) {
            Component c  = child(p);
            Dimension ps = c.getPreferredSize();
            int pw = p.getWidth(), ph = p.getHeight();
            int cw = Math.max(ps.width,  pw);
            int ch = Math.max(ps.height, ph);
            int ox = pw > ps.width  ? (pw - ps.width)  / 2 : 0;
            int oy = ph > ps.height ? (ph - ps.height) / 2 : 0;
            c.setBounds(ox, oy, cw, ch);
        }
        private static Component child(Container p) { return p.getComponent(0); }
    }

    // ── Right column: simulation log ──────────────────────────────────────────
    private Component buildLog() {
        JPanel col = new JPanel(new BorderLayout(0, 0));
        col.setBackground(AppTheme.BG_DEEP);
        col.setPreferredSize(new Dimension(280, 0));
        col.add(AppTheme.hudPanel("SIM LOG", AppTheme.scrollPane(logArea)));
        return col;
    }

    // ── Bottom bar: legend | status | button ──────────────────────────────────
    private Component buildBottomBar() {
        JPanel bar = new JPanel(new BorderLayout(12, 0)) {
            @Override protected void paintComponent(Graphics g) {
                g.setColor(AppTheme.BG_HEADER);
                g.fillRect(0, 0, getWidth(), getHeight());
                // Top separator line
                g.setColor(AppTheme.BORDER_DIM);
                g.fillRect(0, 0, getWidth(), 1);
            }
        };
        bar.setOpaque(false);
        bar.setPreferredSize(new Dimension(0, 50));
        bar.setBorder(new EmptyBorder(7, 18, 7, 18));

        bar.add(buildLegend(),   BorderLayout.WEST);
        bar.add(buildStatus(),   BorderLayout.CENTER);
        bar.add(buildStartBtn(), BorderLayout.EAST);
        return bar;
    }

    private Component buildLegend() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 0));
        p.setOpaque(false);
        p.add(legendItem(AppTheme.COLOR_CAR,     "Car"));
        p.add(legendItem(AppTheme.COLOR_BIKE,    "Bike"));
        p.add(legendItem(AppTheme.COLOR_SCOOTER, "Scooter"));
        return p;
    }

    private JPanel legendItem(Color colour, String label) {
        // Dot
        JPanel dot = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                ((Graphics2D)g).setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g.setColor(colour);
                g.fillOval(0, 3, 10, 10);
            }
            @Override public Dimension getPreferredSize() { return new Dimension(10, 16); }
        };
        dot.setOpaque(false);

        JLabel lbl = new JLabel(label);
        lbl.setFont(AppTheme.FONT_LABEL);
        lbl.setForeground(AppTheme.TEXT_SECONDARY);

        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        row.setOpaque(false);
        row.add(dot);
        row.add(lbl);
        return row;
    }

    private Component buildStatus() {
        statusLabel.setFont(AppTheme.FONT_LABEL);
        statusLabel.setForeground(AppTheme.ACCENT_CYAN);
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);
        p.add(statusLabel, BorderLayout.CENTER);
        return p;
    }

    private Component buildStartBtn() {
        GlowButton btn = new GlowButton("▶  START SIMULATION");
        btn.addActionListener(e -> new Thread(this::runSimulation).start());
        JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        p.setOpaque(false);
        p.add(btn);
        return p;
    }

    // ── Simulation ────────────────────────────────────────────────────────────
    private void runSimulation() {
        setStatus("⟳  Loading…");
        new ConfigFileCreator().createConfigFile();

        List<Vehicle> vehicles = loadVehicles();
        if (vehicles == null) { setStatus("✗  Failed to load vehicles"); return; }

        List<Rental> rentals = loadRentals();
        if (rentals == null) { setStatus("✗  Failed to load rentals"); return; }

        setStatus("⟳  Running — " + rentals.size() + " rental(s)");

        List<Passenger> passengers = new ArrayList<>();
        Random rng = new Random();
        Map<String, Vehicle> byId = new HashMap<>();
        vehicles.forEach(v -> byId.put(v.getVehicleId(), v));

        for (Rental rental : rentals) {
            Vehicle vehicle = byId.get(rental.getVehicleId());
            if (vehicle == null) continue;

            configureVehicle(vehicle, rental);
            registerMalfunctionCosts(vehicle);

            if (vehicle.getState() == Thread.State.NEW) {
                Passenger p = rng.nextBoolean()
                        ? new Local   (rental.getUserName(), "PASSENGER" + passengerCounter, "ADDRESS" + passengerCounter)
                        : new Stranger(rental.getUserName(), "PASSENGER" + passengerCounter, "ADDRESS" + passengerCounter);
                passengerCounter++;
                passengers.add(p);

                vehicle.addPassenger(p);
                JavaCityMap.updateCell(vehicle.getPositionX(), vehicle.getPositionY(), vehicle);
                log("▸ " + vehicle.getVehicleId()
                        + "  [" + vehicle.getStartPositionX() + "," + vehicle.getStartPositionY() + "]"
                        + " → [" + vehicle.getDestinationPositionX() + "," + vehicle.getDestinationPositionY() + "]");
                vehicle.start();
            } else {
                log("⚠ " + vehicle.getVehicleId() + " — already running/finished");
            }

            try { vehicle.join(); } catch (InterruptedException ex) { log("ERR: " + ex); }
        }

        sleep(1000);
        log("\n══════  BILLS  ══════");
        passengers.forEach(p -> log(p.bill != null ? p.bill.toString() : "(no bill)"));

        sleep(500);
        refreshMonitors();
        setStatus("✔  Complete — " + passengers.size() + " passenger(s) served");
    }

    private List<Vehicle> loadVehicles() {
        try { return new VehicleDataLoader().loadVehicles("PJ2 - projektni zadatak 2024 - Prevozna sredstva.csv"); }
        catch (IOException | ParseException ex) { log("ERR: " + ex); return null; }
    }

    private List<Rental> loadRentals() {
        try { return new RentalDataLoader().loadRentals("PJ2 - projektni zadatak 2024 - Iznajmljivanja.csv"); }
        catch (IOException | ParseException ex) { log("ERR: " + ex); return null; }
    }

    private void configureVehicle(Vehicle vehicle, Rental rental) {
        vehicle.setStartPositionX(rental.getVehicleStartPositionX());
        vehicle.setStartPositionY(rental.getVehicleStartPositionY());
        vehicle.setPositionX(rental.getVehicleStartPositionX());
        vehicle.setPositionY(rental.getVehicleStartPositionY());
        vehicle.setDestinationPositionX(rental.getVehicleDestinationPositionX());
        vehicle.setDestinationPositionY(rental.getVehicleDestinationPositionY());
        vehicle.setDuration(rental.getDuration());
        vehicle.setMalfunction(rental.isMalfunction());
        if (vehicle.isMalfunction()) {
            log("⚡ MALFUNCTION — " + vehicle.getVehicleId());
            vehicle.setMalfunctionModel(
                    new Malfunction(Malfunction.MALFUNCTION_MESSAGE, rental.getDate(), vehicle));
        }
    }

    private void registerMalfunctionCosts(Vehicle vehicle) {
        if (!vehicle.isMalfunction()) return;
        switch (vehicle.getVehicleType()) {
            case CAR     -> { RentalRepairCostsMonitor.addCarRepairCost(    RentalRepairCostsMonitor.CAR_REPAIR_COST     * vehicle.getPurchasePrice()); RentalRepairCostsMonitor.cars.add(vehicle); }
            case BIKE    -> { RentalRepairCostsMonitor.addBikeRepairCost(   RentalRepairCostsMonitor.BIKE_REPAIR_COST    * vehicle.getPurchasePrice()); RentalRepairCostsMonitor.bikes.add(vehicle); }
            case SCOOTER -> { RentalRepairCostsMonitor.addScooterRepairCost(RentalRepairCostsMonitor.SCOOTER_REPAIR_COST * vehicle.getPurchasePrice()); RentalRepairCostsMonitor.scooters.add(vehicle); }
        }
    }

    private void refreshMonitors() {
        EventQueue.invokeLater(() -> {
            salesArea .setForeground(AppTheme.TEXT_PRIMARY);
            salaryArea.setForeground(AppTheme.TEXT_PRIMARY);
            repairArea.setForeground(AppTheme.TEXT_PRIMARY);
            salesArea .setText(new RentalSalesMonitor() .toString());
            salaryArea.setText(new RentalSalaryMonitor() .toString());
            repairArea.setText(new RentalRepairCostsMonitor().toString());

            List<Vehicle> lossy = RentalRepairCostsMonitor.getMostLossMakingVehicleType();
            Serializer.serializeVehicleList(lossy);
            new LossMakingVehiclesWindow(Deserializer.deserializeVehicleList()).setVisible(true);
        });
    }

    private void initPlaceholders() {
        String ph = "─── awaiting simulation ───";
        salesArea .setText(ph); salesArea .setForeground(AppTheme.ACCENT_CYAN_DIM);
        salaryArea.setText(ph); salaryArea.setForeground(AppTheme.ACCENT_CYAN_DIM);
        repairArea.setText(ph); repairArea.setForeground(AppTheme.ACCENT_CYAN_DIM);
    }

    private void log(String text) {
        EventQueue.invokeLater(() -> {
            logArea.setForeground(AppTheme.TEXT_PRIMARY);
            logArea.append(text + "\n");
            logArea.setCaretPosition(logArea.getDocument().getLength());
        });
    }

    private void setStatus(String s) {
        EventQueue.invokeLater(() -> statusLabel.setText(s));
    }

    private void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException ex) { log("ERR: " + ex); }
    }
}
