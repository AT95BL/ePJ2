package passenger;

import bill.Bill;
import utility.RandomStringGenerator;

import java.io.Serializable;

/**
 * Abstract base for all passengers in the system.
 *
 * <p>Each passenger receives a randomly generated driving licence number on
 * construction, and will have a {@link Bill} assigned once their trip completes.
 */
public abstract class Passenger implements Serializable {

    protected String id;
    protected String name;
    protected String address;
    protected String drivingLicence;

    /** Assigned by the vehicle thread after the trip ends. */
    public Bill bill;

    /**
     * @param id      unique passenger identifier
     * @param name    passenger full name
     * @param address passenger home address
     */
    public Passenger(String id, String name, String address) {
        this.id             = id;
        this.name           = name;
        this.address        = address;
        this.drivingLicence = RandomStringGenerator.generateRandomString(9);
    }

    // -------------------------------------------------------------------------
    // Abstract
    // -------------------------------------------------------------------------

    /** Returns the passenger's primary identification document as a human-readable string. */
    public abstract String getIdentificationDocument();

    // -------------------------------------------------------------------------
    // Accessors
    // -------------------------------------------------------------------------

    public String getId()      { return id; }
    public String getName()    { return name; }
    public String getAddress() { return address; }

    @Override
    public String toString() {
        return "Passenger ID : " + id      + "\n"
             + "Name         : " + name    + "\n"
             + "Address      : " + address;
    }
}
