package ulb.view.battle.helper;

import java.util.Objects;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import ulb.configuration.Configuration;

/**
 * Applies battle background images and handles domain transition visuals.
 * Temporarily hides or focuses UI elements during animated swaps.
 */
public final class BattleBackgroundManager {
    private final AnchorPane battleRootPane;
    private final VBox playerInfoPanel;
    private final VBox enemyInfoPanel;
    private final StackPane dialoguePanel;
    private final StackPane actionPanel;
    private final ImageView playerSprite;
    private final ImageView enemySprite;
    private final Button specialButton;
    private final BackgroundAnimator backgroundAnimator;
    private String currentBackgroundPath;

    public BattleBackgroundManager(
            AnchorPane battleRootPane,
            VBox playerInfoPanel,
            VBox enemyInfoPanel,
            StackPane dialoguePanel,
            StackPane actionPanel,
            ImageView playerSprite,
            ImageView enemySprite,
            Button specialButton,
            BackgroundAnimator backgroundAnimator
    ) {
        this.battleRootPane = battleRootPane;
        this.playerInfoPanel = playerInfoPanel;
        this.enemyInfoPanel = enemyInfoPanel;
        this.dialoguePanel = dialoguePanel;
        this.actionPanel = actionPanel;
        this.playerSprite = playerSprite;
        this.enemySprite = enemySprite;
        this.specialButton = specialButton;
        this.backgroundAnimator = backgroundAnimator;
    }

    public void syncBackground(String backgroundPath) {
        if (battleRootPane == null) {
            return;
        }

        String resolvedPath = resolveBackgroundPath(backgroundPath);
        if (Objects.equals(currentBackgroundPath, resolvedPath)) {
            return;
        }

        applyBackgroundStyle(resolvedPath);
        currentBackgroundPath = resolvedPath;
    }

    public void playDomainBackgroundTransition(String backgroundPath, boolean playerCaster) {
        String resolvedPath = resolveBackgroundPath(backgroundPath);
        if (Objects.equals(currentBackgroundPath, resolvedPath)) {
            return;
        }

        playBackgroundTransition(resolvedPath, playerCaster);
        currentBackgroundPath = resolvedPath;
    }

    private String resolveBackgroundPath(String backgroundPath) {
        return (backgroundPath == null || backgroundPath.isBlank())
                ? Configuration.DEFAULT_BATTLE_BACKGROUND
                : backgroundPath;
    }

    private void playBackgroundTransition(String resolvedPath, boolean playerCaster) {
        if (!backgroundAnimator.isInitialized()) {
            applyBackgroundStyle(resolvedPath);
            return;
        }

        setDomainFocusMode(true, playerCaster);
        backgroundAnimator.playTransition(
                () -> applyBackgroundStyle(resolvedPath),
                () -> setDomainFocusMode(false, playerCaster)
        );
    }

    private void applyBackgroundStyle(String resolvedPath) {
        if (battleRootPane == null) {
            return;
        }

        battleRootPane.setStyle("-fx-background-image: url('" + resolvedPath + "');"
                + "-fx-background-size: 100% 100%;"
        );
    }

    private void setDomainFocusMode(boolean focused, boolean playerCaster) {
        double hiddenOpacity = 0.0;
        double visibleOpacity = 1.0;

        if (playerInfoPanel != null) {
            playerInfoPanel.setOpacity(focused ? hiddenOpacity : visibleOpacity);
        }
        if (enemyInfoPanel != null) {
            enemyInfoPanel.setOpacity(focused ? hiddenOpacity : visibleOpacity);
        }
        if (enemySprite != null) {
            enemySprite.setOpacity(focused ? (playerCaster ? hiddenOpacity : visibleOpacity) : visibleOpacity);
        }
        if (playerSprite != null) {
            playerSprite.setOpacity(focused ? (playerCaster ? visibleOpacity : hiddenOpacity) : visibleOpacity);
        }
        if (actionPanel != null) {
            actionPanel.setOpacity(focused ? hiddenOpacity : visibleOpacity);
        }
        if (dialoguePanel != null) {
            dialoguePanel.setOpacity(focused ? hiddenOpacity : visibleOpacity);
        }
        if (specialButton != null) {
            specialButton.setOpacity(focused ? hiddenOpacity : visibleOpacity);
        }

        if (focused) {
            backgroundAnimator.bringDarkToFront();
            if (playerCaster) {
                if (playerInfoPanel != null) {
                    playerInfoPanel.toFront();
                }
                if (playerSprite != null) {
                    playerSprite.toFront();
                }
            } else {
                if (enemyInfoPanel != null) {
                    enemyInfoPanel.toFront();
                }
                if (enemySprite != null) {
                    enemySprite.toFront();
                }
            }
            backgroundAnimator.bringFlashToFront();
        } else {
            if (specialButton != null) {
                specialButton.toFront();
            }
            backgroundAnimator.resetFlash();
        }
    }
}
