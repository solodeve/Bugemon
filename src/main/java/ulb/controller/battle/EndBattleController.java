package ulb.controller.battle;

import ulb.controller.ApplicationController;
import ulb.service.battle.BattleSystem;
import ulb.model.domain.Bugemon;
import ulb.view.battle.EndBattleView;

import java.util.List;
import java.util.Set;

/**
 * Controller for the end-battle screen.
 */
public final class EndBattleController implements EndBattleView.Listener {
    private final ApplicationController applicationController;

    public EndBattleController(EndBattleView view, ApplicationController applicationController,
                               BattleSystem battleSystem) {
        this.applicationController = applicationController;
        view.setListener(this);
        boolean won = battleSystem != null && battleSystem.getHasWon();
        view.displayBattleResult(won);
        Set<Bugemon> winningBugemons = battleSystem.getWinningBugemons();
        boolean showProgression = !battleSystem.isFreeBattleMode();
        view.displayWinningBugemons(winningBugemons, showProgression);
        view.displayUnlockedBugemons(showProgression
                ? applicationController.consumePendingUnlockedBugemons()
                : List.of());
    }

    @Override
    public void onBackToMainMenuClicked() {
        applicationController.returnToMainMenu();
    }
}
