package ulb.service.leaderboard;

import ulb.model.leaderboard.TowerRunStat;
import ulb.service.no.NOBattleOutcome;
import ulb.service.no.NOProgressionResult;

/**
 * Accumulates leaderboard statistics for one NO tower run.
 */
public final class NoRunStatsTracker {
    private int highestFloorReached;
    private int combatsWon;
    private int bugemonsDefeated;
    private int bugemonsLost;
    private int flawlessFloors;

    public void start(int startingFloor) {
        highestFloorReached = Math.max(0, startingFloor);
        combatsWon = 0;
        bugemonsDefeated = 0;
        bugemonsLost = 0;
        flawlessFloors = 0;
    }

    public void recordBattle(boolean won, int floorBeforeBattle, int floorReachedAfterBattle, int defeatedInBattle, int lostOnCurrentFloor, NOProgressionResult progressionResult) {
        highestFloorReached = Math.max(highestFloorReached, Math.max(floorBeforeBattle, floorReachedAfterBattle));
        if (progressionResult != null) {highestFloorReached = Math.max(highestFloorReached, progressionResult.floorNumber());}
        if (won) {combatsWon++;}
        bugemonsDefeated += Math.max(0, defeatedInBattle);

        // If no progression result is available, only the raw battle statistics can be updated.
        // Floor completion and end-of-run statistics cannot be determined.
        if (progressionResult == null) {return;}

        NOBattleOutcome outcome = progressionResult.outcome();
        if (outcome == NOBattleOutcome.FLOOR_CLEARED) {
            recordCompletedFloor(lostOnCurrentFloor);
            return;
        }

        // If the run ends without a normal floor clear, still count the losses from the current floor.
        // If the tower is conquered without losing any bugemon on this floor, count it as flawless.
        if (progressionResult.nextState() == NOProgressionResult.NextState.END_RUN) {
            bugemonsLost += Math.max(0, lostOnCurrentFloor);
            if (outcome == NOBattleOutcome.TOWER_CONQUERED && lostOnCurrentFloor == 0) {flawlessFloors++;}
        }
    }

    public TowerRunStat toRunStat() {
        return new TowerRunStat(highestFloorReached, combatsWon, bugemonsDefeated, bugemonsLost, flawlessFloors);
    }

    private void recordCompletedFloor(int lostOnCurrentFloor) {
        int resolvedLost = Math.max(0, lostOnCurrentFloor);
        bugemonsLost += resolvedLost;
        if (resolvedLost == 0) {
            flawlessFloors++;
        }
    }
}
