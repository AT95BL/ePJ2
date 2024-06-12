package monitor;

/**
 * @author AT95
 * @version 1
 * Monitor class for tracking various types of salaries related to rentals.
 */
public class RentalSalaryMonitor {
	/** Total salary earned from rentals. */
    public static double totalSalary;
    
    /** Salary earned from discounts. */
    public static double discountSalary;
    
    /** Salary earned from promotions. */
    public static double promotionSalary;
    
    /**
     * Retrieves the total salary.
     *
     * @return The total salary.
     */
    public static double getTotalSalary() {
        return totalSalary;
    }
    
    /**
     * Sets the total salary.
     *
     * @param totalSalary The total salary to set.
     */
    public static void setTotalSalary(double totalSalary) {
        RentalSalaryMonitor.totalSalary = totalSalary;
    }
    
    /**
     * Retrieves the discount salary.
     *
     * @return The discount salary.
     */
    public static double getDiscountSalary() {
        return discountSalary;
    }
    
    /**
     * Sets the discount salary.
     *
     * @param discountSalary The discount salary to set.
     */
    public static void setDiscountSalary(double discountSalary) {
        RentalSalaryMonitor.discountSalary = discountSalary;
    }
    
    /**
     * Retrieves the promotion salary.
     *
     * @return The promotion salary.
     */
    public static double getPromotionSalary() {
        return promotionSalary;
    }
    
    /**
     * Sets the promotion salary.
     *
     * @param promotionSalary The promotion salary to set.
     */
    public static void setPromotionSalary(double promotionSalary) {
        RentalSalaryMonitor.promotionSalary = promotionSalary;
    }
    
    /**
     * Returns a string representation of the rental salary monitor.
     *
     * @return A string representation of the rental salary monitor.
     */
    @Override
    public String toString() {
        return "Rental Salary Monitor:\n" +
                "Total Salary: $" + String.format("%.2f", totalSalary) + "\n" +
                "Discount Salary: $" + String.format("%.2f", discountSalary) + "\n" +
                "Promotion Salary: $" + String.format("%.2f", promotionSalary);
    }
}
