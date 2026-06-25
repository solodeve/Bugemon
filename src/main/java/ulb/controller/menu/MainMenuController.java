package ulb.controller.menu;

import ulb.controller.ApplicationController;
import ulb.view.menu.MainMenuView;

/**
 * Handles navigation events from the main menu: dispatches to the appropriate game mode or screen.
 */
public final class MainMenuController implements MainMenuView.MainMenuListener {
    private final ApplicationController applicationController;

    /**
     * @param view the main menu view to bind
     * @param applicationController the root controller for navigation
     */
    public MainMenuController(MainMenuView view, ApplicationController applicationController) {
        this.applicationController = applicationController;
        view.setListener(this);
    }

    @Override
    public void onFreeBattleClicked() {
        applicationController.openFreeBattleTeamSelection();
    }

    @Override
    public void onNoTowerClicked() {
        applicationController.openNoTowerTeamSelection();
    }

    @Override
    public void onLeaderboardClicked(){
        applicationController.openLeaderboard();
    }

    @Override
    public void onRewardsClicked() {
        applicationController.openRewards();
    }

    @Override
    public void onSettingsClicked() {
        applicationController.openSettings();
    }

    @Override
    public void onQuitClicked() {
        applicationController.logout();
    }
}
