package utility;

import java.io.*;
import java.util.List;
import model.Vehicle;

public class Serializer {
	public static String FOLDER_NAME = "Reports";
	public static String FILE_NAME = "MostLossMakingVehicles.ser";
	
    public static void serializeVehicleList(List<Vehicle> vehicleList) {
        try {
            // Stvaranje foldera ako ne postoji
            File folder = new File(FOLDER_NAME);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            // Stvaranje putanje do datoteke
            String filePath = FOLDER_NAME + File.separator + FILE_NAME;

            // Otvaranje izlaznog toka prema datoteci
            FileOutputStream fileOut = new FileOutputStream(filePath);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            // Serijalizacija liste vozila
            out.writeObject(vehicleList);

            // Zatvaranje toka
            out.close();
            fileOut.close();

            System.out.println("Lista vozila je uspješno serijalizovana u datoteku " + FILE_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}