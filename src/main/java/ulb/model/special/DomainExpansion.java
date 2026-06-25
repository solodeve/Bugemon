package ulb.model.special;

import java.util.ArrayList;
import java.util.List;

import ulb.model.domain.Element;

/**
 * Describes a domain expansion available to a Bugemon during battle.
 * It represents a stat multiplier that a Bugemon can acquier if its type and the domain's are the same.
 */
public record DomainExpansion(String id, String name, String description, String backgroundSpritePath,
                              Element boostedElement, int cost, List<DomainBonus> bonuses) {
    private static final float DEFAULT_DAMAGE_MULTIPLIER = 1.5f;

    public DomainExpansion(
            String id,
            String name,
            String description,
            String backgroundSpritePath,
            Element boostedElement,
            int cost,
            List<DomainBonus> bonuses
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.backgroundSpritePath = backgroundSpritePath;
        this.boostedElement = boostedElement;
        this.cost = cost;
        this.bonuses = bonuses == null ? List.of() : List.copyOf(new ArrayList<>(bonuses));
    }

    public String getBoostedElementDisplayName() {
        return boostedElement == null ? "-" : boostedElement.displayName();
    }

    public float getDamageMultiplier(Element skillElement) {
        if (skillElement == null || boostedElement == null) {
            return 1.0f;
        }
        return boostedElement == skillElement ? DEFAULT_DAMAGE_MULTIPLIER : 1.0f;
    }
}
