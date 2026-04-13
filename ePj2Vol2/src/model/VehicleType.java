package model;

/**
 * Enum representing the supported types of vehicles in the fleet.
 * Replaces the raw string literals previously scattered throughout the codebase
 * (e.g. "automobil", "bicikl", "trotinet").
 */
public enum VehicleType {
    CAR("car"),
    BIKE("bike"),
    SCOOTER("scooter");

    private final String label;

    VehicleType(String label) {
        this.label = label;
    }

    /** Human-readable label used in display output. */
    public String getLabel() {
        return label;
    }

    /**
     * Parses a raw CSV token (possibly in the original language) into a {@code VehicleType}.
     *
     * @param raw the raw string from the data file
     * @return the matching VehicleType
     * @throws IllegalArgumentException if the token does not map to a known type
     */
    public static VehicleType fromRaw(String raw) {
        return switch (raw.toLowerCase().trim()) {
            case "car", "automobil"  -> CAR;
            case "bike", "bicikl"   -> BIKE;
            case "scooter", "trotinet" -> SCOOTER;
            default -> throw new IllegalArgumentException("Unknown vehicle type: " + raw);
        };
    }
}
