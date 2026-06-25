package ulb.controller.battle.inventory;

import ulb.controller.ApplicationController;
import ulb.controller.battle.BattleController;
import ulb.service.battle.BattleSystem;
import ulb.model.inventory.Item;
import ulb.model.team.Team;
import ulb.view.battle.inventory.InventorySelectionView;

import java.util.List;

/**
 * Controller for selecting an item to use during battle.
 *
 * <p>This controller manages the inventory selection screen that is displayed when
 * the player chooses the "Items" option in the battle menu. It populates the view
 * with the player team's current inventory and handles the selection or cancellation
 * of an item.</p>
 *
 * <p>It implements {@link InventorySelectionView.Listener} to receive user interaction
 * callbacks from the view (item selected, back button clicked).</p>
 *
 * <p><b>Coordination with {@link BattleController}:</b> when an item is selected,
 * the game screen is restored first (so the battle view can catch subsequent dialogue
 * events), and then the actual item usage is delegated to
 * {@link BattleController#onItemSelected(int)}.</p>
 *
 * <p><b>Lifecycle:</b> instantiated by {@link ulb.controller.ScreenManager} each time the inventory
 * screen is shown. The constructor performs all wiring and populates the inventory
 * display immediately.</p>
 */
public final class InventorySelectionController implements InventorySelectionView.Listener {
    private final ApplicationController applicationController;
    private final BattleSystem battleSystem;
    private final Team playerTeam;
    private final BattleController battleController;

    /**
     * Constructs a new {@code InventorySelectionController}, wires it to the given
     * view, and immediately displays the player team's inventory.
     *
     * @param view             the inventory selection view; must not be {@code null}
     * @param applicationController the root controller for screen navigation; must not be {@code null}
     * @param battleSystem     the battle system providing battle state; may be {@code null}
     *                         (in which case item selection is silently rejected)
     * @param playerTeam       the player's team whose inventory will be displayed;
     *                         may be {@code null} (item selection will be silently rejected)
     * @param battleController the battle controller to notify once an item is selected;
     *                         must not be {@code null}
     */
    public InventorySelectionController(
            InventorySelectionView view,
            ApplicationController applicationController,
            BattleSystem battleSystem,
            Team playerTeam,
            BattleController battleController
    ) {
        this.applicationController = applicationController;
        this.battleSystem = battleSystem;
        this.playerTeam = playerTeam;
        this.battleController = battleController;

        view.setListener(this);
        view.displayInventory(playerTeam);
    }

    /**
     * Called by the view when the player taps an item in the inventory list.
     *
     * <p>Validates the selection (non-null battle system, non-null team and inventory,
     * valid index). If validation passes:</p>
     * <ol>
     *   <li>Navigates back to the game screen via
     *       {@link ApplicationController#showGameScreen()} so the battle
     *       view is active and ready to receive dialogue events.</li>
     *   <li>Delegates the item usage to {@link BattleController#onItemSelected(int)}.</li>
     * </ol>
     *
     * <p>Does nothing silently if any precondition is not met.</p>
     *
     * @param index zero-based index of the selected item in the inventory list
     */
    @Override
    public void onItemSelected(int index) {
        if (battleSystem == null || playerTeam == null) {
            return;
        }

        List<Item> items = playerTeam.getInventoryItems();
        if (index < 0 || index >= items.size()) {
            return;
        }

        applicationController.showGameScreen();
        battleController.onItemSelected(index);
    }

    @Override
    public void onBackClicked() {
        applicationController.showGameScreen();
        battleController.promptNextActionFromExternal();
    }
}
