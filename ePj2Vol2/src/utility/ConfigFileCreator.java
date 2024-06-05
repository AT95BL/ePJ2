package utility;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class ConfigFileCreator {
    public void createConfigFile() {
        Properties properties = new Properties();
        
        // Postavljanje vrijednosti svojstava
        properties.setProperty("CAR_UNIT_PRICE", "0.05");
        properties.setProperty("BIKE_UNIT_PRICE", "0.02");
        properties.setProperty("SCOOTER_UNIT_PRICE", "0.01");
        properties.setProperty("DISTANCE_NARROW", "1.0");
        properties.setProperty("DISTANCE_WIDE", "1.5");
        properties.setProperty("DISCOUNT", "0.1");
        properties.setProperty("DISCOUNT_PROM", "0.05");

        // Pokušaj stvaranja datoteke i upisa svojstava u nju
        try (OutputStream output = new FileOutputStream("config.properties")) {
            properties.store(output, "Konfiguracijska datoteka");
            System.out.println("Datoteka 'config.properties' je uspješno kreirana.");
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}
