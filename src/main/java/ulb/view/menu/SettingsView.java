package ulb.view.menu;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import ulb.controller.SoundController;

/**
 * View for the settings screen.
 *
 * <p>Currently only exposes a Back button. The Escape key is registered as a scene-level
 * event filter so it works regardless of which node has focus; the filter is properly
 * removed when the view leaves the scene to avoid listener leaks.
 *
 * <p><b>MVC role:</b> View only.</p>
 */
public class SettingsView {
    private Listener listener;

    @FXML private Button btnBack;

    private javafx.event.EventHandler<KeyEvent> escapeHandler;

    public interface Listener {
        void onBackClicked();
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @FXML
        void initialize() {
        escapeHandler = event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                handleBack();
                event.consume();
            }
        };
        btnBack.sceneProperty().addListener((obs, old, scene) -> {
            if (old != null) old.removeEventFilter(KeyEvent.KEY_PRESSED, escapeHandler);
            if (scene != null) {
                scene.addEventFilter(KeyEvent.KEY_PRESSED, escapeHandler);
                Platform.runLater(btnBack::requestFocus);
            }
        });
    }

    @FXML
        void handleBack() {
        SoundController.getInstance().playButtonClick();
        if (listener != null) {
            listener.onBackClicked();
        }
    }
}
