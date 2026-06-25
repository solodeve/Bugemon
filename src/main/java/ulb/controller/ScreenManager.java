package ulb.controller;

import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.TextInputControl;
import javafx.stage.Stage;
import ulb.configuration.Configuration;
import ulb.controller.battle.BattleController;
import ulb.exception.ScreenLoadingException;
import ulb.exception.TeamPersistenceException;
import ulb.model.domain.GameMode;
import ulb.model.team.Team;
import ulb.view.battle.EndBattleView;
import ulb.view.battle.BattleView;
import ulb.view.menu.LeaderboardView;
import ulb.view.menu.LoginView;
import ulb.view.menu.MainMenuView;
import ulb.view.menu.SettingsView;
import ulb.view.battle.towerMode.LevelupView;
import ulb.view.battle.towerMode.RewardsView;
import ulb.view.battle.inventory.InventorySelectionView;
import ulb.view.battle.party.PartySelectionView;
import ulb.view.menu.teamEdition.TeamEditionView;
import ulb.view.menu.teamSelection.TeamSelectionView;
import ulb.view.battle.towerMode.NOView;

/**
 * Drives all screen transitions and handles global keyboard shortcuts.
 */
public final class ScreenManager {

    public enum MenuState {
        LOGIN, MAIN, GAME, NO_TOWER, TEAM_SELECT, END_BATTLE,
        PARTY, LEVELUP, INVENTORY, TEAM_EDITOR, REWARDS, SETTINGS, LEADERBOARD,
    }

    private final ApplicationController applicationController;
    private final BattleController battleController;
    private final ViewLoader viewLoader;
    private final ControllerFactory controllerFactory;

    private MenuState currentState = MenuState.MAIN;
    private final Map<MenuState, Parent> cachedRoots = new EnumMap<>(MenuState.class);
    private final boolean stageAvailable;

    public ScreenManager(Stage stage, ApplicationController applicationController, BattleController battleController) {
        this.applicationController = applicationController;
        this.battleController      = battleController;
        this.viewLoader            = new ViewLoader(stage, this::handleSceneKeyPressed);
        this.controllerFactory     = new ControllerFactory(applicationController, battleController);
        this.stageAvailable        = stage != null;
    }

    /**
     * @throws ScreenLoadingException if the FXML or its controller cannot be loaded
     */
    public void showScreen(MenuState state) throws ScreenLoadingException {
        currentState = state;
        if (!stageAvailable) {return;}
        try {
            switch (state) {
                case LOGIN       -> showLogin();
                case MAIN        -> showMainMenu();
                case TEAM_SELECT -> showTeamSelection();
                case GAME        -> showGame();
                case NO_TOWER    -> showNoTower();
                case END_BATTLE  -> showEndBattle();
                case PARTY       -> showPartySelection();
                case LEVELUP     -> showLevelUp();
                case INVENTORY   -> showInventorySelection();
                case REWARDS     -> showRewards();
                case SETTINGS    -> showSettings();
                case LEADERBOARD -> showLeaderboard();
            }
        } catch (IOException | TeamPersistenceException e) {throw new ScreenLoadingException("Unable to load screen " + state + ".", e);}
    }

    /**
     * Opens the team editor, passing {@code team} and {@code gameMode} directly to avoid
     * temporary state in {@link ApplicationController}.
     *
     * @throws ScreenLoadingException if the FXML cannot be loaded
     */
    void showTeamEditor(Team team, GameMode gameMode) throws ScreenLoadingException {
        currentState = MenuState.TEAM_EDITOR;
        try {
            ViewLoader.LoadedView<TeamEditionView> view =
                    viewLoader.load(Configuration.TEAM_EDITION_VIEW_PATH);
            controllerFactory.wireTeamEditor(view.getController(), team, gameMode);
            viewLoader.setScene(view.getRoot());
        } catch (IOException e) {
            throw new ScreenLoadingException("Unable to load screen TEAM_EDITOR.", e);
        }
    }

    public MenuState getCurrentState() {
        return currentState;
    }

    public void exitApplication() {
        viewLoader.close();
    }

    private void showLogin() throws IOException {
        ViewLoader.LoadedView<LoginView> view =
                viewLoader.load(Configuration.LOGIN_VIEW_PATH);
        controllerFactory.wireLogin(view.getController());
        viewLoader.setScene(view.getRoot());
    }

    private void showMainMenu() throws IOException {
        ViewLoader.LoadedView<MainMenuView> view =
                viewLoader.load(Configuration.MAIN_MENU_VIEW_PATH);
        controllerFactory.wireMainMenu(view.getController());
        viewLoader.setScene(view.getRoot());
    }

    private void showTeamSelection() throws IOException, TeamPersistenceException {
        ViewLoader.LoadedView<TeamSelectionView> view =
                viewLoader.load(Configuration.TEAM_SELECTION_VIEW_PATH);
        controllerFactory.wireTeamSelection(view.getController());
        viewLoader.setScene(view.getRoot());
    }

    private void showGame() throws IOException {
        ViewLoader.LoadedView<BattleView> view =
                viewLoader.load(Configuration.BATTLE_VIEW_PATH);
        controllerFactory.wireGame(view.getController());
        viewLoader.setScene(view.getRoot());
    }

    private void showNoTower() throws IOException {
        ViewLoader.LoadedView<NOView> view =
                viewLoader.load(Configuration.NO_VIEW_PATH);
        controllerFactory.wireNoTower(view.getController());
        viewLoader.setScene(view.getRoot());
    }

    private void showLeaderboard() throws IOException {
        ViewLoader.LoadedView<LeaderboardView> view =
                viewLoader.load(Configuration.LEADERBOARD_VIEW_PATH);
        controllerFactory.wireLeaderboard(view.getController());
        viewLoader.setScene(view.getRoot());
    }

    private void showEndBattle() throws IOException {
        ViewLoader.LoadedView<EndBattleView> view =
                viewLoader.load(Configuration.END_BATTLE_VIEW_PATH);
        controllerFactory.wireEndBattle(view.getController());
        viewLoader.setScene(view.getRoot());
    }

    private void showPartySelection() throws IOException {
        ViewLoader.LoadedView<PartySelectionView> view =
                viewLoader.load(Configuration.PARTY_VIEW_PATH);
        controllerFactory.wirePartySelection(view.getController());
        viewLoader.setScene(view.getRoot());
    }

    private void showLevelUp() throws IOException {
        ViewLoader.LoadedView<LevelupView> view =
                viewLoader.load(Configuration.LEVELUP_VIEW_PATH);
        controllerFactory.wireLevelUp(view.getController());
        viewLoader.setScene(view.getRoot());
    }

    private void showInventorySelection() throws IOException {
        Parent cached = cachedRoots.get(MenuState.INVENTORY);
        if (cached != null) {
            viewLoader.setScene(cached);
            return;
        }
        ViewLoader.LoadedView<InventorySelectionView> view = viewLoader.load(Configuration.INVENTORY_VIEW_PATH);
        controllerFactory.wireInventory(view.getController());
        cachedRoots.put(MenuState.INVENTORY, view.getRoot());
        viewLoader.setScene(view.getRoot());
    }

    private void showRewards() throws IOException {
        ViewLoader.LoadedView<RewardsView> view =
                viewLoader.load(Configuration.REWARDS_VIEW_PATH);
        controllerFactory.wireRewards(view.getController());
        viewLoader.setScene(view.getRoot());
    }

    private void showSettings() throws IOException {
        ViewLoader.LoadedView<SettingsView> view =
                viewLoader.load(Configuration.SETTINGS_VIEW_PATH);
        controllerFactory.wireSettings(view.getController());
        viewLoader.setScene(view.getRoot());
    }

    private void handleSceneKeyPressed(KeyEvent event) {
        if (event.isConsumed() || event.getTarget() instanceof TextInputControl) {
            return;}

        BattleController battleController = getBattleController();
        KeyCode keyCode = event.getCode();

        if (handleSpaceKey(event, keyCode, battleController)) {return;}

        if (keyCode != KeyCode.ESCAPE) {return;}

        if (shouldIgnoreEscape()) {return;}

        boolean handled = handleEscapeByState(battleController);

        if (handled) {event.consume();}
    }

    private BattleController getBattleController() {
        return battleController;
    }

    private boolean handleSpaceKey(KeyEvent event, KeyCode keyCode, BattleController battleController) {
        if (keyCode != KeyCode.SPACE) {return false;}

        if (currentState != MenuState.GAME) {return false;}

        if (battleController == null) {return false;}

        battleController.onSpacePressed();
        event.consume();
        return true;
    }

    boolean shouldIgnoreEscape() {
        return applicationController == null
                && currentState != MenuState.MAIN
                && currentState != MenuState.GAME
                && currentState != MenuState.LEVELUP;
    }

    boolean handleEscapeByState(BattleController battleController) {
        return switch (currentState) {
            case LOGIN, MAIN -> {exitApplication();
                yield true;}
            case TEAM_SELECT, END_BATTLE, REWARDS, SETTINGS, LEADERBOARD -> {applicationController.returnToMainMenu();
                yield true;}
            case TEAM_EDITOR -> {applicationController.openTeamSelection();
                yield true;}
            case GAME -> battleController != null && battleController.handleEscapePressed();
            case NO_TOWER -> {applicationController.abandonNoTower();
                yield true;}
            case PARTY, INVENTORY -> {applicationController.showGameScreen();
                yield true;}
            case LEVELUP -> false;
        };
    }
}
