package utility;

import java.io.*;
import java.util.List;
import model.Vehicle;

public class Deserializer {
    public static String FOLDER_NAME = "Reports";
    public static String FILE_NAME = "MostLossMakingVehicles.ser";

    public static List<Vehicle> deserializeVehicleList() {
        List<Vehicle> vehicleList = null;
        try {
            // Kreiranje putanje do datoteke
            String filePath = FOLDER_NAME + File.separator + FILE_NAME;

            // Otvaranje ulaznog toka iz datoteke
            FileInputStream fileIn = new FileInputStream(filePath);
            ObjectInputStream in = new ObjectInputStream(fileIn);

            // Deserijalizacija liste vozila
            vehicleList = (List<Vehicle>) in.readObject();

            // Zatvaranje toka
            in.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return vehicleList;
    }
}
