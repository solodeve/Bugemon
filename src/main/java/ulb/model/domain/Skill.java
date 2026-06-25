package ulb.model.domain;

import ulb.model.domain.effect.Effect;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a combat ability where effects can be added dynamically.
 * We use a standard class instead of a record to allow mutability.
 */
public class Skill {
    private final String id;
    private final String name;
    private final Element element;
    private final String description;
    private final int power;
    private final List<Effect> effects;
    private final float precision;      // between 0 and 1
    private final int priority;         // between -1 and 1
    private final boolean attackMagic;

    public Skill(String id, String name, Element element, String description, int power, float precision, int priority, boolean attackMagic) {
        this.id = id;
        this.name = name;
        this.element = element;
        this.description = description;
        this.power = power;
        this.priority = priority;
        this.effects = new ArrayList<>(); // Initialize an empty mutable list
        this.precision = precision;
        this.attackMagic = attackMagic;
    }

    /**
     * Allows adding an effect to the skill after the object is created.
     * This is the core reason for using a class over a record.
     */
    public void addEffect(Effect effect) {
        if (effect != null) {
            this.effects.add(effect);
        }
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Element getElement() {
        return element;
    }

    public String getElementDisplayName() {
        return element == null ? null : element.displayName();
    }

    public String getDescription() {
        return description;
    }

    public int getPower() {
        return power;
    }

    public int getPriority() {
        return priority;
    }

    public float getPrecision() {
        return precision;
    }

    public boolean getAttackMagic() {
        return attackMagic;
    }

    /**
     * Returns an unmodifiable view of the effects to maintain encapsulation
     * while allowing external classes to read the current effects.
     */
    public List<Effect> getEffects() {
        return List.copyOf(effects);
    }

    public void applyEffects(Bugemon source, Bugemon target, List<ulb.event.BattleMessage> messages) {
        for (Effect effect : effects) {
            java.util.Optional<Bugemon> affected = resolveEffectTarget(effect, source, target);
            affected.ifPresent(bugemon -> {
                bugemon.applyEffect(effect);
                ulb.event.BattleMessage msg = effect.getEffectDescription(bugemon);
                if (msg != null) {
                    messages.add(msg);
                }
            });
        }
    }

    private java.util.Optional<Bugemon> resolveEffectTarget(Effect effect, Bugemon source, Bugemon target) {
        if (effect == null) {
            return java.util.Optional.empty();
        }
        return java.util.Optional.ofNullable(switch (effect.target()) {
            case CASTER, TEAM -> source;
            case OPPONENT -> target;
        });
    }
}