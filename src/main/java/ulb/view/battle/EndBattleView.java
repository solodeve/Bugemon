package ulb.view.battle;

import java.util.List;
import java.util.Set;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import ulb.controller.SoundController;
import ulb.model.domain.Bugemon;

/** Pure view for displaying the outcome of a battle. */
public class EndBattleView {

    private Listener listener;

    public interface Listener {
        void onBackToMainMenuClicked();
    }

    @FXML private Label lblResult;
    @FXML private VBox vboxBugemons;
    @FXML private VBox unlockedSection;
    @FXML private VBox vboxUnlockedBugemons;
    @FXML private Button btnBack;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @FXML
        void initialize() {
        btnBack.sceneProperty().addListener((obs, old, scene) -> {
            if (scene != null) {
                scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
                    if (event.getCode() == KeyCode.ESCAPE) {
                        handleBack();
                        event.consume();
                    }
                });
                Platform.runLater(btnBack::requestFocus);
            }
        });
    }

    public void displayBattleResult(boolean hasPlayerWon) {
        if (hasPlayerWon) {
            lblResult.setText("Victory !");
            lblResult.getStyleClass().setAll("result-victory");
            SoundController.getInstance().playVictory();
        } else {
            lblResult.setText("Defeat !");
            lblResult.getStyleClass().setAll("result-defeat");
            SoundController.getInstance().playDefeat();
        }
    }

    /**
     * Populates the surviving Bugemon list. {@code showLevelAndXp} is {@code false} for
     * free battles where XP is not tracked; passing {@code null} or an empty set shows
     * a placeholder instead.
     */
    public void displayWinningBugemons(Set<Bugemon> bugemons, boolean showLevelAndXp) {
        if (vboxBugemons == null) {
            return;
        }

        ObservableList<Node> children = vboxBugemons.getChildren();
        children.clear();

        if (bugemons == null || bugemons.isEmpty()) {
            Label noData = new Label("No bugemon alive");
            noData.getStyleClass().add("winning-bugemon-empty");
            children.add(noData);
            return;
        }

        for (Bugemon bugemon : bugemons) {
            String labelText = showLevelAndXp
                    ? bugemon.getName() + " | Level: " + bugemon.getLevel() + " | XP: " + bugemon.getXP()
                    : bugemon.getName();
            Label bugemonLabel = new Label(labelText);
            bugemonLabel.getStyleClass().add("winning-bugemon-label");
            children.add(bugemonLabel);
        }
    }

    public void displayUnlockedBugemons(List<Bugemon> bugemons) {
        if (unlockedSection == null || vboxUnlockedBugemons == null) {
            return;
        }

        ObservableList<Node> children = vboxUnlockedBugemons.getChildren();
        children.clear();

        boolean hasUnlocks = bugemons != null && !bugemons.isEmpty();
        unlockedSection.setVisible(hasUnlocks);
        unlockedSection.setManaged(hasUnlocks);
        if (!hasUnlocks) {
            return;
        }

        for (Bugemon bugemon : bugemons) {
            Label bugemonLabel = new Label(bugemon.getName());
            bugemonLabel.getStyleClass().add("unlocked-bugemon-label");
            children.add(bugemonLabel);
        }
    }

    @FXML
        void handleBack() {
        SoundController.getInstance().playButtonClick();
        if (listener != null) {
            listener.onBackToMainMenuClicked();
        }
    }
}
