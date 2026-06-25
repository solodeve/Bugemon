package ulb.service.no;

import ulb.model.domain.Bugemon;

import java.util.List;

/**
 * The outcome of processing one battle result inside a NO tower run.
 */
public record NOProgressionResult(
        NextState nextState,
        NOBattleOutcome outcome,
        int floorNumber,
        List<Bugemon> unlockedBugemons
) {
    public List<Bugemon> getUnlockedBugemons() {
        return unlockedBugemons;
    }

    public NextState getNextState() {
        return nextState;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public NOBattleOutcome getOutcome() {
        return outcome;
    }

    public NOProgressionResult {
        unlockedBugemons = unlockedBugemons == null ? List.of() : List.copyOf(unlockedBugemons);
    }

    public NOProgressionResult(NextState nextState, NOBattleOutcome outcome, int floorNumber) {
        this(nextState, outcome, floorNumber, List.of());
    }

    public NOProgressionResult(NextState nextState, NOBattleOutcome outcome) {
        this(nextState, outcome, 0, List.of());
    }

    /**
     * Represents whether the controller must end the run or return to the tower screen.
     */
    public enum NextState {
        END_RUN,
        CONTINUE_TOWER
    }
}
