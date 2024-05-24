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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

public class RentalDataLoader {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("d.M.yyyy HH:mm");

    public List<Rental> loadRentals(String filePath) throws IOException, ParseException {
        List<Rental> rentals = new ArrayList<>();
        HashSet<Date> uniqueDates = new HashSet<>();		 // Set za praćenje jedinstvenih datuma
        HashSet<String> uniqueVehicleIDs = new HashSet<>();  // Set za praćenje jedinstvenih ID
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // Read header
            while ((line = br.readLine()) != null) {
                String[] record = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"); // Split on commas not within quotes
                if (record.length != 8) {
                    System.err.println("Invalid record: " + line);
                    continue;
                }

                Date date = DATE_FORMAT.parse(record[0]);
                String vehicleId = record[2];
                // Provjera je li datum i vehicleID već dodan
                if (uniqueDates.contains(date) && uniqueVehicleIDs.contains(vehicleId)) {
                    System.err.println("Duplicate date && vehicle ID record: " + line);
                    continue;
                } else {
                    uniqueDates.add(date);
                    uniqueVehicleIDs.add(vehicleId);
                    
                }
                String user = record[1];
                
                String startLocation = record[3].replace("\"", ""); 	// Remove quotes
                String endLocation = record[4].replace("\"", "");   	// Remove quotes
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
        
        // Sortiranje liste prije nego što je vratimo
        Collections.sort(rentals, Comparator.comparing(Rental::getDate));
        
        return rentals;
    }
}
