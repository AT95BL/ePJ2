package data;

import rental.Rental;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RentalDataLoader {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("d.M.yyyy HH:mm");

    public List<Rental> loadRentals(String filePath) throws IOException, ParseException {
        List<Rental> rentals = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // Read header
            while ((line = br.readLine()) != null) {
                String[] record = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"); // Split on commas not within quotes
                if (record.length != 8) {
                    System.err.println("Invalid record: " + line);
                    continue;
                }

                Date date = DATE_FORMAT.parse(record[0]);
                String user = record[1];
                String vehicleId = record[2];
                String startLocation = record[3].replace("\"", ""); // Remove quotes
                String endLocation = record[4].replace("\"", "");   // Remove quotes
                int duration = Integer.parseInt(record[5]);
                boolean malfunction = record[6].equalsIgnoreCase("da");
                boolean promotion = record[7].equalsIgnoreCase("da");

                try {
                    rentals.add(new Rental(date, user, vehicleId, startLocation, endLocation, duration, malfunction, promotion));
                } catch (IllegalArgumentException e) {
                    System.err.println("Invalid record: " + line + " - " + e.getMessage());
                }
            }
        }
        return rentals;
    }
}
