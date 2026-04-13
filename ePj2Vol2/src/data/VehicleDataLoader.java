package data;

import model.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Reads vehicle records from a CSV file and produces a typed {@link Vehicle} list.
 *
 * <p>Expected CSV columns (1-indexed):
 * <ol>
 *   <li>ID</li>
 *   <li>Manufacturer</li>
 *   <li>Model</li>
 *   <li>Purchase date (dd.MM.yyyy.)</li>
 *   <li>Purchase price</li>
 *   <li>Range / max-speed</li>
 *   <li>Max speed</li>
 *   <li>Description</li>
 *   <li>Type (car / bike / scooter, also accepts original language tokens)</li>
 * </ol>
 */
public class VehicleDataLoader {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy.");

    /**
     * Loads and returns all vehicles from the given file path.
     *
     * @param filePath path to the CSV file
     * @return list of {@link Vehicle} objects
     * @throws IOException    on I/O failure
     * @throws ParseException if a date field cannot be parsed
     */
    public List<Vehicle> loadVehicles(String filePath) throws IOException, ParseException {
        List<Vehicle> vehicles = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            reader.readLine(); // skip header row
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");

                String id           = fields[0];
                String manufacturer = fields[1];
                String model        = fields[2];
                Date   purchaseDate = fields[3].isEmpty() ? null : DATE_FORMAT.parse(fields[3]);
                double purchasePrice       = fields[4].isEmpty() ? 0 : Double.parseDouble(fields[4]);
                double autonomyOrMaxSpeed  = fields[5].isEmpty() ? 0 : Double.parseDouble(fields[5]);
                double maxSpeed            = fields[6].isEmpty() ? 0 : Double.parseDouble(fields[6]);
                String description  = fields[7];
                String rawType      = fields[8];

                VehicleType vehicleType;
                try {
                    vehicleType = VehicleType.fromRaw(rawType);
                } catch (IllegalArgumentException ex) {
                    System.err.println("Skipping unknown vehicle type '" + rawType + "' in: " + line);
                    continue;
                }

                Vehicle vehicle = switch (vehicleType) {
                    case CAR     -> new Car    (id, manufacturer, model, purchaseDate, purchasePrice, autonomyOrMaxSpeed, maxSpeed, description, vehicleType);
                    case BIKE    -> new Bike   (id, manufacturer, model, purchaseDate, purchasePrice, autonomyOrMaxSpeed, maxSpeed, description, vehicleType);
                    case SCOOTER -> new Scooter(id, manufacturer, model, purchaseDate, purchasePrice, autonomyOrMaxSpeed, maxSpeed, description, vehicleType);
                };
                vehicles.add(vehicle);
            }
        }
        return vehicles;
    }
}
