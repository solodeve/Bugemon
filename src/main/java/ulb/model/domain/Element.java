package ulb.model.domain;

import java.util.Locale;

/**
 * Enumeration of elemental types for Bugemons and their skills.
 * 
 */
public enum Element {
    FIRE("Fire"),
    ICE("Ice"),
    PLANT("Plant"),
    MECHA("Mecha"),
    LIGHT("Light"),
    SHADOW("Shadow"),
    ALL("All");

    private final String displayName;

    Element(String displayName) {
        this.displayName = displayName;
    }

    public String displayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public static Element fromString(String raw) {
        if (raw == null || raw.isBlank()) {throw new IllegalArgumentException("Element value cannot be null or blank");}

        String normalized = raw.trim().toUpperCase(Locale.ROOT);

        return switch (normalized) {
            case "FIRE" -> FIRE;
            case "ICE" -> ICE;
            case "PLANT" -> PLANT;
            case "MECHA" -> MECHA;
            case "LIGHT" -> LIGHT;
            case "SHADOW" -> SHADOW;
            case "ALL" -> ALL;
            default -> throw new IllegalArgumentException("Unknown element type: " + raw);
        };
    }
}
