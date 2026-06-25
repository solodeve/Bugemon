package ulb.view.battle.helper;

import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * Drives the dark-overlay + white-flash sequence used for domain-move background swaps.
 * The dark overlay fades in first; the background is swapped while the screen is black;
 * a white flash fires; then the dark overlay fades out. Call {@link #initialize} once
 * before use and check {@link #isInitialized} before calling {@link #playTransition}.
 */
public class BackgroundAnimator {

    private static final Duration DARK_FADE_DURATION       = Duration.seconds(3);
    private static final Duration FLASH_IN_DURATION        = Duration.millis(140);
    private static final Duration FLASH_OUT_DURATION       = Duration.millis(220);
    private static final Duration BACKGROUND_FADE_DURATION = Duration.seconds(1.5);

    private Rectangle darkOverlay;
    private Rectangle flashOverlay;

    /** Idempotent — safe to call multiple times. */
    public void initialize(AnchorPane overlay) {
        if (overlay == null || darkOverlay != null) return;

        darkOverlay  = createOverlay(overlay, Color.BLACK);
        flashOverlay = createOverlay(overlay, Color.WHITE);
        overlay.getChildren().addAll(darkOverlay, flashOverlay);
    }

    public boolean isInitialized() {
        return darkOverlay != null && flashOverlay != null;
    }

    /**
     * Plays the full transition sequence.
     *
     * @param onDarkened fires when the screen is fully black — swap the background here
     * @param onComplete fires after the overlay has faded out — re-enable UI elements here
     */
    public void playTransition(Runnable onDarkened, Runnable onComplete) {
        FadeTransition darkIn = new FadeTransition(DARK_FADE_DURATION, darkOverlay);
        darkIn.setFromValue(darkOverlay.getOpacity());
        darkIn.setToValue(0.96);

        FadeTransition darkOut = new FadeTransition(BACKGROUND_FADE_DURATION, darkOverlay);
        darkOut.setFromValue(0.96);
        darkOut.setToValue(0.0);

        FadeTransition flashIn  = new FadeTransition(FLASH_IN_DURATION,  flashOverlay);
        flashIn.setFromValue(0.0);
        flashIn.setToValue(0.85);

        FadeTransition flashOut = new FadeTransition(FLASH_OUT_DURATION, flashOverlay);
        flashOut.setFromValue(0.85);
        flashOut.setToValue(0.0);

        if (onDarkened != null) darkIn.setOnFinished(e -> onDarkened.run());
        if (onComplete  != null) darkOut.setOnFinished(e -> onComplete.run());

        new SequentialTransition(darkIn, flashIn, flashOut, darkOut).play();
    }

    public void bringDarkToFront() {
        if (darkOverlay != null) darkOverlay.toFront();
    }

    public void bringFlashToFront() {
        if (flashOverlay != null) flashOverlay.toFront();
    }

    public void resetFlash() {
        if (flashOverlay != null) flashOverlay.setOpacity(0.0);
    }

    private Rectangle createOverlay(AnchorPane parent, Color color) {
        Rectangle rect = new Rectangle();
        rect.setManaged(false);
        rect.setMouseTransparent(true);
        rect.setOpacity(0.0);
        rect.setFill(color);
        rect.widthProperty().bind(parent.widthProperty());
        rect.heightProperty().bind(parent.heightProperty());
        return rect;
    }
}
