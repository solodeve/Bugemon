package ulb.service.leaderboard;

import org.junit.jupiter.api.Test;
import ulb.model.leaderboard.TowerRunStat;
import ulb.service.no.NOBattleOutcome;
import ulb.service.no.NOProgressionResult;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestNoRunStatsTracker {

    @Test
    void roomWinCountsCombatAndDefeatedBugemonsWithoutCompletingFloor() {
        NoRunStatsTracker tracker = new NoRunStatsTracker();
        tracker.start(1);

        tracker.recordBattle(
                true,
                1,
                1,
                3,
                0,
                new NOProgressionResult(NOProgressionResult.NextState.CONTINUE_TOWER, NOBattleOutcome.ROOM_CLEARED)
        );

        TowerRunStat runStat = tracker.toRunStat();
        assertEquals(1, runStat.floorReached());
        assertEquals(1, runStat.combatsWon());
        assertEquals(3, runStat.bugemonsDefeated());
        assertEquals(0, runStat.bugemonsLost());
        assertEquals(0, runStat.flawlessFloors());
    }

    @Test
    void floorClearCountsLossesAndReachedNextFloor() {
        NoRunStatsTracker tracker = new NoRunStatsTracker();
        tracker.start(1);

        tracker.recordBattle(
                true,
                1,
                2,
                1,
                2,
                new NOProgressionResult(NOProgressionResult.NextState.CONTINUE_TOWER, NOBattleOutcome.FLOOR_CLEARED, 1)
        );

        TowerRunStat runStat = tracker.toRunStat();
        assertEquals(2, runStat.floorReached());
        assertEquals(1, runStat.combatsWon());
        assertEquals(1, runStat.bugemonsDefeated());
        assertEquals(2, runStat.bugemonsLost());
        assertEquals(0, runStat.flawlessFloors());
    }

    @Test
    void towerConqueredCountsFinalFloorAsFlawlessWhenNoBugemonWasLost() {
        NoRunStatsTracker tracker = new NoRunStatsTracker();
        tracker.start(1);

        tracker.recordBattle(
                true,
                1,
                2,
                2,
                0,
                new NOProgressionResult(NOProgressionResult.NextState.CONTINUE_TOWER, NOBattleOutcome.FLOOR_CLEARED, 1)
        );
        tracker.recordBattle(
                true,
                2,
                2,
                1,
                0,
                new NOProgressionResult(NOProgressionResult.NextState.END_RUN, NOBattleOutcome.TOWER_CONQUERED)
        );

        TowerRunStat runStat = tracker.toRunStat();
        assertEquals(2, runStat.floorReached());
        assertEquals(2, runStat.combatsWon());
        assertEquals(3, runStat.bugemonsDefeated());
        assertEquals(0, runStat.bugemonsLost());
        assertEquals(2, runStat.flawlessFloors());
    }

    @Test
    void runLossCountsPartialDefeatsAndCurrentFloorLosses() {
        NoRunStatsTracker tracker = new NoRunStatsTracker();
        tracker.start(1);

        tracker.recordBattle(
                false,
                1,
                1,
                2,
                4,
                new NOProgressionResult(NOProgressionResult.NextState.END_RUN, NOBattleOutcome.RUN_LOST, 1)
        );

        TowerRunStat runStat = tracker.toRunStat();
        assertEquals(1, runStat.floorReached());
        assertEquals(0, runStat.combatsWon());
        assertEquals(2, runStat.bugemonsDefeated());
        assertEquals(4, runStat.bugemonsLost());
        assertEquals(0, runStat.flawlessFloors());
    }
}
