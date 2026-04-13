package passenger;

import utility.RandomStringGenerator;

/**
 * A foreign (non-resident) passenger identified by a passport number.
 */
public class Stranger extends Passenger {

    private final String passportNumber;

    public Stranger(String id, String name, String address) {
        super(id, name, address);
        this.passportNumber = RandomStringGenerator.generateRandomString(11);
    }

    public String getPassportNumber() { return passportNumber; }

    @Override
    public String getIdentificationDocument() {
        return "Passport: " + passportNumber;
    }

    @Override
    public String toString() {
        return super.toString() + "\nPassport     : " + passportNumber;
    }
}
