package test;

import data.RentalDataLoader;
import rental.Rental;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class TestRentalDataLoader {
	
    public static void main(String[] args) {
        RentalDataLoader loader = new RentalDataLoader();
        try {
            List<Rental> rentals = loader.loadRentals("C:\\Users\\Korisnik.DESKTOP-JVOQTMK\\Desktop\\pj2_project\\PJ2 - projektni zadatak 2024 - Iznajmljivanja.csv");
            for (Rental rental : rentals) {
                System.out.println(rental);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}