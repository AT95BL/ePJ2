package rental;

import malfunction.Malfunction;
import model.Vehicle;

import java.util.Date;

/**
 * Represents a single vehicle rental transaction.
 *
 * <p>On construction the start and end location strings are parsed into
 * integer grid coordinates. Every tenth rental (tracked via {@link #RENTAL_COUNTER})
 * qualifies for a promotional discount.
 */
public class Rental {

    /** Incremented for each successfully constructed rental; used for promo discount eligibility. */
    public static long RENTAL_COUNTER = 1;

    // Core CSV fields
    public Date    date;
    public String  userName;
    public String  vehicleId;
    public String  startLocation;
    public String  endLocation;
    public long    duration;
    public boolean malfunction;
    public boolean promotion;

    // Parsed grid coordinates
    public int startRow;
    public int startCol;
    public int destinationRow;
    public int destinationCol;

    public Malfunction malfunctionModel = null;

    /** Default no-arg constructor. */
    public Rental() {}

    /**
     * Full constructor that also parses location strings into grid coordinates.
     *
     * @throws IllegalArgumentException if either location string is not parseable as "row,col"
     */
    public Rental(
            Date    date,
            String  userName,
            String  vehicleId,
            String  startLocation,
            String  endLocation,
            long    duration,
            boolean malfunction,
            boolean promotion) {

        this.date          = date;
        this.userName      = userName;
        this.vehicleId     = vehicleId;
        this.startLocation = startLocation;
        this.endLocation   = endLocation;
        this.duration      = duration;
        this.malfunction   = malfunction;
        this.promotion     = promotion;

        int[] start = parseCoordinate(startLocation, "start");
        this.startRow = start[0];
        this.startCol = start[1];

        int[] end = parseCoordinate(endLocation, "end");
        this.destinationRow = end[0];
        this.destinationCol = end[1];

        RENTAL_COUNTER++;
    }

    /**
     * Parses a {@code "row,col"} location string into a two-element int array.
     *
     * @param location the location string
     * @param role     "start" or "end" — used only in the error message
     * @return {@code int[]{row, col}}
     * @throws IllegalArgumentException if the string is malformed
     */
    public int[] parseCoordinate(String location, String role) {
        String[] parts = location.split(",");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid " + role + " location: '" + location + "'");
        }
        try {
            return new int[]{Integer.parseInt(parts[0].trim()), Integer.parseInt(parts[1].trim())};
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Non-numeric coordinates in " + role + " location: '" + location + "'", ex);
        }
    }

    // -------------------------------------------------------------------------
    // Accessors — Vehicle positioning (delegates to parsed coordinates)
    // -------------------------------------------------------------------------

    public int getVehicleStartPositionX()  { return startRow; }
    public int getVehicleStartPositionY()  { return startCol; }
    public int getVehicleDestinationPositionX() { return destinationRow; }
    public int getVehicleDestinationPositionY() { return destinationCol; }

    // -------------------------------------------------------------------------
    // Standard accessors
    // -------------------------------------------------------------------------

    public Date   getDate()                  { return date; }
    public void   setDate(Date date)         { this.date = date; }

    public String getUserName()              { return userName; }
    public void   setUserName(String name)   { this.userName = name; }

    public String getVehicleId()             { return vehicleId; }
    public void   setVehicleId(String id)    { this.vehicleId = id; }

    public String getStartLocation()                    { return startLocation; }
    public void   setStartLocation(String startLocation) { this.startLocation = startLocation; }

    public String getEndLocation()                   { return endLocation; }
    public void   setEndLocation(String endLocation) { this.endLocation = endLocation; }

    public long   getDuration()              { return duration; }
    public void   setDuration(long duration) { this.duration = duration; }

    public boolean isMalfunction()                     { return malfunction; }
    public void    setMalfunction(boolean malfunction) { this.malfunction = malfunction; }

    public boolean isPromotion()                    { return promotion; }
    public void    setPromotion(boolean promotion)  { this.promotion = promotion; }

    public Malfunction getMalfunctionModel()                      { return malfunctionModel; }
    public void        setMalfunctionModel(Malfunction m)         { this.malfunctionModel = m; }

    /** Convenience factory for a new {@link Malfunction} tied to this rental's vehicle. */
    public Malfunction createMalfunctionModel(String message, Date when, Vehicle vehicle) {
        return new Malfunction(message, when, vehicle);
    }

    @Override
    public String toString() {
        return "Rental{"
             + "date=" + date
             + ", user='" + userName + '\''
             + ", vehicleId='" + vehicleId + '\''
             + ", start='" + startLocation + '\''
             + ", end='" + endLocation + '\''
             + ", duration=" + duration
             + ", malfunction=" + malfunction
             + ", promotion=" + promotion
             + ", startRow=" + startRow
             + ", startCol=" + startCol
             + ", destRow=" + destinationRow
             + ", destCol=" + destinationCol
             + '}';
    }
}
