package ulb.controller.battle.towerMode;

import ulb.controller.ApplicationController;
import ulb.controller.battle.BattleController;
import ulb.controller.session.GameModeStrategy;
import ulb.model.domain.GameMode;
import ulb.model.no.NO;
import ulb.model.team.Team;
import ulb.service.no.NOProgressionResult;
import ulb.service.no.NOProgressionService;

import java.util.List;

/**
 * Game-mode strategy for a NO tower run.
 *
 * <p>Creates a new {@link NO} adventure, navigates to the tower screen and
 * delegates each post-battle decision to {@link NOProgressionService}.
 * When the run ends (win or loss) the strategy resets the application back to
 * free-battle mode and records the run in the leaderboard.</p>
 */
public class NoTowerStrategy implements GameModeStrategy {

    private final ApplicationController app;
    private final BattleController battleController;
    private final NOProgressionService noProgressionService = new NOProgressionService();

    public NoTowerStrategy(ApplicationController app, BattleController battleController) {
        this.app = app;
        this.battleController = battleController;
    }

    @Override
    public void startGameWithTeam(Team playerTeam) {
        battleController.setTeamPersistenceType(getGameMode().name());
        battleController.setPlayerTeam(playerTeam);
        app.startNoTowerSession(new NO(app.getGameRepository()));
        app.openNoTower();
    }

    /**
     * Applies tower progression after a battle and routes the player to the correct next screen.
     *
     * <p>If no tower session is active, it falls back to the standard end-battle flow.</p>
     */
    @Override
    public void handleBattleFinished(boolean won) {
        if (!app.isNoTowerSessionActive()) {
            app.setPendingUnlockedBugemons(List.of());
            app.openEndBattle();
            return;
        }

        Team playerTeam = battleController.getPlayerTeam();

        int floorBefore = app.getNoTowerCurrentFloorNumber();
        int playerFainted = countFainted(playerTeam);
        int enemyFainted = countFainted(battleController.getEnemyTeam());

        NOProgressionResult result = noProgressionService.processBattleResult(won, app.getNoTowerAdventure(), app.getGameRepository(), playerTeam);
        saveBattleOutcome(won, floorBefore, playerFainted, enemyFainted, result);
        navigateAccordingTo(result, playerTeam);
    }

    private void saveBattleOutcome(boolean won, int floorBefore, int playerFainted, int enemyFainted, NOProgressionResult result) {
        app.recordNoTowerBattle(won, floorBefore, result, enemyFainted, playerFainted);
        app.setNoTowerOutcome(result);
        app.setPendingUnlockedBugemons(result.unlockedBugemons());
    }

    /**
     * Resolves post-battle navigation from progression state.
     *
     * <p>When the run ends, it records leaderboard data, clears tower session state,
     * and switches back to free-battle mode. Otherwise, it prepares the next tower step.</p>
     */
    private void navigateAccordingTo(NOProgressionResult result, Team playerTeam) {
        if (result.nextState() == NOProgressionResult.NextState.END_RUN) {
            app.getLeaderboardController().recordNoTowerRun(app.getGameRepository().getUser(), playerTeam, app.getNoTowerRunStat());
            app.clearNoTowerSession();
            app.switchToFreeBattleMode();
            app.openEndBattle();
        } else {
            battleController.setBattleInitialized(false);
            app.openNoTower();
        }
    }

    @Override
    public GameMode getGameMode() {
        return GameMode.NO_TOWER;
    }

    private int countFainted(Team team) {
        if (team == null) {
            return 0;
        }
        return (int) team.getMembers().stream().filter(b -> !b.isHealthy()).count();
    }
}
