package battery;

/**
 * @author AT95
 * @version 1
 * The {@code BatteryException} class represents exceptions specific to battery operations.
 * It extends the {@code Exception} class and provides constructors for creating exceptions
 * with or without a custom message.
 * 
 * <p>
 * Example usage:
 * <pre>
 * {@code
 * try {
 *     Battery battery = new Battery(500); // This will throw a BatteryException
 * } catch (BatteryException e) {
 *     System.out.println(e.getMessage());
 * }
 * }
 * </pre>
 * </p>
 * 
 * @see Exception
 */
public class BatteryException extends Exception {
	
	/**
	 * Constructs a new {@code BatteryException} with {@code null} as its detail message.
	 */
	public BatteryException() {
		super();
	}
	
	/**
	 * Constructs a new {@code BatteryException} with the specified detail message.
	 * 
	 * @param message the detail message
	 */
	public BatteryException(String message) {
		super(message);
	}
}
