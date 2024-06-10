package utility;

import java.io.*;
import java.util.List;
import model.Vehicle;

/**
 * The Deserializer class provides functionality to deserialize a list of Vehicle objects
 * from a file. The deserialized file is read from a specified folder with a specified name.
 */
public class Deserializer {
	/** The name of the folder where the serialized file is stored. */
    public static String FOLDER_NAME = "Reports";
    
    /** The name of the file from which the list of Vehicle objects will be deserialized. */
    public static String FILE_NAME = "MostLossMakingVehicles.ser";
    
    /**
     * Deserializes a list of Vehicle objects from a file.
     *
     * @return the deserialized list of Vehicle objects
     */
    public static List<Vehicle> deserializeVehicleList() {
        List<Vehicle> vehicleList = null;
        try {
        	// Create the file path
            String filePath = FOLDER_NAME + File.separator + FILE_NAME;

            // Open an input stream from the file
            FileInputStream fileIn = new FileInputStream(filePath);
            ObjectInputStream in = new ObjectInputStream(fileIn);

            // Deserialize the list of vehicles
            vehicleList = (List<Vehicle>) in.readObject();

            // Close the streams
            in.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return vehicleList;
    }
}
