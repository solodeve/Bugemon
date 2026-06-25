package ulb.event;

/**
 * Emitted by BattleSystem when a battle concludes.
 *
 * @param won {@code true} if the player won, {@code false} otherwise.
 */
public record BattleEndEvent(boolean won) {}
