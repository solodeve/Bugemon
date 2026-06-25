package ulb.controller.menu;

import ulb.controller.ApplicationController;
import ulb.view.menu.SettingsView;

/**
 * Handles user interactions on the settings screen.
 */
public final class SettingsController implements SettingsView.Listener {
    private final ApplicationController applicationController;

    /**
     * @param view the settings view to attach this controller to
     * @param applicationController the root controller for navigation; must not be {@code null}
     */
    public SettingsController(SettingsView view, ApplicationController applicationController) {
        this.applicationController = applicationController;
        view.setListener(this);
    }

    @Override
    public void onBackClicked() {
        applicationController.returnToMainMenu();
    }
}
