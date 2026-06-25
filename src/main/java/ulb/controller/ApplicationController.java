package ulb.controller;

import javafx.stage.Stage;
import ulb.controller.battle.BattleNavigator;
import ulb.controller.battle.BattleController;
import ulb.controller.session.GameDataInitializer;
import ulb.controller.session.GameModeStrategy;
import ulb.controller.session.SessionManager;
import ulb.controller.battle.towerMode.NoTowerSession;
import ulb.controller.battle.towerMode.NoTowerStrategy;
import ulb.controller.battle.freeMode.FreeBattleController;
import ulb.controller.menu.LeaderboardController;
import ulb.exception.DbNotConfiguredException;
import ulb.model.infrastructure.DatabaseBugemon;
import ulb.model.infrastructure.UserDao;
import ulb.model.no.NO;
import ulb.model.no.Room;
import ulb.model.user.User;
import ulb.service.leaderboard.Leaderboard;
import ulb.service.login.LoginService;
import ulb.model.leaderboard.TowerRunStat;
import ulb.model.domain.GameMode;
import ulb.service.no.NOProgressionResult;
import ulb.exception.GameDataLoadingException;
import ulb.exception.ScreenLoadingException;
import ulb.repository.GameRepository;
import ulb.model.domain.Bugemon;
import ulb.model.team.Team;
import ulb.view.battle.state.BattleIntroUiState;
import ulb.view.common.NavigationFailureView;

import java.util.List;

/**
 * Root controller that coordinates navigation, session lifecycle, and game-mode transitions.
 */
public final class ApplicationController implements BattleNavigator {

    private final ScreenManager screenManager;
    private final GameRepository gameRepository;
    private final BattleController battleController;
    private final SessionManager sessionManager;
    private final LeaderboardController leaderboardController;
    private final LoginService loginService;
    private final NavigationFailureView navigationFailureView;
    private final NoTowerSession noTowerSession = new NoTowerSession();

    private final GameModeStrategy freeBattleStrategy;
    private final GameModeStrategy noTowerStrategy;
    private GameModeStrategy currentGameMode;
    private List<Bugemon> pendingUnlockedBugemons = List.of();

    /**
     * @param stage the JavaFX primary stage
     * @throws GameDataLoadingException if game data files cannot be loaded
     * @throws DbNotConfiguredException if the database is not configured
     */
    public ApplicationController(Stage stage) throws GameDataLoadingException, DbNotConfiguredException {
        this.gameRepository    = new GameDataInitializer().initialize();
        this.sessionManager    = new SessionManager(gameRepository);
        this.battleController  = new BattleController(this, gameRepository);
        DatabaseBugemon db     = new DatabaseBugemon();
        this.loginService      = new LoginService(new UserDao(db), db);
        this.screenManager     = new ScreenManager(stage, this, battleController);
        this.freeBattleStrategy = new FreeBattleController(this, battleController);
        this.noTowerStrategy    = new NoTowerStrategy(this, battleController);
        this.currentGameMode    = freeBattleStrategy;
        this.leaderboardController = new LeaderboardController(new Leaderboard(gameRepository),this);
        this.navigationFailureView = new NavigationFailureView();
    }

    /**
     * @throws ScreenLoadingException if the login screen cannot be displayed
     */
    public void start() throws ScreenLoadingException {
        screenManager.showScreen(ScreenManager.MenuState.LOGIN);
    }

    /**
     * Registers the authenticated user and navigates to the main menu.
     * Has no effect if {@code loggedUser} is {@code null}.
     */
    public void completeLogin(User loggedUser) {
        if (loggedUser == null) {
            return;
        }
        sessionManager.login(loggedUser);
        returnToMainMenu();
    }

    /**
     * Sets free-battle as the active game mode and opens team selection.
     */
    public void openFreeBattleTeamSelection() {
        currentGameMode = freeBattleStrategy;
        navigateTo(ScreenManager.MenuState.TEAM_SELECT);
    }

    /**
     * Sets NO tower as the active game mode and opens team selection.
     */
    public void openNoTowerTeamSelection() {
        currentGameMode = noTowerStrategy;
        navigateTo(ScreenManager.MenuState.TEAM_SELECT);
    }

    public void openLeaderboard(){
        navigateTo(ScreenManager.MenuState.LEADERBOARD);
    }

    public void openTeamSelection() {
        navigateTo(ScreenManager.MenuState.TEAM_SELECT);
    }

    /**
     * Opens the team editor, passing {@code team} directly to avoid a temporary field in this class.
     *
     * @param team the team to edit, or {@code null} to create a new one
     */
    public void openTeamEditor(Team team) {
        try {
            screenManager.showTeamEditor(team, currentGameMode.getGameMode());
        } catch (ScreenLoadingException e) {
            handleNavigationFailure(ScreenManager.MenuState.TEAM_EDITOR, e);
        }
    }

    public void showGameScreen() {
        navigateTo(ScreenManager.MenuState.GAME);
    }

    public void openNoTower() {
        navigateTo(ScreenManager.MenuState.NO_TOWER);
    }

    public void openEndBattle() {
        navigateTo(ScreenManager.MenuState.END_BATTLE);
    }

    public void openPartySelection() {
        navigateTo(ScreenManager.MenuState.PARTY);
    }

    public void openInventorySelection() {
        navigateTo(ScreenManager.MenuState.INVENTORY);
    }

    public void openLevelUpScreen() {
        navigateTo(ScreenManager.MenuState.LEVELUP);
    }

    public void openRewards() {
        navigateTo(ScreenManager.MenuState.REWARDS);
    }

    public void openSettings() {
        navigateTo(ScreenManager.MenuState.SETTINGS);
    }

    public void returnToMainMenu() {
        navigateTo(ScreenManager.MenuState.MAIN);
    }

    public void exitApplication() {
        screenManager.exitApplication();
    }

    /**
     * Logs out the current user, clears the active NO tower run, resets to free-battle mode,
     * and navigates to the login screen.
     */
    public void logout() {
        sessionManager.logout();
        noTowerSession.clear();
        currentGameMode = freeBattleStrategy;
        navigateTo(ScreenManager.MenuState.LOGIN);
    }

    /**
     * Delegates to the active {@link GameModeStrategy} to start a game with the chosen team.
     *
     * @param playerTeam the team chosen by the player
     */
    public void startGameModeWithTeam(Team playerTeam) {
        currentGameMode.startGameWithTeam(playerTeam);
    }

    /**
     * Routes the battle outcome to the active {@link GameModeStrategy}.
     *
     * @param won whether the player won the battle
     */
    public void handleBattleFinished(boolean won) {
        currentGameMode.handleBattleFinished(won);
    }

    /**
     * Stores the Bugemons unlocked by the last battle for the rewards screen.
     * A {@code null} argument is stored as an empty list.
     *
     * @param unlockedBugemons the newly unlocked Bugemons, may be {@code null}
     */
    public void setPendingUnlockedBugemons(List<Bugemon> unlockedBugemons) {
        pendingUnlockedBugemons = unlockedBugemons == null ? List.of() : List.copyOf(unlockedBugemons);
    }

    /**
     * Returns the pending unlocked Bugemons and clears the list so subsequent calls return empty.
     *
     * @return the Bugemons unlocked since the last call; never {@code null}
     */
    public List<Bugemon> consumePendingUnlockedBugemons() {
        List<Bugemon> unlockedBugemons = pendingUnlockedBugemons;
        pendingUnlockedBugemons = List.of();
        return unlockedBugemons;
    }

    /**
     * Prepares and starts the battle for the given NO tower room.
     * Does nothing if {@code room} is {@code null} or no tower session is active.
     *
     * @param room the room whose enemy team must be faced
     */
    public void startNoTowerBattle(Room room) {
        if (room == null || !noTowerSession.isActive()) {
            return;
        }

        Team playerTeam = battleController.getPlayerTeam();
        battleController.prepareBattle(
                playerTeam,
                room.getEnemyTeam(),
                BattleIntroUiState.noTowerRoom(room.isBossRoom(), room.getDisplayName())
        );
        navigateTo(ScreenManager.MenuState.GAME);
    }

    /**
     * Records the current run in the leaderboard if active, clears the session,
     * and returns to the main menu in free-battle mode.
     */
    public void abandonNoTower() {
        Team playerTeam = battleController.getPlayerTeam();
        if (noTowerSession.isActive()) {
            leaderboardController.recordNoTowerRun(
                    gameRepository.getUser(),
                    playerTeam,
                    noTowerSession.getRunStat());
        }
        noTowerSession.clear();
        currentGameMode = freeBattleStrategy;
        navigateTo(ScreenManager.MenuState.MAIN);
    }

    /**
     * @return the last pending tower battle outcome, or {@code null} if none is pending
     */
    public NOProgressionResult consumeNoTowerBattleOutcome() {
        return noTowerSession.consumeOutcome();
    }

    public void startNoTowerSession(NO adventure) {
        noTowerSession.start(adventure);
    }

    public boolean isNoTowerSessionActive() {
        return noTowerSession.isActive();
    }

    public NO getNoTowerAdventure() {
        return noTowerSession.getAdventure();
    }

    /**
     * @param won           whether the player won the battle
     * @param floorBefore   the floor number before the battle
     * @param result        the progression result (advance, game-over, etc.)
     * @param enemyFainted  number of enemy Bugemons that fainted
     * @param playerFainted number of player Bugemons that fainted
     */
    public void recordNoTowerBattle(boolean won, int floorBefore, NOProgressionResult result, int enemyFainted, int playerFainted) {
        noTowerSession.recordBattle(won, floorBefore, result, enemyFainted, playerFainted);
    }

    public void setNoTowerOutcome(NOProgressionResult outcome) {
        noTowerSession.setOutcome(outcome);
    }

    public TowerRunStat getNoTowerRunStat() {
        return noTowerSession.getRunStat();
    }

    public void clearNoTowerSession() {
        noTowerSession.clear();
    }

    public GameMode getCurrentGameMode() {
        return currentGameMode.getGameMode();
    }

    public GameRepository getGameRepository() {
        return gameRepository;
    }

    public Integer getCurrentUserId() {
        return gameRepository.getCurrentUserId();
    }

    public int getNoTowerCurrentFloorNumber() {
        return noTowerSession.getCurrentFloorNumber();
    }

    /**
     * Wires the leaderboard view to its controller and populates it with current entries.
     *
     * @param view the leaderboard view to configure
     */
    public void configureLeaderboard(ulb.view.menu.LeaderboardView view) {
        leaderboardController.setLeaderboardView(view);
        view.setListener(leaderboardController);
        leaderboardController.populateLeaderboard();
    }

    public LeaderboardController getLeaderboardController(){
        return leaderboardController;
    }

    LoginService getLoginService() {
        return loginService;
    }

    private void navigateTo(ScreenManager.MenuState state) {
        try {
            screenManager.showScreen(state);
        } catch (ScreenLoadingException e) {
            handleNavigationFailure(state, e);
        }
    }

    /** Resets the active strategy to free-battle; called by {@link NoTowerStrategy} after a run ends. */
    public void switchToFreeBattleMode() {
        currentGameMode = freeBattleStrategy;
    }

    /** Shows a blocking error dialogue and closes the application when a screen cannot be loaded. */
    private void handleNavigationFailure(ScreenManager.MenuState state, ScreenLoadingException exception) {
        navigationFailureView.show(state.name(), exception.getMessage());
        exitApplication();
    }
}
