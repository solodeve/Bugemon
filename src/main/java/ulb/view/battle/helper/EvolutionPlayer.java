package ulb.view.battle.helper;

import java.net.URL;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import ulb.configuration.Configuration;
import ulb.controller.SoundController;

/**
 * Plays a Bugemon evolution video in two phases: fade to black (keeping the player sprite
 * visible), then play the video in the sprite area. Calls {@code onFinished} when done
 * or if the video resource cannot be loaded.
 */
public class EvolutionPlayer {

    private static final Duration FADE_DURATION = Duration.seconds(1.0);
    private static final double VIDEO_OPACITY   = 1.0;
    private static final double VIDEO_SCALE     = 1.3;

    private final AnchorPane rootPane;
    private final ImageView playerSprite;
    private MediaPlayer currentPlayer;

    public EvolutionPlayer(AnchorPane rootPane, ImageView playerSprite) {
        this.rootPane = rootPane;
        this.playerSprite = playerSprite;
    }

    /**
     * Returns {@code true} when evolution should be skipped — i.e. no video resource
     * exists for this ID. Despite the name, a {@code true} return means "do not play."
     */
    public boolean hasNoEvolutionVideo(String bugemonId) {
        if (bugemonId == null || bugemonId.isBlank()) return true;
        return getClass().getResource(buildResourcePath(bugemonId)) == null;
    }

    public void play(String bugemonId, Runnable onFinished) {
        URL resourceUrl = getClass().getResource(buildResourcePath(bugemonId));
        if (resourceUrl == null) {
            if (onFinished != null) onFinished.run();
            return;
        }

        SoundController.getInstance().playEvolution();

        Rectangle blackOverlay = new Rectangle();
        blackOverlay.setFill(Color.BLACK);
        blackOverlay.setOpacity(0.0);

        var rootChildren = rootPane.getChildren();
        blackOverlay.widthProperty().bind(rootPane.widthProperty());
        blackOverlay.heightProperty().bind(rootPane.heightProperty());

        int spriteIndex = rootChildren.indexOf(playerSprite);
        if (spriteIndex >= 0) {
            rootChildren.add(spriteIndex, blackOverlay);
        } else {
            rootChildren.add(blackOverlay);
        }

        FadeTransition fadeIn = new FadeTransition(FADE_DURATION, blackOverlay);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.setOnFinished(evt -> playVideoInSpriteArea(resourceUrl, blackOverlay, onFinished));
        fadeIn.play();
    }

    private void playVideoInSpriteArea(URL resourceUrl, Rectangle blackOverlay, Runnable onFinished) {
        double top  = AnchorPane.getTopAnchor(playerSprite) - 30;
        double left = AnchorPane.getLeftAnchor(playerSprite) - 20;
        if (Double.isNaN(top))  top  = 0.0;
        if (Double.isNaN(left)) left = 0.0;

        double scaledWidth  = playerSprite.getFitWidth()  * VIDEO_SCALE;
        double scaledHeight = playerSprite.getFitHeight() * VIDEO_SCALE;
        double centeredTop  = top  - (scaledHeight - playerSprite.getFitHeight()) / 2.0;
        double centeredLeft = left - (scaledWidth  - playerSprite.getFitWidth())  / 2.0;

        Media media = new Media(resourceUrl.toExternalForm());
        currentPlayer = new MediaPlayer(media);
        currentPlayer.setMute(true);

        MediaView mediaView = new MediaView(currentPlayer);
        mediaView.setPreserveRatio(true);
        mediaView.setFitWidth(scaledWidth);
        mediaView.setFitHeight(scaledHeight);
        mediaView.setOpacity(VIDEO_OPACITY);
        AnchorPane.setTopAnchor(mediaView, centeredTop);
        AnchorPane.setLeftAnchor(mediaView, centeredLeft);

        var rootChildren      = rootPane.getChildren();
        var overlayWidthProp  = blackOverlay.widthProperty();
        var overlayHeightProp = blackOverlay.heightProperty();

        rootChildren.add(mediaView);
        playerSprite.setVisible(false);

        Runnable cleanup = () -> Platform.runLater(() -> {
            overlayWidthProp.unbind();
            overlayHeightProp.unbind();
            rootChildren.removeAll(blackOverlay, mediaView);
            if (currentPlayer != null) {
                currentPlayer.dispose();
                currentPlayer = null;
            }
            if (onFinished != null) onFinished.run();
        });

        currentPlayer.setOnEndOfMedia(cleanup);
        currentPlayer.setOnError(cleanup);
        currentPlayer.play();
    }

    private String buildResourcePath(String bugemonId) {
        return Configuration.bugemonEvolutionVideoResourcePathForId(bugemonId);
    }
}
