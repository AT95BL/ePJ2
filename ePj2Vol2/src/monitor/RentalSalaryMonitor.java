package monitor;

import java.util.concurrent.atomic.DoubleAdder;

/**
 * Tracks revenue figures across all concurrent rental threads.
 *
 * <p>{@link DoubleAdder} is used instead of plain {@code double} fields to provide
 * thread-safe accumulation without synchronization overhead.
 */
public class RentalSalaryMonitor {

    private static final DoubleAdder totalRevenue      = new DoubleAdder();
    private static final DoubleAdder discountRevenue   = new DoubleAdder();
    private static final DoubleAdder promotionRevenue  = new DoubleAdder();

    public static void addTotalRevenue(double amount)     { totalRevenue.add(amount); }
    public static void addDiscountRevenue(double amount)  { discountRevenue.add(amount); }
    public static void addPromotionRevenue(double amount) { promotionRevenue.add(amount); }

    public static double getTotalRevenue()     { return totalRevenue.sum(); }
    public static double getDiscountRevenue()  { return discountRevenue.sum(); }
    public static double getPromotionRevenue() { return promotionRevenue.sum(); }

    @Override
    public String toString() {
        return "=== Rental Salary Monitor ===\n"
             + String.format("Total Revenue    : $%.2f%n", totalRevenue.sum())
             + String.format("Discount Revenue : $%.2f%n", discountRevenue.sum())
             + String.format("Promotion Revenue: $%.2f%n", promotionRevenue.sum());
    }
}
