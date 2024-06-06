package bill;

import passenger.Passenger;

public class Bill {
    private double total;
    private String vehicleType;
    private Passenger passenger;
    private boolean malfunction;

    public Bill(double total, String vehicleType, Passenger passenger, boolean malfunction) {
        this.total = malfunction ? 0 : total;
        this.vehicleType = vehicleType;
        this.passenger = passenger;
        this.malfunction = malfunction;
    }

    @Override
    public String toString() {
        return "Bill Information:\n" +
                "Total: $" + String.format("%.2f", total) + "\n" +
                "Vehicle Type: " + vehicleType + "\n" +
                "Passenger: " + passenger.getName() + "\n" +
                "Malfunction: " + (malfunction ? "Yes" : "No") + 
                "\n"; 
    }
}
