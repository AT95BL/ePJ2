package utility;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

/**
 * Creates (or overwrites) the application configuration file with default values.
 */
public class ConfigFileCreator {

    public void createConfigFile() {
        Properties props = new Properties();
        props.setProperty("CAR_UNIT_PRICE",    "0.05");
        props.setProperty("BIKE_UNIT_PRICE",   "0.02");
        props.setProperty("SCOOTER_UNIT_PRICE","0.01");
        props.setProperty("DISTANCE_NARROW",   "1.0");
        props.setProperty("DISTANCE_WIDE",     "1.5");
        props.setProperty("DISCOUNT",          "0.1");
        props.setProperty("DISCOUNT_PROM",     "0.05");

        try (OutputStream out = new FileOutputStream("config.properties")) {
            props.store(out, "Application configuration file");
            System.out.println("config.properties created successfully.");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
