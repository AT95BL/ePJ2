package utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author AT95
 * @version 1
 * Utility class for reading configuration properties from a file.
 */
public class ConfigFileReader {
    private Properties properties;
    
    /**
     * Constructs a new ConfigFileReader and loads properties from the configuration file.
     */
    public ConfigFileReader() {
        properties = new Properties();
        try (FileInputStream input = new FileInputStream("config.properties")) {
            properties.load(input);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
    
    /**
     * Retrieves the value of the specified property from the configuration file.
     *
     * @param key The key of the property to retrieve.
     * @return The value associated with the specified key, or null if the key is not found.
     */
    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
