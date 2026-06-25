package ulb.view.battle.towerMode;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import ulb.controller.SoundController;

import java.util.List;

/**
 * View for the skill-rewards screen, displaying skills unlocked at each
 * progression level as a 3-column card grid.
 *
 * <p>The Escape key is registered as a scene-level filter and properly removed
 * when the view leaves the scene.
 *
 * <p><b>MVC role:</b> View only. Forwards navigation events to {@link Listener}.</p>
 */
public class RewardsView {
    private static final int COLUMN_COUNT = 3;
    
    private final SoundController soundController = SoundController.getInstance();
    private Listener listener;

    public interface Listener {
        void onBackClicked();
    }

    @FXML private GridPane levelsContainer;
    

    private EventHandler<KeyEvent> escapeHandler;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @FXML
        void initialize() {
        escapeHandler = this::handleEscapeKey;

        levelsContainer.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (oldScene != null) oldScene.removeEventFilter(KeyEvent.KEY_PRESSED, escapeHandler);
            if (newScene != null) newScene.addEventFilter(KeyEvent.KEY_PRESSED, escapeHandler);
        });
    }

    private void handleEscapeKey(KeyEvent event) {
        KeyCode code = event.getCode();

        if (code == KeyCode.ESCAPE) {
            handleBack();
            event.consume();
        }
    }

    /**
     * Replaces the current grid with one card per entry in {@code rewards},
     * laid out in a 3-column grid (left-to-right, top-to-bottom).
     */
    public void displayRewards(List<LevelRewardDisplay> rewards) {
        configureGrid();
        int column = 0;
        int row = 0;
        for (LevelRewardDisplay reward : rewards) {
            addRewardCard(reward, column, row);
            column++;
            if (column == COLUMN_COUNT) {
                column = 0;
                row++;
            }
        }
    }

    private void configureGrid() {
        levelsContainer.getChildren().clear();
        levelsContainer.setHgap(22);
        levelsContainer.setVgap(22);
        levelsContainer.setPadding(new Insets(10));
    }

    public void displayError(String message) {
        VBox errorCard = createErrorCard(message);
        levelsContainer.getChildren().add(errorCard);
    }

    @FXML
        void handleBack() {
        soundController.playButtonClick();
        if (listener != null) {
            listener.onBackClicked();
        }
    }

    private void addRewardCard(LevelRewardDisplay reward, int column, int row) {
        VBox rewardCard = createRewardCard(reward);
        levelsContainer.add(rewardCard, column, row);
    }

    private VBox createRewardCard(LevelRewardDisplay reward) {
        VBox card = new VBox(12);
        card.getChildren().addAll(createRewardContent(reward));
        configureRewardCard(card);
        return card;
    }

    private List<javafx.scene.Node> createRewardContent(LevelRewardDisplay reward) {
        Region spacer = createSpacer();

        return List.of(
                createStyledLabel("Niveau " + reward.level(), "reward-level-label", false),
                createStyledLabel(reward.name(), "reward-name-label", true),
                createStyledLabel(reward.type(), "reward-type-badge", false),
                createStyledLabel(formatStats(reward), "reward-stats-label", false),
                createStyledLabel(reward.description(), "reward-desc-label", true),
                spacer,
                createStyledLabel("Effet", "reward-effect-title", false),
                createStyledLabel(reward.effectText(), "reward-effect-label", false)
        );
    }

    private Region createSpacer() {
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        return spacer;
    }

    private void configureRewardCard(VBox card) {
        card.setPadding(new Insets(20));
        card.setPrefWidth(280);
        card.setMinHeight(320);
        card.setAlignment(Pos.TOP_LEFT);
        card.getStyleClass().add("reward-card");
    }
    

    private String formatStats(LevelRewardDisplay reward) {
        int accuracyPercent = (int) (reward.accuracy() * 100);
        String magicText = reward.isMagic() ? "Oui" : "Non";

        return "Puissance : " + reward.power() + "\n"
                + "Précision : " + accuracyPercent + "%\n"
                + "Priorité : " + reward.priority() + "\n"
                + "Magique : " + magicText;
    }

    private Label createStyledLabel(String text, String styleClass, boolean wrapText) {
        Label label = new Label(text);
        label.setWrapText(wrapText);
        label.getStyleClass().add(styleClass);
        return label;
    }

    private VBox createErrorCard(String message) {
        Label label = createStyledLabel(message, "reward-error-label", false);
        VBox box = new VBox(label);
        configureErrorCard(box);

        return box;
    }

    private void configureErrorCard(VBox box) {
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(30));
        box.getStyleClass().add("reward-error-card");
    }
}

