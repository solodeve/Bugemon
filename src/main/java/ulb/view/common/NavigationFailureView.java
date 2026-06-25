package ulb.view.common;

import javafx.scene.control.Alert;

/**
 * Displays a modal error dialog when the application fails to navigate to a screen.
 *
 * <p>Used by the view loader to report FXML loading failures without crashing the app.
 * The dialog blocks the calling thread until the user dismisses it.
 */
public final class NavigationFailureView {

    /**
     * Shows a blocking error dialog.
     *
     * @param screenName    human-readable name of the screen that could not be opened
     * @param detailMessage technical detail or exception message shown in the dialog body
     */
    public void show(String screenName, String detailMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Navigation error");
        alert.setHeaderText("Unable to open screen " + screenName + ".");
        alert.setContentText(detailMessage);
        alert.showAndWait();
    }
}
