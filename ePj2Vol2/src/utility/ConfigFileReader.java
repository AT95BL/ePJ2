package utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;

/**
 * Reads key-value configuration properties from {@code config.properties}.
 * Implements {@link Serializable} because {@code Vehicle} (which holds a reference
 * to this reader) is itself serializable.
 */
public class ConfigFileReader implements Serializable {

    private Properties properties;

    public ConfigFileReader() {
        properties = new Properties();
        try (FileInputStream input = new FileInputStream("config.properties")) {
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Returns the value for {@code key}, or {@code null} if not found.
     */
    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
