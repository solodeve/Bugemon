package ulb.event;

import ulb.model.domain.Bugemon;

/**
 * Emitted by BattleSystem when a Bugemon gains a level.
 *
 * @param bugemon The Bugemon that leveled up.
 */
public record LevelUpEvent(Bugemon bugemon) {}
