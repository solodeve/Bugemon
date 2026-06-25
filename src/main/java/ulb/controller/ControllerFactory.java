package ulb.controller;

import ulb.controller.battle.BattleController;
import ulb.controller.battle.EndBattleController;
import ulb.controller.battle.inventory.InventorySelectionController;
import ulb.controller.battle.party.PartySelectionController;
import ulb.controller.menu.teamEdition.TeamEditionController;
import ulb.controller.menu.teamSelection.TeamSelectionController;
import ulb.controller.battle.towerMode.NoLevelupController;
import ulb.controller.battle.towerMode.NoController;
import ulb.controller.battle.towerMode.NoRewardsController;
import ulb.controller.menu.LoginController;
import ulb.controller.menu.MainMenuController;
import ulb.controller.menu.SettingsController;
import ulb.exception.TeamPersistenceException;
import ulb.model.domain.GameMode;
import ulb.model.team.Team;
import ulb.view.battle.EndBattleView;
import ulb.view.battle.BattleView;
import ulb.view.battle.inventory.InventorySelectionView;
import ulb.view.battle.towerMode.LevelupView;
import ulb.view.menu.LoginView;
import ulb.view.menu.MainMenuView;
import ulb.view.battle.towerMode.NOView;
import ulb.view.battle.party.PartySelectionView;
import ulb.view.battle.towerMode.RewardsView;
import ulb.view.menu.SettingsView;
import ulb.view.menu.teamEdition.TeamEditionView;
import ulb.view.menu.LeaderboardView;
import ulb.view.menu.teamSelection.TeamSelectionView;

/**
 * Instantiates and wires each feature controller to its FXML view.
 */
class ControllerFactory {

    private final ApplicationController app;
    private final BattleController battleController;

    ControllerFactory(ApplicationController app, BattleController battleController) {
        this.app = app;
        this.battleController = battleController;
    }

    void wireLogin(LoginView view) {
        new LoginController(view, app, app.getLoginService());
    }

    void wireMainMenu(MainMenuView view) {
        new MainMenuController(view, app);
    }

    /**
     * @throws TeamPersistenceException if team data cannot be loaded from the repository
     */
    void wireTeamSelection(TeamSelectionView view) throws TeamPersistenceException {
        new TeamSelectionController(view, app, app.getGameRepository(), app.getCurrentGameMode());
    }

    void wireGame(BattleView view) {
        battleController.setBattleView(view);
        battleController.openGameScreen();
    }

    void wireNoTower(NOView view) {
        NoController controller = new NoController(app);
        controller.attachView(view, app.getNoTowerAdventure());
    }

    void wireTeamEditor(TeamEditionView view, Team team, GameMode gameMode) {
        new TeamEditionController(
                view,
                app.getGameRepository(),
                app,
                team == null ? null : team.getName(),
                gameMode
        );
    }

    void wireEndBattle(EndBattleView view) {
        new EndBattleController(view, app, battleController.getBattleSystem());
    }

    void wirePartySelection(PartySelectionView view) {
        new PartySelectionController(view, app, battleController.getBattleSystem(), battleController.getPlayerTeam(), battleController);
    }

    void wireLevelUp(LevelupView view) {
        new NoLevelupController(view, app, battleController.getBattleSystem());
    }

    void wireInventory(InventorySelectionView view) {
        new InventorySelectionController(view, app, battleController.getBattleSystem(), battleController.getPlayerTeam(), battleController);
    }

    void wireRewards(RewardsView view) {
        new NoRewardsController(view, app, app.getGameRepository());
    }

    void wireSettings(SettingsView view) {
        new SettingsController(view, app);
    }

    void wireLeaderboard(LeaderboardView view) {
        app.configureLeaderboard(view);
    }
}

