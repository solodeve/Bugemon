package ulb.service.no;

import ulb.model.domain.Bugemon;
import ulb.model.no.NO;
import ulb.model.team.Team;
import ulb.repository.IGameRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Applies NO tower progression rules after a battle and returns the resulting state.
 *
 * <p>All side effects on the model (unlocking bugemons, advancing floors/rooms,
 * restoring HP) are performed here. The controller reads the returned
 * {@link NOProgressionResult} to decide navigation only.</p>
 */
public class NOProgressionService {

    private static final NOProgressionResult.NextState END_RUN = NOProgressionResult.NextState.END_RUN;
    private static final NOProgressionResult.NextState CONTINUE = NOProgressionResult.NextState.CONTINUE_TOWER;

    /**
     * Processes the outcome of a battle and mutates the tower state accordingly.
     *
     * @param won          whether the player won the battle
     * @param adventure    the active NO tower run
     * @param repository   game repository (unlock / floor-clear side effects)
     * @param playerTeam   the player's team (HP restore on floor clear)
     * @return the next state and a human-readable status message
     */
    public NOProgressionResult processBattleResult(
            boolean won,
            NO adventure,
            IGameRepository repository,
            Team playerTeam) {

        if (!won) {
            return new NOProgressionResult(END_RUN, NOBattleOutcome.RUN_LOST, adventure.getCurrentFloorNumber());
        }

        if (adventure.getCurrentRoom() == null) {
            return new NOProgressionResult(END_RUN, NOBattleOutcome.NO_ROOMS_LEFT);
        }

        if (adventure.isCurrentRoomBossRoom() && adventure.hasCurrentRoomAfoBugemon()) {
            return new NOProgressionResult(END_RUN, NOBattleOutcome.AFO_BLOCKED);
        }

        if (adventure.isCurrentRoomBossRoom()) {
            return processBossRoomWin(adventure, repository, playerTeam);
        }

        return processEnemyRoomWin(adventure, repository);
    }

    private NOProgressionResult processBossRoomWin(NO adventure, IGameRepository repository, Team playerTeam) {

        List<Bugemon> unlockedBugemons = new ArrayList<>(safeUnlocks(repository.unlockBugemonsFromTeam(adventure.getCurrentEnemyTeam())));

        if (adventure.getCurrentFloorNumber() >= adventure.getNumberOfFloors()) {
            return new NOProgressionResult(END_RUN, NOBattleOutcome.TOWER_CONQUERED, 0, unlockedBugemons);
        }

        int clearedFloor = adventure.getCurrentFloorNumber();
        unlockedBugemons.addAll(safeUnlocks(repository.unlockBugemonsOnFloorClear(clearedFloor)));
        adventure.goToNextFloor();
        playerTeam.restoreAllHp();

        return new NOProgressionResult(CONTINUE, NOBattleOutcome.FLOOR_CLEARED, clearedFloor, unlockedBugemons);
    }

    private NOProgressionResult processEnemyRoomWin(NO adventure, IGameRepository repository) {
        List<Bugemon> unlockedBugemons = safeUnlocks(repository.unlockBugemonsFromTeam(adventure.getCurrentEnemyTeam()));
        adventure.goToNextRoom();

        return new NOProgressionResult(CONTINUE, NOBattleOutcome.ROOM_CLEARED, 0, unlockedBugemons);
    }

    private List<Bugemon> safeUnlocks(List<Bugemon> unlockedBugemons) {
        return unlockedBugemons == null ? List.of() : unlockedBugemons;
    }
}
