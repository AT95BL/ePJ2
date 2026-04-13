package battery;

import java.io.Serializable;

/**
 * Represents a rechargeable battery with a fixed capacity and a charge level expressed
 * as a percentage (0–100).
 *
 * <p>Valid capacities are:
 * <ul>
 *   <li>100 — Scooters</li>
 *   <li>200 — Bikes</li>
 *   <li>300 — Cars</li>
 * </ul>
 *
 * <p>Example:
 * <pre>{@code
 * Battery b = new Battery(100);
 * b.charge(10);
 * b.discharge(5);
 * System.out.println(b);
 * }</pre>
 *
 * @see BatteryException
 */
public class Battery implements Serializable {

    private static final String INVALID_CAPACITY_MESSAGE =
            "Battery capacity must be 100 (Scooter), 200 (Bike), or 300 (Car).";
    private static final String INVALID_STATUS_MESSAGE =
            "Battery status must be between 0.0 and 100.0.";

    /** Maximum energy capacity; determines the vehicle class. */
    private double capacity;

    /** Current charge level as a percentage (0–100). Starts fully charged. */
    private double chargeLevel = 100;

    /** Default no-arg constructor. */
    public Battery() {}

    /**
     * Creates a {@code Battery} with the given capacity.
     *
     * @param capacity must be 100, 200, or 300
     * @throws BatteryException if the capacity is not one of the allowed values
     */
    public Battery(double capacity) throws BatteryException {
        if (capacity != 100 && capacity != 200 && capacity != 300) {
            throw new BatteryException(INVALID_CAPACITY_MESSAGE);
        }
        this.capacity = capacity;
    }

    // -------------------------------------------------------------------------
    // Behaviour
    // -------------------------------------------------------------------------

    /**
     * Increases the charge level by {@code amount}, capped at 100 %.
     *
     * @param amount the percentage points to add; must be ≥ 0
     * @throws IllegalArgumentException if {@code amount} is negative
     */
    public void charge(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Charge amount cannot be negative.");
        }
        // BUG FIX: was `this.chargeLevel += 100` — should add the argument, not a literal.
        this.chargeLevel = Math.min(this.chargeLevel + amount, 100);
    }

    /**
     * Decreases the charge level by {@code amount}, floored at 0 %.
     *
     * @param amount the percentage points to subtract; must be ≥ 0
     * @throws IllegalArgumentException if {@code amount} is negative
     */
    public void discharge(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Discharge amount cannot be negative.");
        }
        this.chargeLevel = Math.max(this.chargeLevel - amount, 0);
    }

    // -------------------------------------------------------------------------
    // Accessors
    // -------------------------------------------------------------------------

    public double getCapacity() { return capacity; }

    public void setCapacity(double capacity) throws BatteryException {
        if (capacity != 100 && capacity != 200 && capacity != 300) {
            throw new BatteryException(INVALID_CAPACITY_MESSAGE);
        }
        this.capacity = capacity;
    }

    public double getChargeLevel() { return chargeLevel; }

    /**
     * Directly sets the charge level.
     *
     * @param chargeLevel a value in the range [0, 100]
     * @throws BatteryException if the value is out of range
     */
    public void setChargeLevel(double chargeLevel) throws BatteryException {
        // BUG FIX: original condition `0 < status && status >= 100` was logically impossible.
        if (chargeLevel < 0 || chargeLevel > 100) {
            throw new BatteryException(INVALID_STATUS_MESSAGE);
        }
        this.chargeLevel = chargeLevel;
    }

    @Override
    public String toString() {
        return String.format("Battery [capacity=%.0f, charge=%.1f%%]", capacity, chargeLevel);
    }
}
