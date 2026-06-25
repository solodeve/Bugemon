package ulb.model.inventory;

import ulb.model.domain.effect.Effect;
import ulb.repository.GameRepository;

/**
 * Represents a consumable item that the player can use during battle.
 *
 * <p>Items are loaded from the database by
 * {@link GameRepository} and stored in the game catalogue.
 * Each item is immutable (declared as a {@code record}) because item definitions
 * never change at runtime.</p>
 *
 * <p>When a player uses an item during battle, its {@link #effect()} is applied
 * to the active Bugemon via {@link ulb.model.domain.Bugemon#applyEffect(Effect)}.</p>
 *
 * @param id          Unique identifier for this item.
 * @param name        Human-readable display name shown in the inventory UI.
 * @param description Flavour text explaining what the item does.
 * @param type        Category of the item (e.g. {@code "heal"} for healing,
 *                    {@code "boost"} for stat modifiers).
 * @param effect      The {@link Effect} applied to the target Bugemon when the item is used.
 *                    May be {@code null} if the item has no mechanical effect.
 * @param sprite      File name of the image displayed in the inventory UI.
 */
public record Item(
        String id,
        String name,
        String description,
        String type,
        Effect effect,
        String sprite) {

    public Item(
            String id,
            String name,
            String description,
            String type,
            Effect effect
    ) {
        this(id, name, description, type, effect, null);
    }

    public ulb.event.BattleMessage getEffectDescription(ulb.model.domain.Bugemon bugemon) {
        return effect != null ? effect.getEffectDescription(bugemon) : null;
    }
}
