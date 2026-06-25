package ulb.controller.battle.party;

import ulb.controller.ApplicationController;
import ulb.controller.battle.BattleController;
import ulb.service.battle.BattleSystem;
import ulb.model.domain.Bugemon;
import ulb.model.team.Team;
import ulb.view.battle.party.PartySelectionView;

/**
 * Controller for selecting the next active Bugemon during battle.
 *
 * <p>This controller manages the party selection screen, which is displayed when
 * the player opens their team view mid-battle (e.g., to switch to a different Bugemon).
 * It populates the view with the current state of the player's team and handles
 * Bugemon selection and cancellation.</p>
 *
 * <p>It implements {@link PartySelectionView.Listener} to receive callbacks from the
 * view when the player taps a Bugemon or presses the "Back" button.</p>
 *
 * <p><b>Lifecycle:</b> instantiated by {@link ulb.controller.ScreenManager} each time the party
 * selection screen is shown. The constructor performs all wiring and immediately
 * populates the view with the team, highlighting the currently active Bugemon.</p>
 */
public final class PartySelectionController implements PartySelectionView.Listener {
    private final ApplicationController applicationController;
    private final BattleSystem battleSystem;
    private final Team playerTeam;
    private final BattleController battleController;

    public PartySelectionController(PartySelectionView view, ApplicationController applicationController, BattleSystem battleSystem, Team playerTeam, BattleController battleController) {
        this.applicationController = applicationController;
        this.battleSystem = battleSystem;
        this.playerTeam = playerTeam;
        this.battleController = battleController;

        view.setListener(this);
        view.setDomainExpansionResolver(bugemon -> battleSystem == null
                ? java.util.Optional.empty()
                : java.util.Optional.ofNullable(battleSystem.getDomainExpansionFor(bugemon)));
        view.displayParty(playerTeam, battleSystem == null ? null : battleSystem.getActiveBugemonOfPlayer());
    }

    /**
     * Called by the view when the player taps a Bugemon card in the party list.
     *
     * <p>Validates the selection:</p>
     * <ul>
     *   <li>Rejects the call silently if {@code battleSystem}, {@code playerTeam},
     *       or the index is out of range.</li>
     *   <li>Rejects the call silently if the Bugemon at the given index is {@code null}
     *       or is not healthy (i.e., has fainted).</li>
     * </ul>
     *
     * <p>If the selection is valid, navigates back to the game screen first, then
     * delegates to {@link BattleController#onBugemonSelected(Bugemon)} so the
     * switch message is queued on the active battle view.</p>
     *
     * @param index zero-based index of the selected Bugemon in the player team's member list
     */
    @Override
    public void onBugemonSelected(int index) {
        if (battleSystem == null || playerTeam == null || index < 0 || index >= playerTeam.size()) {
            return;
        }

        Bugemon bugemon = playerTeam.getTeamMember(index).orElse(null);
        if (bugemon == null || !bugemon.isHealthy()) {
            return;
        }
        applicationController.showGameScreen();
        battleController.onBugemonSelected(bugemon);
        battleController.promptNextActionFromExternal();
    }

    @Override
    public void onBackClicked() {
        applicationController.showGameScreen();
        battleController.promptNextActionFromExternal();
    }
}
