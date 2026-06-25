package ulb.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ulb.exception.ScreenLoadingException;

import static org.junit.jupiter.api.Assertions.*;

class TestScreenManager {

    // ── getCurrentState après showScreen ────────────────────────────────────

    @Test
    @DisplayName("showScreen sets current state to LOGIN")
    void showScreenSetsStateLogin() throws ScreenLoadingException {
        ScreenManager sm = new ScreenManager(null, null, null);
        sm.showScreen(ScreenManager.MenuState.LOGIN);
        assertEquals(ScreenManager.MenuState.LOGIN, sm.getCurrentState());
    }

    @Test
    @DisplayName("showScreen sets current state to MAIN")
    void showScreenSetsStateMain() throws ScreenLoadingException {
        ScreenManager sm = new ScreenManager(null, null, null);
        sm.showScreen(ScreenManager.MenuState.MAIN);
        assertEquals(ScreenManager.MenuState.MAIN, sm.getCurrentState());
    }

    @Test
    @DisplayName("showScreen sets current state to TEAM_SELECT")
    void showScreenSetsStateTeamSelect() throws ScreenLoadingException {
        ScreenManager sm = new ScreenManager(null, null, null);
        sm.showScreen(ScreenManager.MenuState.TEAM_SELECT);
        assertEquals(ScreenManager.MenuState.TEAM_SELECT, sm.getCurrentState());
    }

    @Test
    @DisplayName("showScreen sets current state to GAME")
    void showScreenSetsStateGame() throws ScreenLoadingException {
        ScreenManager sm = new ScreenManager(null, null, null);
        sm.showScreen(ScreenManager.MenuState.GAME);
        assertEquals(ScreenManager.MenuState.GAME, sm.getCurrentState());
    }

    @Test
    @DisplayName("showScreen sets current state to NO_TOWER")
    void showScreenSetsStateNoTower() throws ScreenLoadingException {
        ScreenManager sm = new ScreenManager(null, null, null);
        sm.showScreen(ScreenManager.MenuState.NO_TOWER);
        assertEquals(ScreenManager.MenuState.NO_TOWER, sm.getCurrentState());
    }

    @Test
    @DisplayName("showScreen sets current state to END_BATTLE")
    void showScreenSetsStateEndBattle() throws ScreenLoadingException {
        ScreenManager sm = new ScreenManager(null, null, null);
        sm.showScreen(ScreenManager.MenuState.END_BATTLE);
        assertEquals(ScreenManager.MenuState.END_BATTLE, sm.getCurrentState());
    }

    @Test
    @DisplayName("showScreen sets current state to PARTY")
    void showScreenSetsStateParty() throws ScreenLoadingException {
        ScreenManager sm = new ScreenManager(null, null, null);
        sm.showScreen(ScreenManager.MenuState.PARTY);
        assertEquals(ScreenManager.MenuState.PARTY, sm.getCurrentState());
    }

    @Test
    @DisplayName("showScreen sets current state to LEVELUP")
    void showScreenSetsStateLevelup() throws ScreenLoadingException {
        ScreenManager sm = new ScreenManager(null, null, null);
        sm.showScreen(ScreenManager.MenuState.LEVELUP);
        assertEquals(ScreenManager.MenuState.LEVELUP, sm.getCurrentState());
    }

    @Test
    @DisplayName("showScreen sets current state to INVENTORY")
    void showScreenSetsStateInventory() throws ScreenLoadingException {
        ScreenManager sm = new ScreenManager(null, null, null);
        sm.showScreen(ScreenManager.MenuState.INVENTORY);
        assertEquals(ScreenManager.MenuState.INVENTORY, sm.getCurrentState());
    }

    @Test
    @DisplayName("showScreen sets current state to TEAM_EDITOR")
    void showScreenSetsStateTeamEditor() throws ScreenLoadingException {
        ScreenManager sm = new ScreenManager(null, null, null);
        sm.showScreen(ScreenManager.MenuState.TEAM_EDITOR);
        assertEquals(ScreenManager.MenuState.TEAM_EDITOR, sm.getCurrentState());
    }

    @Test
    @DisplayName("showScreen sets current state to REWARDS")
    void showScreenSetsStateRewards() throws ScreenLoadingException {
        ScreenManager sm = new ScreenManager(null, null, null);
        sm.showScreen(ScreenManager.MenuState.REWARDS);
        assertEquals(ScreenManager.MenuState.REWARDS, sm.getCurrentState());
    }

    @Test
    @DisplayName("showScreen sets current state to SETTINGS")
    void showScreenSetsStateSettings() throws ScreenLoadingException {
        ScreenManager sm = new ScreenManager(null, null, null);
        sm.showScreen(ScreenManager.MenuState.SETTINGS);
        assertEquals(ScreenManager.MenuState.SETTINGS, sm.getCurrentState());
    }

    @Test
    @DisplayName("showScreen sets current state to LEADERBOARD")
    void showScreenSetsStateLeaderboard() throws ScreenLoadingException {
        ScreenManager sm = new ScreenManager(null, null, null);
        sm.showScreen(ScreenManager.MenuState.LEADERBOARD);
        assertEquals(ScreenManager.MenuState.LEADERBOARD, sm.getCurrentState());
    }

    // ── shouldIgnoreEscape (null controller) ─────────────────────────────────

    @Test
    @DisplayName("Ignore escape for TEAM_SELECT when controller is absent")
    void ignoresEscapeWhenControllerNullAndStateIsTeamSelect() throws ScreenLoadingException {
        ScreenManager sm = new ScreenManager(null, null, null);
        sm.showScreen(ScreenManager.MenuState.TEAM_SELECT);
        assertTrue(sm.shouldIgnoreEscape());
    }

    @Test
    @DisplayName("Ignore escape for SETTINGS when controller is absent")
    void ignoresEscapeWhenControllerNullAndStateIsSettings() throws ScreenLoadingException {
        ScreenManager sm = new ScreenManager(null, null, null);
        sm.showScreen(ScreenManager.MenuState.SETTINGS);
        assertTrue(sm.shouldIgnoreEscape());
    }

    @Test
    @DisplayName("Ignore escape for LEADERBOARD when controller is absent")
    void ignoresEscapeWhenControllerNullAndStateIsLeaderboard() throws ScreenLoadingException {
        ScreenManager sm = new ScreenManager(null, null, null);
        sm.showScreen(ScreenManager.MenuState.LEADERBOARD);
        assertTrue(sm.shouldIgnoreEscape());
    }

    @Test
    @DisplayName("Do not ignore escape for MAIN even when controller is absent")
    void doesNotIgnoreEscapeForMainWithoutController() throws ScreenLoadingException {
        ScreenManager sm = new ScreenManager(null, null, null);
        sm.showScreen(ScreenManager.MenuState.MAIN);
        assertFalse(sm.shouldIgnoreEscape());
    }

    @Test
    @DisplayName("Do not ignore escape for GAME even when controller is absent")
    void doesNotIgnoreEscapeForGameWithoutController() throws ScreenLoadingException {
        ScreenManager sm = new ScreenManager(null, null, null);
        sm.showScreen(ScreenManager.MenuState.GAME);
        assertFalse(sm.shouldIgnoreEscape());
    }

    @Test
    @DisplayName("Do not ignore escape for LEVELUP even when controller is absent")
    void doesNotIgnoreEscapeForLevelupWithoutController() throws ScreenLoadingException {
        ScreenManager sm = new ScreenManager(null, null, null);
        sm.showScreen(ScreenManager.MenuState.LEVELUP);
        assertFalse(sm.shouldIgnoreEscape());
    }

}
