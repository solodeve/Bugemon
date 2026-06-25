package ulb.model.domain;

/**
 * Represents the current phase of an ongoing battle.
 */
public enum BattleState {
    START,
    ACTION_SELECTION,
    MOVE_SELECTION,
    ITEM_SELECTION,
    PERFORM_MOVE,
    PARTY_SCREEN,
    END
}
