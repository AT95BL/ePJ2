package utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigFileReader {
    public static void main(String[] args) {
        Properties properties = new Properties();

        try (FileInputStream input = new FileInputStream("config.properties")) {
            properties.load(input);

            // Čitanje vrijednosti svojstava
            String carUnitPrice = properties.getProperty("CAR_UNIT_PRICE");
            String bikeUnitPrice = properties.getProperty("BIKE_UNIT_PRICE");
            String scooterUnitPrice = properties.getProperty("SCOOTER_UNIT_PRICE");
            String distanceNarrow = properties.getProperty("DISTANCE_NARROW");
            String distanceWide = properties.getProperty("DISTANCE_WIDE");
            String discount = properties.getProperty("DISCOUNT");
            String discountProm = properties.getProperty("DISCOUNT_PROM");

            // Ispisivanje učitanih vrijednosti
            System.out.println("CAR_UNIT_PRICE: " + carUnitPrice);
            System.out.println("BIKE_UNIT_PRICE: " + bikeUnitPrice);
            System.out.println("SCOOTER_UNIT_PRICE: " + scooterUnitPrice);
            System.out.println("DISTANCE_NARROW: " + distanceNarrow);
            System.out.println("DISTANCE_WIDE: " + distanceWide);
            System.out.println("DISCOUNT: " + discount);
            System.out.println("DISCOUNT_PROM: " + discountProm);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}

