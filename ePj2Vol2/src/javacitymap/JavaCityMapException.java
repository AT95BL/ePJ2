package javacitymap;

/**
 * @author AT95
 * @version 1
 * The {@code JavaCityMapException} class represents a custom exception that is thrown
 * when an error occurs within the JavaCityMap application.
 * 
 * <p>
 * This exception can be used to signal various error conditions specific to the
 * JavaCityMap functionality.
 * </p>
 * 
 * <p>
 * Example usage:
 * <pre>
 * {@code
 * if (someErrorCondition) {
 *     throw new JavaCityMapException("An error occurred in the JavaCityMap.");
 * }
 * }
 * </pre>
 * </p>
 */
public class JavaCityMapException extends Exception{
	
	/**
     * Constructs a new {@code JavaCityMapException} with {@code null} as its detail message.
     */
	public JavaCityMapException() {
		super();
	}
	
	/**
     * Constructs a new {@code JavaCityMapException} with the specified detail message.
     * 
     * @param message the detail message
     */
	public JavaCityMapException(String message){
		super(message);
	}
}
