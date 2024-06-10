package utility;

import java.io.*;
import java.util.List;
import model.Vehicle;

/**
 * @author AT95
 * @version 1
 * The Serializer class provides functionality to serialize a list of Vehicle objects
 * to a file. The serialized file is stored in a specified folder with a specified name.
 */
public class Serializer {
	/** The name of the folder where the serialized file will be stored. */
	public static String FOLDER_NAME = "Reports";
	
	/** The name of the file where the list of Vehicle objects will be serialized. */
	public static String FILE_NAME = "MostLossMakingVehicles.ser";
	
	/**
     * Serializes a list of Vehicle objects to a file.
     *
     * @param vehicleList the list of Vehicle objects to be serialized
     */
    public static void serializeVehicleList(List<Vehicle> vehicleList) {
        try {
        	// Create the folder if it does not exist
            File folder = new File(FOLDER_NAME);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            // Create the file path
            String filePath = FOLDER_NAME + File.separator + FILE_NAME;

            // Open an output stream to the file
            FileOutputStream fileOut = new FileOutputStream(filePath);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            // Serialize the list of vehicles
            out.writeObject(vehicleList);

            // Close the streams
            out.close();
            fileOut.close();

            System.out.println("Lista vozila je uspješno serijalizovana u datoteku " + FILE_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}