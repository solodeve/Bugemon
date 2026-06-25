package ulb.view.common;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

/**
 * Utility class that wires a gold focus border onto {@link Button} instances.
 *
 * <p>The border is stored separately from the button's base inline style so that
 * runtime style changes (e.g. skill buttons swapping their element-colour class
 * during battle) do not corrupt or lose the border.
 *
 * <p>Hovering over a button moves keyboard focus to it (hover-to-focus),
 * which in turn shows the border. The border is removed when the button loses focus.
 *
 * <p>Current call-sites: {@code ViewLoader}, {@code BattleButtonManager},
 * {@code MainMenuView}, {@code PartySelectionView}, {@code TeamEditionView}.
 */
public final class FocusHighlight {

    private static final String FOCUS_BORDER =
            "-fx-border-color: #FFD700; -fx-border-width: 6; -fx-border-radius: 8; -fx-border-insets: -4;";

    private static final String STYLE_KEY = "focus-highlight-base-style";

    private FocusHighlight() {}

    /**
     * Adds hover-to-focus and gold border behaviour to {@code button}.
     * Idempotent — safe to call multiple times on the same button.
     *
     * @param button the button to enhance; must not be {@code null}
     */
    public static void apply(Button button) {
        if (Boolean.TRUE.equals(button.getProperties().get("focus-managed"))) return;
        button.getProperties().put("focus-managed", true);

        EventHandler<? super MouseEvent> prevEntered = button.getOnMouseEntered();
        button.setOnMouseEntered(e -> {
            if (prevEntered != null) prevEntered.handle(e);
            button.requestFocus();
        });

        EventHandler<? super MouseEvent> prevExited = button.getOnMouseExited();
        button.setOnMouseExited(e -> {
            if (prevExited != null) prevExited.handle(e);
            // focusedProperty listener does not fire when focus does not change.
            // Re-apply the border explicitly when the mouse leaves while the button
            // still has focus (e.g. mouse moved off to a non-focusable area).
            if (button.isFocused()) addBorder(button);
        });

        button.focusedProperty().addListener((obs, wasF, isF) -> {
            if (isF) addBorder(button);
            else removeBorder(button);
        });
    }

    private static void addBorder(Button button) {
        // Capture the base style once; putIfAbsent guards against overwriting it on
        // repeated calls while the button remains focused.
        String current = button.getStyle();
        button.getProperties().putIfAbsent(STYLE_KEY, current != null ? current : "");
        String base = (String) button.getProperties().get(STYLE_KEY);
        button.setStyle(base.isBlank() ? FOCUS_BORDER : base + " " + FOCUS_BORDER);
    }

    private static void removeBorder(Button button) {
        // Remove the stored style so the next focus event captures a fresh baseline.
        String original = (String) button.getProperties().remove(STYLE_KEY);
        button.setStyle(original != null ? original : "");
    }
}
