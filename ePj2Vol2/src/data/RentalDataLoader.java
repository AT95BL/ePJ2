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

/**
 * @author AT95
 * @version 1
 * The {@code RentalDataLoader} class is responsible for loading rental data from a CSV file.
 * It parses the file, validates the records, and returns a sorted list of {@code Rental} objects.
 * The class ensures that each rental record has a unique date and vehicle ID combination.
 * 
 * <p>
 * Example usage:
 * <pre>
 * {@code
 * RentalDataLoader rentalDataLoader = new RentalDataLoader();
 * List<Rental> rentals = rentalDataLoader.loadRentals("rentals.csv");
 * for (Rental rental : rentals) {
 *     System.out.println(rental);
 * }
 * }
 * </pre>
 * </p>
 * 
 * @see Rental
 */
public class RentalDataLoader {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("d.M.yyyy HH:mm");
    
    /**
     * Loads rental data from the specified CSV file.
     * 
     * @param filePath the path to the CSV file containing rental data
     * @return a list of {@code Rental} objects sorted by date
     * @throws IOException if an I/O error occurs reading from the file
     * @throws ParseException if a date in the file cannot be parsed
     */
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
    
    /*
    public static void main(String[] args) {
    	RentalDataLoader rentalDataLoader = new RentalDataLoader();
    	List<Rental> rentals = new ArrayList<>();
    	try {
    		rentals = rentalDataLoader.loadRentals("PJ2 - projektni zadatak 2024 - Iznajmljivanja.csv");
    	}catch(IOException | ParseException ex) {
    		System.err.println(ex);
    	}
    	
    	for(var v: rentals) {
    		System.out.println(v);
    	}
    }
    */
}