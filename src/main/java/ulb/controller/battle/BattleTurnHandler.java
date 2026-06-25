package ulb.controller.battle;

import java.util.ArrayDeque;
import java.util.Queue;

import ulb.event.BattleEndEvent;
import ulb.event.LevelUpEvent;
import ulb.event.BattleMessage;
import ulb.model.domain.BattleState;
import ulb.model.domain.Bugemon;
import ulb.service.battle.BattleSystem;
import ulb.view.battle.state.BattleIntroUiState;
import ulb.view.battle.BattleView;

/**
 * Owns all turn-execution state: the controller state machine, the pending-navigation
 * queue, and the message-auto-display flag. Handles turn processing, dialogue queuing,
 * and end-of-turn navigation (level-up, battle end).
 */
class BattleTurnHandler {

    enum State { NOT_INITIALIZED, SHOWING_MESSAGES, WAITING_PLAYER_INPUT }
    enum PendingNavigation { LEVEL_UP, BATTLE_END }

    private final BattleSystem battleSystem;
    private final BattleNavigator navigator;
    private final BattleUiBuilder uiBuilder;

    private BattleView battleView;
    private State state = State.NOT_INITIALIZED;
    private final Queue<PendingNavigation> pendingNavigations = new ArrayDeque<>();
    private boolean shouldAutoDisplayNextMessage;
    private Runnable onTurnComplete;

    BattleTurnHandler(BattleSystem battleSystem, BattleNavigator navigator,
                      BattleUiBuilder uiBuilder) {
        this.battleSystem = battleSystem;
        this.navigator = navigator;
        this.uiBuilder = uiBuilder;
        battleSystem.setOnBattleEnd(this::queueBattleEnd);
        battleSystem.setOnMessageUpdate(this::queueBattleMessage);
        battleSystem.setOnLevelUp(this::queueLevelUpScreen);
    }

    void setBattleView(BattleView view) { this.battleView = view; }
    void setOnTurnComplete(Runnable callback) { this.onTurnComplete = callback; }

    boolean isInitialized()      { return state != State.NOT_INITIALIZED; }
    boolean isShowingMessages()  { return state == State.SHOWING_MESSAGES; }
    boolean isWaitingInput()     { return state == State.WAITING_PLAYER_INPUT; }

    void reset() {
        state = State.NOT_INITIALIZED;
        pendingNavigations.clear();
        shouldAutoDisplayNextMessage = false;
    }

    void setBattleInitialized(boolean initialized) {
        state = initialized ? State.WAITING_PLAYER_INPUT : State.NOT_INITIALIZED;
    }

    void beginSetup(BattleIntroUiState introState) {
        pendingNavigations.clear();
        state = State.SHOWING_MESSAGES;
        shouldAutoDisplayNextMessage = false;
        BattleView view = requireView();
        view.setBattleControlsDisabled(true);
        view.displayBattleIntro(introState, true);
    }

    boolean canAcceptInput() {
        BattleView view = requireView();
        return state == State.WAITING_PLAYER_INPUT
                && pendingNavigations.isEmpty()
                && !view.areBattleControlsDisabled();
    }

    void processTurn(int skillIndex) {
        BattleView view = requireView();
        beginTurnMessageSequence(view);
        executeBattleAction(() -> battleSystem.executePlayerSkill(skillIndex));
        if (view.isMessageQueueEmpty()) unlockBattleControlsIfReady();
    }

    void processItemTurn(int itemIndex) {
        BattleView view = requireView();
        beginTurnMessageSequence(view);
        executeBattleAction(() -> battleSystem.executePlayerItemAt(itemIndex));
        if (view.isMessageQueueEmpty()) unlockBattleControlsIfReady();
    }

    void processSpecial() {
        BattleView view = requireView();
        beginTurnMessageSequence(view);
        executeBattleAction(battleSystem::executePlayerDomain);
        if (view.isMessageQueueEmpty()) unlockBattleControlsIfReady();
    }

    void onBugemonSelected(Bugemon bugemon) {
        BattleView view = requireView();
        beginTurnMessageSequence(view);
        battleSystem.selectBugemon(bugemon);
        if (view.isMessageQueueEmpty()) unlockBattleControlsIfReady();
    }

    /**
     * Public wrapper to prompt the next action for the active Bugemon. Useful
     * for external controllers that return to the battle screen and need the
     * dialogue prompt to appear.
     */
    void promptNextActionFromExternal() { promptNextAction(); }

    private void beginTurnMessageSequence(BattleView view) {
        state = State.SHOWING_MESSAGES;
        shouldAutoDisplayNextMessage = true;
        view.showAttackButtons(false);
        view.showMainButtons(true);
        view.setBattleControlsDisabled(true);
    }

    private void executeBattleAction(Runnable action) {
        String prevBackground = battleSystem.getActiveBackgroundPath();
        action.run();
        updateBackgroundAfterAction(prevBackground);
    }

    /**
     * Plays a background transition if the active domain changed the arena.
     * Skipped when the battle is already over to avoid animating a dead state.
     */
    private void updateBackgroundAfterAction(String previousPath) {
        if (battleView == null) return;
        String currentPath = battleSystem.getActiveBackgroundPath();
        if (currentPath.equals(previousPath)) return;
        if (battleSystem.getBattleState() == BattleState.END) return;
        battleView.playDomainBackgroundTransition(currentPath, battleSystem.isPlayerActiveDomainCaster());
    }

    /**
     * Checks whether all end-of-turn dialogue messages have been read.
     * If ready, triggers pending events like Level Up screen, Battle End,
     * or restores battle controls for the next turn.
     */
    void unlockBattleControlsIfReady() {
        BattleView view = requireView();
        if (!view.isMessageQueueEmpty()) return;
        PendingNavigation next = pollNextPendingNavigation();
        if (next != null) {
            state = State.WAITING_PLAYER_INPUT;
            handlePendingNavigation(next);
            return;
        }
        if (state != State.SHOWING_MESSAGES) return;
        state = State.WAITING_PLAYER_INPUT;
        shouldAutoDisplayNextMessage = false;
        if (onTurnComplete != null) onTurnComplete.run();
    }

    /**
     * If messages are still showing, queues the battle-end navigation so it fires only
     * after the last message is acknowledged. Otherwise fires immediately.
     */
    private void queueBattleEnd(BattleEndEvent event) {
        if (state == State.SHOWING_MESSAGES) queuePending(PendingNavigation.BATTLE_END);
        else navigator.handleBattleFinished(event.won());
    }

    private void queueLevelUpScreen(LevelUpEvent event) {
        Bugemon bugemon = event.bugemon();
        if (bugemon != null) uiBuilder.updateXpBarForBugemon(requireView(), bugemon);
        if (state == State.SHOWING_MESSAGES) queuePending(PendingNavigation.LEVEL_UP);
        else navigator.openLevelUpScreen();
    }

    private void handlePendingNavigation(PendingNavigation nav) {
        if (nav == PendingNavigation.LEVEL_UP) { navigator.openLevelUpScreen(); return; }
        if (nav == PendingNavigation.BATTLE_END) navigator.handleBattleFinished(battleSystem.getHasWon());
    }

    private void queuePending(PendingNavigation nav) {
        if (nav != null && !pendingNavigations.contains(nav)) pendingNavigations.add(nav);
    }

    /** LEVEL_UP is always drained before BATTLE_END so the XP screen never gets skipped. */
    private PendingNavigation pollNextPendingNavigation() {
        if (pendingNavigations.remove(PendingNavigation.LEVEL_UP)) return PendingNavigation.LEVEL_UP;
        return pendingNavigations.poll();
    }

    /**
     * The first message of a turn sequence auto-advances; subsequent messages wait for
     * the player to press Space. {@code shouldAutoDisplayNextMessage} is consumed here
     * so only the very first message of each action plays automatically.
     */
    private void queueBattleMessage(BattleMessage message) {
        if (battleView == null) return;
        boolean auto = shouldAutoDisplayNextMessage;
        battleView.updateMessage(uiBuilder.toViewMessage(message), auto);
        shouldAutoDisplayNextMessage = false;
    }

    private void promptNextAction() {
        String activeName = battleSystem.getActivePlayerBugemonName();
        if (activeName != null) {
            BattleView view = requireView();
            view.askingNextBugemonAction(activeName);
            view.updateMessageDisplay();
        }
    }

    private BattleView requireView() {
        if (battleView == null) {
            throw new IllegalStateException("BattleTurnHandler requires a BattleView.");
        }
        return battleView;
    }
}
