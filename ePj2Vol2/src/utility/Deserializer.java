package utility;

import model.Vehicle;

import java.io.*;
import java.util.List;

/**
 * Deserializes a {@link Vehicle} list that was previously written by {@link Serializer}.
 */
public class Deserializer {

    public static final String FOLDER_NAME = "Reports";
    public static final String FILE_NAME   = "MostLossMakingVehicles.ser";

    @SuppressWarnings("unchecked")
    public static List<Vehicle> deserializeVehicleList() {
        String filePath = FOLDER_NAME + File.separator + FILE_NAME;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
            return (List<Vehicle>) in.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
