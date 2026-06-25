package ulb.controller.battle.freeMode;

import ulb.controller.ApplicationController;
import ulb.controller.battle.BattleController;
import ulb.controller.session.GameModeStrategy;
import ulb.model.domain.GameMode;
import ulb.model.team.Team;
import ulb.view.battle.state.BattleIntroUiState;



/**
 * Game-mode strategy for a standalone free battle.
 *
 * <p>Sets all Bugemons to their maximum level, initialises a battle against a
 * randomly selected opponent and navigates directly to the game screen.
 * When the battle ends the end-battle screen is shown.</p>
 */
public class FreeBattleController implements GameModeStrategy {

    private final ApplicationController app;
    private final BattleController battleController;

    public FreeBattleController(ApplicationController app, BattleController battleController) {
        this.app = app;
        this.battleController = battleController;
    }

    @Override
    public void startGameWithTeam(Team playerTeam) {
        setTeamLevelMax(playerTeam);
        battleController.setTeamPersistenceType(GameMode.FREE_BATTLE.name());
        battleController.setPlayerTeam(playerTeam);
        battleController.setBattleInitialized(false);
        battleController.prepareBattle(playerTeam, null, BattleIntroUiState.defaultIntro());
        app.showGameScreen();
    }

    @Override
    public void handleBattleFinished(boolean won) {
        app.openEndBattle();
    }

    @Override
    public GameMode getGameMode() {
        return GameMode.FREE_BATTLE;
    }

    private static void setTeamLevelMax(Team team) {
        team.setAllMembersMaxLevel();
    }
}
