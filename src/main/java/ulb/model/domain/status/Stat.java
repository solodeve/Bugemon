package ulb.model.domain.status;

/**
 * Enumeration of combat stat types that can be modified by status effects.
 * These are tracked separately: ATTACK and ATTACK_MAGIC are distinct,
 * allowing physical and magical stats to scale independently.
 */
public enum Stat {
    HP, ATTACK, DEFENSE, INITIATIVE, PRECISION, DEFENSE_MAGIC, ATTACK_MAGIC
}