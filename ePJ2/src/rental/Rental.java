package rental;

import java.util.Date;

public class Rental {
    private Date date;
    private String user;
    private String vehicleId;
    private String startLocation;
    private String endLocation;
    private int duration;
    private boolean malfunction;
    private boolean promotion;

    public Rental(Date date, String user, String vehicleId, String startLocation, String endLocation, int duration, boolean malfunction, boolean promotion) {
        this.date = date;
        this.user = user;
        this.vehicleId = vehicleId;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.duration = duration;
        this.malfunction = malfunction;
        this.promotion = promotion;
    }

    // Getters and setters

    @Override
    public String toString() {
        return "Rental{" +
                "date=" + date +
                ", user='" + user + '\'' +
                ", vehicleId='" + vehicleId + '\'' +
                ", startLocation='" + startLocation + '\'' +
                ", endLocation='" + endLocation + '\'' +
                ", duration=" + duration +
                ", malfunction=" + malfunction +
                ", promotion=" + promotion +
                '}';
    }
}
