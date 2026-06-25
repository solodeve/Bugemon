package ulb.service.battle;

import ulb.event.BattleMessage;

import java.util.List;

/**
 * Immutable result of executing a single battle move.
 *
 * @param messages     ordered list of messages to display after the move
 * @param targetFainted whether the target reached 0 HP during this move
 */
public record MoveResult(List<BattleMessage> messages, boolean targetFainted) {}
