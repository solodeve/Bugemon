package ulb.view.menu;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * View for the login/registration screen.
 *
 * <p>Client-side validation runs before forwarding any event to the listener:
 * username must be non-blank, password must be non-blank and at least 4 characters.
 * Validation errors are shown inline via {@link #showError} — no request reaches the
 * listener until the inputs pass.
 *
 * <p><b>MVC role:</b> View only. Contains no authentication logic.</p>
 */
public class LoginView {
    private LoginListener listener;

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label messageLabel;

    public interface LoginListener {
        void onLoginRequested(String username, String password);
        void onRegisterRequested(String username, String password);
        void onQuitRequested();
    }

    public void setListener(LoginListener listener) {
        this.listener = listener;
    }

    @FXML
        void handleLogin() {
        String username = getNormalizedText(usernameField);
        String password = getNormalizedText(passwordField);

        if (validateInputs(username, password)) {
            return;
        }
        clearMessage();

        if (listener != null) {
            listener.onLoginRequested(username, password);
        }
    }

    @FXML
        void handleRegister() {
        String username = getNormalizedText(usernameField);
        String password = getNormalizedText(passwordField);

        if (validateInputs(username, password)) {
            return;
        }
        clearMessage();

        if (listener != null) {
            listener.onRegisterRequested(username, password);
        }
    }

    @FXML
        void handleQuit() {
        if (listener != null) {
            listener.onQuitRequested();
        }
    }

    public void showError(String message) {
        messageLabel.setText(message);
        messageLabel.getStyleClass().setAll("message-error");
        messageLabel.setManaged(true);
        messageLabel.setVisible(true);
    }

    private void showSuccess(String message) {
        messageLabel.setText(message);
        messageLabel.getStyleClass().setAll("message-success");
        messageLabel.setManaged(true);
        messageLabel.setVisible(true);
    }

    public void showRegistrationSuccess(String username) {
        showSuccess("Account created successfully. Welcome " + (username == null ? "" : username) + "!");
    }

    private String getNormalizedText(TextField field) {
        String raw = field.getText();
        return raw == null ? "" : raw.trim();
    }

    private boolean validateInputs(String username, String password) {
        if (username.isBlank()) {
            showError("Please enter a username.");
            return true;
        }
        if (password.isBlank()) {
            showError("Please enter a password.");
            return true;
        }
        return false;
    }

    private void clearMessage() {
        messageLabel.setText("");
        messageLabel.setManaged(false);
        messageLabel.setVisible(false);
    }

}
