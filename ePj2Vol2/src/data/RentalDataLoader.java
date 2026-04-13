package data;

import rental.Rental;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Reads rental records from a CSV file, validates them, and returns a
 * date-sorted {@link Rental} list.
 *
 * <p>Duplicate records (same date AND vehicle ID) are silently skipped.
 */
public class RentalDataLoader {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("d.M.yyyy HH:mm");
    private static final int EXPECTED_FIELD_COUNT = 8;

    public List<Rental> loadRentals(String filePath) throws IOException, ParseException {
        List<Rental> rentals = new ArrayList<>();
        Set<Date>   seenDates      = new HashSet<>();
        Set<String> seenVehicleIds = new HashSet<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            reader.readLine(); // skip header
            String line;
            while ((line = reader.readLine()) != null) {
                // Split on commas that are not inside quoted fields
                String[] fields = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");

                if (fields.length != EXPECTED_FIELD_COUNT) {
                    System.err.println("Skipping malformed record (wrong field count): " + line);
                    continue;
                }

                Date   date      = DATE_FORMAT.parse(fields[0]);
                String vehicleId = fields[2];

                if (seenDates.contains(date) && seenVehicleIds.contains(vehicleId)) {
                    System.err.println("Skipping duplicate (date + vehicleId): " + line);
                    continue;
                }
                seenDates.add(date);
                seenVehicleIds.add(vehicleId);

                String  userName      = fields[1];
                String  startLocation = fields[3].replace("\"", "");
                String  endLocation   = fields[4].replace("\"", "");
                int     duration      = Integer.parseInt(fields[5]);
                boolean malfunction   = fields[6].equalsIgnoreCase("da") || fields[6].equalsIgnoreCase("yes");
                boolean promotion     = fields[7].equalsIgnoreCase("da") || fields[7].equalsIgnoreCase("yes");

                try {
                    rentals.add(new Rental(date, userName, vehicleId, startLocation,
                                           endLocation, duration, malfunction, promotion));
                } catch (IllegalArgumentException ex) {
                    System.err.println("Skipping invalid record: " + line + " — " + ex.getMessage());
                }
            }
        }

        rentals.sort(Comparator.comparing(Rental::getDate));
        return rentals;
    }
}
