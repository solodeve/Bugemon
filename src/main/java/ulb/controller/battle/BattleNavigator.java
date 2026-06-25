package ulb.controller.battle;

/**
 * Navigation callbacks that the battle subsystem needs from the application.
 *
 * <p>Isolates {@link BattleController} and {@link BattleTurnHandler} from the
 * concrete {@code ApplicationController} type so they can be tested independently.</p>
 */
public interface BattleNavigator {
    void openPartySelection();
    void openInventorySelection();
    void openLevelUpScreen();
    void handleBattleFinished(boolean won);
}
