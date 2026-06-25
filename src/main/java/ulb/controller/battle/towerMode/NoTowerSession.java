package ulb.controller.battle.towerMode;

import ulb.model.leaderboard.TowerRunStat;
import ulb.model.no.NO;
import ulb.service.leaderboard.NoRunStatsTracker;
import ulb.service.no.NOBattleOutcome;
import ulb.service.no.NOProgressionResult;

/**
 * Stores the state of the current NO tower run.
 *
 * <p>It keeps the active adventure, the next outcome, and run stats.
 * Use it to start, update, read, and reset a run.</p>
 */
public class NoTowerSession {

    private NO adventure;
    private NOProgressionResult pendingOutcome;
    private final NoRunStatsTracker statsTracker = new NoRunStatsTracker();

    /**
     * Starts a new tower run for the provided adventure.
     *
     * <p>The run is initialized with the progression state
     * {@code CONTINUE_TOWER / RUN_STARTED} so the UI can render the tower entry
     * correctly before the first battle.
     * Run statistics are also reset from the current floor.</p>
     *
     * @param adventure NO adventure that becomes the active run
     */
    public void start(NO adventure) {
        this.adventure = adventure;
        this.pendingOutcome = new NOProgressionResult(
                NOProgressionResult.NextState.CONTINUE_TOWER, NOBattleOutcome.RUN_STARTED);
        statsTracker.start(adventure.getCurrentFloorNumber());
    }

    /** Ends the current run and discards all state. */
    public void clear() {
        adventure = null;
        pendingOutcome = null;
        statsTracker.start(0);
    }

    public boolean isActive() {
        return adventure != null;
    }

    public NO getAdventure() {
        return adventure;
    }

    public void setOutcome(NOProgressionResult outcome) {
        this.pendingOutcome = outcome;
    }

    /**
     * Returns the latest pending progression outcome, then clears it.
     *
     * <p>This method enforces single-consumption semantics: once read, the same
     * outcome cannot be consumed again, preventing duplicate processing.</p>
     *
     * @return the pending outcome, or {@code null} when none is waiting
     */
    public NOProgressionResult consumeOutcome() {
        NOProgressionResult result = pendingOutcome;
        pendingOutcome = null;
        return result;
    }

    /**
     * Records the business impact of one battle on {@link TowerRunStat}.
     *
     * <p>Win state, floor transition, and fainted counts for both teams are
     * consolidated into the run-level performance metrics.</p>
     *
     * @param won              whether the player won the battle
     * @param floorBefore      floor number when the battle started
     * @param result           progression result produced after the battle
     * @param enemyFainted     number of enemy Bugemons that fainted
     * @param playerFainted    number of player Bugemons that fainted
     */
    public void recordBattle(boolean won, int floorBefore, NOProgressionResult result,
                      int enemyFainted, int playerFainted) {
        statsTracker.recordBattle(won, floorBefore, result.floorNumber(),
                enemyFainted, playerFainted, result);
    }

    /** Returns the accumulated run statistics snapshot. */
    public TowerRunStat getRunStat() {
        return statsTracker.toRunStat();
    }

    public int getCurrentFloorNumber() {
        return adventure.getCurrentFloorNumber();
    }
}
