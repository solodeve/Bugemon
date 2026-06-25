package ulb.event;

/**
 * Semantic classification of a battle message, used by the view to pick a display colour.
 */
public enum BattleMessageType {
    /** Good news for the player (player attacks, enemy takes damage, player heals, etc.). */
    PLAYER_ADVANTAGE,
    /** Bad news for the player (enemy attacks, player takes damage, bugemon fainted, etc.). */
    ENEMY_ADVANTAGE,
    /** Neutral system event (stat changes, critical hit notice, item usage announcement). */
    SYSTEM,
    /** Status ailment cured. */
    STATUS_CURE,
    /** Negative stat modifiers reset. */
    STATUS_RESET,
    /** Fallback / no semantic classification. */
    NEUTRAL
}
