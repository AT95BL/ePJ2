package malfunction;

import java.util.Date;

import model.Vehicle;

public class Malfunction {
	public static final String MALFUNCTION_MESSAGE = "MALFUNCTION OCCURED!!";
	
	public String malfunctionDescription;	//	opis kvara
	public Date malfunctionHappenDate;		//	datum javljanja-detekcije kvara
	public Vehicle brokenVehicle;			//	pokvareno vozilo
	
	// Default Constructor
	public Malfunction() {
		this.malfunctionDescription=null;
		this.malfunctionHappenDate=null;
		this.brokenVehicle=null;
	}
	
	// Constructor
	public Malfunction(String malfunctionDescription, Date malfunctionHappenDate, Vehicle brokenVehicle) {
		this.malfunctionDescription=malfunctionDescription;
		this.malfunctionHappenDate=malfunctionHappenDate;
		this.brokenVehicle=brokenVehicle;
	}
	
	// getters/setters
	// Setter za opis kvara
    public void setMalfunctionDescription(String malfunctionDescription) {
        this.malfunctionDescription = malfunctionDescription;
    }

    // Getter za datum javljanja-detekcije kvara
    public Date getMalfunctionHappenDate() {
        return malfunctionHappenDate;
    }

    // Setter za datum javljanja-detekcije kvara
    public void setMalfunctionHappenDate(Date malfunctionHappenDate) {
        this.malfunctionHappenDate = malfunctionHappenDate;
    }

    // Getter za pokvareno vozilo
    public Vehicle getBrokenVehicle() {
        return brokenVehicle;
    }

    // Setter za pokvareno vozilo
    public void setBrokenVehicle(Vehicle brokenVehicle) {
        this.brokenVehicle = brokenVehicle;
    }
	
	// Overrides
	@Override
	public String toString() {
		return "Malfunction Description: " + this.malfunctionDescription + "\n"
				+ "Date Occured: " + this.malfunctionHappenDate + "\n"
				+ this.brokenVehicle;
	}
}
/*
 * 1) Za sada, kvar ima opis, datum javljanja i vozilo
 * 2) Exception handlers nisu implementirani!!
 * 3) Zbog 2) nema provjere ispravnosti!1
 * 4) Još nije gotovo..
 * 5) klasa je napravljena samo kako bi se kompletirali atributi klase Vehicle ..za sad ..
 * */
