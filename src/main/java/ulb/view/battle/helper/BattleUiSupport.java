package ulb.view.battle.helper;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.util.Duration;
import ulb.view.battle.state.BattleIntroUiState;
import ulb.view.battle.state.SpecialUiState;

/**
 * Helper for battle UI widgets such as the special meter and intro text.
 * Coordinates button visibility and animates the special progress bar.
 */
public final class BattleUiSupport {
    private final BattleButtonManager buttonManager;
    private final ProgressBar playerSpecialBar;
    private final Label playerSpecialLabel;
    private final Button specialButton;

    public BattleUiSupport(BattleButtonManager buttonManager, ProgressBar playerSpecialBar, Label playerSpecialLabel, Button specialButton) {
        this.buttonManager = buttonManager;
        this.playerSpecialBar = playerSpecialBar;
        this.playerSpecialLabel = playerSpecialLabel;
        this.specialButton = specialButton;
    }

    public void updateSpecialUi(SpecialUiState specialUiState, boolean mainMenuVisible, boolean controlsDisabled) {
        if (playerSpecialBar == null || playerSpecialLabel == null || specialButton == null) {
            return;
        }
        SpecialUiState state = specialUiState == null ? SpecialUiState.hidden() : specialUiState;
        BattleButtonManager.setNodeDisplayed(playerSpecialBar, state.visible());
        BattleButtonManager.setNodeDisplayed(playerSpecialLabel, state.visible());
        buttonManager.setSpecialVisible(state.buttonVisible() && mainMenuVisible);
        animateProgressBar(playerSpecialBar, state.progress());
        playerSpecialLabel.setText("Special : " + state.charge() + " / " + state.cost());
        specialButton.setText("Domain");
        specialButton.setDisable(controlsDisabled || !state.ready());
    }

    public String formatBattleIntro(BattleIntroUiState introState) {
        BattleIntroUiState safeState = introState == null ? BattleIntroUiState.defaultIntro() : introState;
        if (safeState.type() == BattleIntroUiState.Type.NO_TOWER) {
            if (safeState.bossRoom()) return "Boss " + safeState.roomDisplayName() + " is waiting for you.";
            String displayName = safeState.roomDisplayName();
            return displayName != null ? "A challenger appears in " + displayName + "." : "A new challenger appears!";
        }
        return "Welcome to the arena! Let the fight begin.";
    }

    private void animateProgressBar(ProgressBar bar, double targetProgress) {
        if (bar == null) return;
        if (bar.getProgress() < 0) {
            bar.setProgress(targetProgress);
            return;
        }
        new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(bar.progressProperty(), bar.getProgress())),
                new KeyFrame(Duration.millis(400), new KeyValue(bar.progressProperty(), targetProgress))
        ).play();
    }
}
