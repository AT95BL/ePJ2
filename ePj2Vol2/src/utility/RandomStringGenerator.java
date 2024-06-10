package utility;

import java.util.Random;

/**
 * @author AT95
 * @version 1
 * Utility class for generating random strings.
 */
public class RandomStringGenerator {
	
	/**
     * Generates a random string of specified length using alphanumeric characters.
     *
     * @param length The length of the random string to generate.
     * @return A randomly generated string.
     */
    public static String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            sb.append(randomChar);
        }

        return sb.toString();
    }
}
