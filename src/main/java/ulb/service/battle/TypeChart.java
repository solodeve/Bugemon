package ulb.service.battle;

import ulb.model.domain.Element;

/**
 * Represents the type effectiveness chart for the different elements in the game.
 * This class provides a method to determine the effectiveness multiplier based on the attacking and defending elements.
 */
public class TypeChart {
    private static final float TYPE_EFFECTIVENESS_SUPER = 1.5f;
    private static final float TYPE_EFFECTIVENESS_WEAK = 0.66f;
    private static final float TYPE_EFFECTIVENESS_NEUTRAL = 1.0f;

    // Horizontal = defence, Vertical = attack
    // Order: FIRE, ICE, PLANT, MECHA, LIGHT, SHADOW
    static final float [][] chart = {
        //              FIRE                     ICE                      PLANT                    MECHA                        LIGHT                             SHADOW
            {TYPE_EFFECTIVENESS_NEUTRAL, TYPE_EFFECTIVENESS_WEAK,  TYPE_EFFECTIVENESS_SUPER, TYPE_EFFECTIVENESS_NEUTRAL, TYPE_EFFECTIVENESS_NEUTRAL, TYPE_EFFECTIVENESS_NEUTRAL}, // FIRE
            {TYPE_EFFECTIVENESS_SUPER,   TYPE_EFFECTIVENESS_NEUTRAL, TYPE_EFFECTIVENESS_NEUTRAL, TYPE_EFFECTIVENESS_NEUTRAL, TYPE_EFFECTIVENESS_NEUTRAL, TYPE_EFFECTIVENESS_NEUTRAL}, // ICE
            {TYPE_EFFECTIVENESS_WEAK,    TYPE_EFFECTIVENESS_SUPER, TYPE_EFFECTIVENESS_NEUTRAL, TYPE_EFFECTIVENESS_NEUTRAL, TYPE_EFFECTIVENESS_NEUTRAL, TYPE_EFFECTIVENESS_NEUTRAL}, // PLANT
            {TYPE_EFFECTIVENESS_NEUTRAL, TYPE_EFFECTIVENESS_NEUTRAL, TYPE_EFFECTIVENESS_NEUTRAL, TYPE_EFFECTIVENESS_NEUTRAL, TYPE_EFFECTIVENESS_SUPER, TYPE_EFFECTIVENESS_WEAK}, // MECHA
            {TYPE_EFFECTIVENESS_NEUTRAL, TYPE_EFFECTIVENESS_NEUTRAL, TYPE_EFFECTIVENESS_NEUTRAL, TYPE_EFFECTIVENESS_SUPER, TYPE_EFFECTIVENESS_NEUTRAL, TYPE_EFFECTIVENESS_SUPER}, // LIGHT
            {TYPE_EFFECTIVENESS_NEUTRAL, TYPE_EFFECTIVENESS_NEUTRAL, TYPE_EFFECTIVENESS_NEUTRAL, TYPE_EFFECTIVENESS_WEAK, TYPE_EFFECTIVENESS_SUPER, TYPE_EFFECTIVENESS_NEUTRAL}  // SHADOW
    };

    /**
     * Returns the damage multiplier for a given attack/defence element pair.
     * @param attack  The element of the skill being used.
     * @param defense The element of the defending Bugemon.
     * @return 1.5f (super effective), 1.0f (neutral), 0.66f or 0.5f (not effective).
     */
    public static float getEffectiveness(Element attack, Element defense) {
        if (attack == Element.ALL || defense == Element.ALL) return TYPE_EFFECTIVENESS_NEUTRAL;
        return chart[attack.ordinal()][defense.ordinal()];
    }
}
