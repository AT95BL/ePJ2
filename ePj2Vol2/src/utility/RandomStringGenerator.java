package utility;

import java.util.Random;

/**
 * Generates random alphanumeric strings, used for driving licences and ID numbers.
 */
public class RandomStringGenerator {

    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final Random RANDOM   = new Random();

    /**
     * Returns a random alphanumeric string of the requested length.
     *
     * @param length number of characters
     * @return the generated string
     */
    public static String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return sb.toString();
    }
}
