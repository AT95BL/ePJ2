package passenger;

import utility.RandomStringGenerator;

/**
 * A resident passenger identified by a national ID card.
 */
public class Local extends Passenger {

    private final String nationalId;

    public Local(String id, String name, String address) {
        super(id, name, address);
        this.nationalId = RandomStringGenerator.generateRandomString(10);
    }

    public String getNationalId() { return nationalId; }

    @Override
    public String getIdentificationDocument() {
        return "National ID: " + nationalId;
    }

    @Override
    public String toString() {
        return super.toString() + "\nNational ID  : " + nationalId;
    }
}
