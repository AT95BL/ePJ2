package monitor;

public class RentalSalaryMonitor {
    public static double totalSalary;
    public static double discountSalary;
    public static double promotionSalary;

    public static double getTotalSalary() {
        return totalSalary;
    }

    public static void setTotalSalary(double totalSalary) {
        RentalSalaryMonitor.totalSalary = totalSalary;
    }

    public static double getDiscountSalary() {
        return discountSalary;
    }

    public static void setDiscountSalary(double discountSalary) {
        RentalSalaryMonitor.discountSalary = discountSalary;
    }

    public static double getPromotionSalary() {
        return promotionSalary;
    }

    public static void setPromotionSalary(double promotionSalary) {
        RentalSalaryMonitor.promotionSalary = promotionSalary;
    }

    @Override
    public String toString() {
        return "Rental Salary Monitor:\n" +
                "Total Salary: $" + String.format("%.2f", totalSalary) + "\n" +
                "Discount Salary: $" + String.format("%.2f", discountSalary) + "\n" +
                "Promotion Salary: $" + String.format("%.2f", promotionSalary);
    }
}
