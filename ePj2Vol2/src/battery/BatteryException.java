package battery;

/**
 * Thrown when an invalid operation is performed on a {@link Battery}.
 */
public class BatteryException extends Exception {

    public BatteryException() {
        super();
    }

    public BatteryException(String message) {
        super(message);
    }
}
