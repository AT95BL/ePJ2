package malfunction;

import model.Vehicle;

import java.io.Serializable;
import java.util.Date;

/**
 * Records a single malfunction event: what broke, when it was detected,
 * and which vehicle is affected.
 */
public class Malfunction implements Serializable {

    public static final String MALFUNCTION_MESSAGE = "MALFUNCTION DETECTED";

    private String  description;
    private Date    detectedAt;
    private Vehicle affectedVehicle;

    /** Default constructor — all fields {@code null}. */
    public Malfunction() {}

    /**
     * @param description     human-readable fault description
     * @param detectedAt      timestamp when the fault was detected
     * @param affectedVehicle the vehicle that broke down
     */
    public Malfunction(String description, Date detectedAt, Vehicle affectedVehicle) {
        this.description     = description;
        this.detectedAt      = detectedAt;
        this.affectedVehicle = affectedVehicle;
    }

    // -------------------------------------------------------------------------
    // Accessors
    // -------------------------------------------------------------------------

    public String  getDescription()                       { return description; }
    public void    setDescription(String description)     { this.description = description; }

    public Date    getDetectedAt()                        { return detectedAt; }
    public void    setDetectedAt(Date detectedAt)         { this.detectedAt = detectedAt; }

    public Vehicle getAffectedVehicle()                           { return affectedVehicle; }
    public void    setAffectedVehicle(Vehicle affectedVehicle)    { this.affectedVehicle = affectedVehicle; }

    @Override
    public String toString() {
        return "Malfunction Report\n"
             + "  Description : " + description + "\n"
             + "  Detected at : " + detectedAt  + "\n"
             + "  Vehicle     : " + (affectedVehicle != null ? affectedVehicle.getVehicleId() : "N/A");
    }
}
