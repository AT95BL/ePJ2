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

    // Nove varijable					
    private int startX;
    private int startY;
    private int endX;
    private int endY;

    // Konstruktor
    public Rental(
            Date date, 
            String user, 
            String vehicleId, 
            String startLocation, 
            String endLocation, 
            int duration, 
            boolean malfunction, 
            boolean promotion) throws IllegalArgumentException {
        this.date = date;
        this.user = user;
        this.vehicleId = vehicleId;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.duration = duration;
        this.malfunction = malfunction;
        this.promotion = promotion;

        // Validacija i parsiranje koordinata
        this.startX = parseCoordinate(startLocation, "start");
        this.startY = parseCoordinate(startLocation, "start");
        this.endX = parseCoordinate(endLocation, "end");
        this.endY = parseCoordinate(endLocation, "end");
    }

    // Metoda za parsiranje koordinata
    private int parseCoordinate(String location, String type) throws IllegalArgumentException {
        String[] coords = location.split(",");
        if (coords.length != 2) {
            throw new IllegalArgumentException("Invalid " + type + " location: " + location);
        }
        try {
            return Integer.parseInt(coords[type.equals("start") ? 0 : 1].trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid coordinates in " + type + " location: " + location, e);
        }
    }

    // Getteri za nove varijable
    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public int getEndX() {
        return endX;
    }

    public int getEndY() {
        return endY;
    }
    
    public Date getDate() {
    	return this.date;
    }

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
                ", startX=" + startX +
                ", startY=" + startY +
                ", endX=" + endX +
                ", endY=" + endY +
                '}';
    }
}
