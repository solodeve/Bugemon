package ulb.controller.battle;

import java.util.List;
import java.util.Objects;

import ulb.service.battle.BattleSystem;
import ulb.model.domain.Bugemon;
import ulb.model.domain.Skill;
import ulb.model.team.Team;
import ulb.repository.GameRepository;
import ulb.view.battle.state.BattleIntroUiState;
import ulb.view.battle.BattleView;
import ulb.view.battle.state.ColouredMessage;

/**
 * Coordinator responsible for connecting the battle logic
 * with the battle user interface.
 */
public class BattleController implements BattleView.Listener {

    private final BattleSystem battleSystem;
    private final BattleNavigator navigator;
    private final BattleUiBuilder uiBuilder;
    private final BattleTurnHandler turnHandler;

    record BattleSession(Team enemyTeam, BattleIntroUiState introState) {}

    private BattleView gameMenuView;
    private BattleSession pendingBattleSession;

    public BattleController(BattleNavigator navigator, GameRepository gameRepository) {
        this.navigator = navigator;
        this.battleSystem = new BattleSystem(gameRepository);
        this.uiBuilder = new BattleUiBuilder(battleSystem);
        this.turnHandler = new BattleTurnHandler(battleSystem, navigator, uiBuilder);

    }

    /**
     * Called when the space key is pressed in the view.
     * Advances the current dialogue without changing the battle control state.
     * The controls are unlocked only when the dialogue queue reports that it is empty.
     */
    @Override
    public void onSpacePressed() {
        BattleView view = requireBattleView();
        if (!view.isMessageQueueEmpty()) {
            view.updateMessageDisplay();
        }
    }

    public void setBattleView(BattleView gameMenuView) {
        this.gameMenuView = Objects.requireNonNull(gameMenuView, "Game menu view is required.");
        this.gameMenuView.setListener(this);
        this.gameMenuView.setXpUiVisible(!battleSystem.isFreeBattleMode());
        this.gameMenuView.setOnDialogQueueEmpty(turnHandler::unlockBattleControlsIfReady);
        this.gameMenuView.restoreEvolvedState(battleSystem.getEvolvedPlayerBugemonIds());
        this.gameMenuView.syncBackground(battleSystem.getActiveBackgroundPath());
        turnHandler.setBattleView(gameMenuView);
        turnHandler.setOnTurnComplete(() -> {
            refreshBattleUI();
            showMainBattleMenu();
            turnHandler.promptNextActionFromExternal();
        });
        refreshBattleUI();
    }

    public BattleSystem getBattleSystem() { return battleSystem; }

    public Team getPlayerTeam() { return battleSystem.getPlayerTeam(); }

    public Team getEnemyTeam() { return battleSystem.getEnemyTeam(); }

    public void setPlayerTeam(Team team) { battleSystem.setPlayerTeam(team); }

    public void setTeamPersistenceType(String type) { battleSystem.setTeamPersistenceType(type); }

    public void setBattleInitialized(boolean battleInitialized) {
        turnHandler.setBattleInitialized(battleInitialized);
    }

    public void prepareBattle(Team playerTeam, Team enemyTeam, BattleIntroUiState introState) {
        battleSystem.setPlayerTeam(playerTeam);
        turnHandler.reset();
        pendingBattleSession = new BattleSession(enemyTeam, introState);
    }

    public void openGameScreen() {
        if (!turnHandler.isInitialized()) {
            setUpBattle();
            return;
        }
        refreshBattleUI();
        showMainBattleMenu();
        requireBattleView().setBattleControlsDisabled(!turnHandler.isWaitingInput());
    }

    public void setUpBattle() {
        Team enemyTeam = pendingBattleSession == null ? null : pendingBattleSession.enemyTeam();
        battleSystem.initializeBattleWithTeam(enemyTeam);
        refreshBattleUI();
        showMainBattleMenu();
        turnHandler.beginSetup(resolveIntroState());
    }

    public void refreshBattleUI() {
        BattleView view = requireBattleView();
        Bugemon player = battleSystem.getActiveBugemonOfPlayer();
        view.syncBackground(battleSystem.getActiveBackgroundPath());
        view.refreshBattle(uiBuilder.buildBattleUiState());
        uiBuilder.updateXpBarForBugemon(view, player);
        if (player != null) {
            List<Skill> skills = battleSystem.getActivePlayerBugemonSkills();
            view.updateSkills(uiBuilder.buildBattleSkillsState(player));
            view.updateSkillColors(
                    uiBuilder.getSkillElementOrNull(skills, 0),
                    uiBuilder.getSkillElementOrNull(skills, 1),
                    uiBuilder.getSkillElementOrNull(skills, 2)
            );
        }
    }

    @Override
    public void onAttackClicked() {
        BattleView view = requireBattleView();
        if (!turnHandler.canAcceptInput()) return;
        Bugemon active = battleSystem.getActiveBugemonOfPlayer();
        List<Skill> skills = battleSystem.getActivePlayerBugemonSkills();
        if (active == null || skills.isEmpty()) {
            view.displayNoSkillsAvailable(true);
            return;
        }
        battleSystem.enterSelectionState();
        refreshBattleUI();
        showAttackMenu();
    }

    @Override
    public void onTeamClicked() {
        if (!turnHandler.canAcceptInput()) return;
        navigator.openPartySelection();
    }

    @Override
    public void onItemsClicked() {
        BattleView view = requireBattleView();
        if (!turnHandler.canAcceptInput()) return;
        Team playerTeam = battleSystem.getPlayerTeam();
        if (playerTeam == null) {
            throw new IllegalStateException("Cannot open inventory without a player team.");
        }
        if (battleSystem.isPlayerInventoryEmpty()) {
            view.displayNoItemsAvailable(true);
            return;
        }
        battleSystem.enterSelectionState();
        navigator.openInventorySelection();
    }

    @Override
    public void onSpecialClicked() {
        if (!turnHandler.canAcceptInput()) return;
        turnHandler.processSpecial();
    }

    @Override
    public void onGiveUpClicked() {
        if (!turnHandler.canAcceptInput()) return;
        battleSystem.battleOver(false);
    }

    @Override
    public void onReturnClicked() {
        if (!turnHandler.canAcceptInput()) return;
        battleSystem.enterSelectionState();
        refreshBattleUI();
        showMainBattleMenu();
        turnHandler.promptNextActionFromExternal();
    }

    @Override
    public void onEvolutionCompleted() {
        battleSystem.markActivePlayerBugemonEvolved();
    }

    public void onSkillSelected(int skillIndex) {
        if (!turnHandler.canAcceptInput()) return;
        turnHandler.processTurn(skillIndex);
    }

    public boolean handleEscapePressed() {
        BattleView view = requireBattleView();
        if (turnHandler.isShowingMessages() || view.areBattleControlsDisabled()) return false;
        if (view.isAttackMenuVisible()) { onReturnClicked(); return true; }
        if (view.isMainMenuVisible())   { onGiveUpClicked(); return true; }
        return false;
    }

    public void onItemSelected(int itemIndex) { turnHandler.processItemTurn(itemIndex); }

    public void onBugemonSelected(Bugemon bugemon) { turnHandler.onBugemonSelected(bugemon); }

    /**
     * Public wrapper to prompt the next action for the active Bugemon. Useful
     * for external controllers that return to the battle screen and need the
     * dialogue prompt to appear.
     */
    public void promptNextActionFromExternal() { turnHandler.promptNextActionFromExternal(); }

    private void showMainBattleMenu() {
        BattleView view = requireBattleView();
        view.showAttackButtons(false);
        view.showMainButtons(true);
        view.setBattleControlsDisabled(false);
    }

    private void showAttackMenu() {
        BattleView view = requireBattleView();
        view.showMainButtons(false);
        view.showAttackButtons(true);
        view.setBattleControlsDisabled(false);
    }

    /** Converts a battle message to the coloured view message format. Used by tests. */
    public ColouredMessage toViewMessage(ulb.event.BattleMessage message) {
        return uiBuilder.toViewMessage(message);
    }

    private BattleView requireBattleView() {
        if (gameMenuView == null) {
            throw new IllegalStateException("BattleController requires a BattleView before handling battle UI actions.");
        }
        return gameMenuView;
    }

    /** Falls back to the default intro if no specific intro was set for this session. */
    private BattleIntroUiState resolveIntroState() {
        if (pendingBattleSession != null && pendingBattleSession.introState() != null) {
            return pendingBattleSession.introState();
        }
        return BattleIntroUiState.defaultIntro();
    }
}
