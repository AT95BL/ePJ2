package utility;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

/**
 * @author AT95
 * @version 1
 * Utility class for creating a configuration file.
 */
public class ConfigFileCreator {
	
	/**
     * Creates a configuration file with default properties.
     */
    public void createConfigFile() {
        Properties properties = new Properties();
        
        // Setting property values
        properties.setProperty("CAR_UNIT_PRICE", "0.05");
        properties.setProperty("BIKE_UNIT_PRICE", "0.02");
        properties.setProperty("SCOOTER_UNIT_PRICE", "0.01");
        properties.setProperty("DISTANCE_NARROW", "1.0");
        properties.setProperty("DISTANCE_WIDE", "1.5");
        properties.setProperty("DISCOUNT", "0.1");
        properties.setProperty("DISCOUNT_PROM", "0.05");

        // Attempt to create the file and write properties to it
        try (OutputStream output = new FileOutputStream("config.properties")) {
            properties.store(output, "Konfiguracijska datoteka");
            System.out.println("Datoteka 'config.properties' je uspješno kreirana.");
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}
