package utility;

import model.Vehicle;

import java.io.*;
import java.util.List;

/**
 * Serializes a {@link Vehicle} list to disk for later retrieval by {@link Deserializer}.
 */
public class Serializer {

    public static final String FOLDER_NAME = "Reports";
    public static final String FILE_NAME   = "MostLossMakingVehicles.ser";

    public static void serializeVehicleList(List<Vehicle> vehicleList) {
        try {
            File folder = new File(FOLDER_NAME);
            if (!folder.exists()) folder.mkdirs();

            String filePath = FOLDER_NAME + File.separator + FILE_NAME;
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
                out.writeObject(vehicleList);
                System.out.println("Vehicle list serialized successfully to " + FILE_NAME);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
