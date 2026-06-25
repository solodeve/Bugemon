package ulb.event;

import ulb.model.domain.Element;

/**
 * Simple battle event message emitted by the model layer.
 * When {@code attackElement} is non-null this message announces an attack move,
 * and the UI layer should play the matching elemental animation.
 */
public record BattleMessage(
        String text,
        String colour,
        Element attackElement,
        boolean playerAttacking,
        boolean attackMagic,
        boolean healingSound,
        BattleMessageType type,
        boolean deathSound,
        boolean impactSound,
        boolean switchSound,
        boolean boostSound) {
    private static final String DEFAULT_COLOUR = "black";

    public BattleMessage(String text) {
        this(text, BattleMessageType.NEUTRAL);
    }

    public BattleMessage(String text, BattleMessageType type) {
        this(text, DEFAULT_COLOUR, null, false, false, false, type, false, false, false, false);
    }

    public BattleMessage(String text, String colour) {
        this(text, colour, null, false, false, false, BattleMessageType.NEUTRAL, false, false, false, false);
    }

    public BattleMessage(String text, String colour, Element attackElement,
                         boolean playerAttacking, boolean attackMagic, boolean healingSound) {
        this(text, colour, attackElement, playerAttacking, attackMagic, healingSound,
                BattleMessageType.NEUTRAL, false, false, false, false);
    }

    public BattleMessage(String text, String colour, Element attackElement,
                         boolean playerAttacking, boolean attackMagic, boolean healingSound, boolean deathSound) {
        this(text, colour, attackElement, playerAttacking, attackMagic, healingSound,
                BattleMessageType.NEUTRAL, deathSound, false, false, false);
    }

    public BattleMessage(String text, String colour, Element attackElement,
                         boolean playerAttacking, boolean attackMagic, boolean healingSound,
                         BattleMessageType type) {
        this(text, colour, attackElement, playerAttacking, attackMagic, healingSound, type, false, false, false, false);
    }

    /** Convenience constructor for messages that carry only a sound flag (switch or boost). */
    public BattleMessage(String text, BattleMessageType type, boolean switchSound, boolean boostSound) {
        this(text, DEFAULT_COLOUR, null, false, false, false, type, false, false, switchSound, boostSound);
    }
}
