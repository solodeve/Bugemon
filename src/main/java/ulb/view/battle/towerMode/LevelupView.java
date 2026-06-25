package ulb.view.battle.towerMode;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import ulb.controller.SoundController;
import ulb.model.domain.status.Bonus;

import java.util.List;
import ulb.view.common.SpriteLoader;

/**
 * JavaFX view for the level-up reward screen.
 *
 * <p>It displays three randomly generated bonus options, previews the stat
 * evolution for the selected one, and delegates user actions to its listener.</p>
 */
public class LevelupView {

    /**
     * Listener implemented by the controller driving the level-up workflow.
     */
    public interface Listener {
        void onBonusConfirmed(int bonusIndex);
        void onBonusSelected(int bonusIndex);
    }

    @FXML private BorderPane rootPane;
    @FXML private Label levelupTitle;
    @FXML private Button bonus1Button;
    @FXML private Button bonus2Button;
    @FXML private Button bonus3Button;
    @FXML private ImageView bugemonSprite;
    @FXML private VBox statsContainer;
    @FXML private Button confirmButton;

    private final SpriteLoader spriteLoader = new SpriteLoader();

    private Listener listener;
    private List<BonusDisplayData> bonusOptions;
    private int bonusIndex = -1;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    /**
     * Displays the Bugemon that levelled up and the three pre-generated bonus options.
     */
    public void displayLevelup(LevelupDisplayData displayData) {
        levelupTitle.setText(displayData.title());

        spriteLoader.loadSpriteForBugemon(displayData.spritePath(), displayData.element(), false, false)
                .ifPresent(bugemonSprite::setImage);
        this.bonusOptions = displayData.bonuses();

        bonus1Button.setText(bonusOptions.get(0).text());
        bonus2Button.setText(bonusOptions.get(1).text());
        bonus3Button.setText(bonusOptions.get(2).text());
    }

    /**
     * Shows the before/after stat values produced by the given bonus.
     *
     * <p>{@code oldStats} must be a four-element array in the order
     * {@code [HP, Attack, Defense, Initiative]}.
     *
     * @param oldStats   current Bugemon stats before the bonus is applied
     * @param bonusIndex 0-based index into the bonus options list set by
     *                   {@link #displayLevelup}
     */
    public void displayStatsEvolution(Bonus oldStats, int bonusIndex) {
        statsContainer.getChildren().clear();

        BonusDisplayData bonus = bonusOptions.get(bonusIndex);

        int newHp = oldStats.hp() + bonus.hp();
        int newAttack = oldStats.attack() + bonus.attack();
        int newDefense = oldStats.defense() + bonus.defense();
        int newInitiative = oldStats.initiative() + bonus.initiative();

        Label hpLabel = new Label("HP : " + oldStats.hp() + " -> " + newHp);
        Label attackLabel = new Label("Attack : " + oldStats.attack() + " -> " + newAttack);
        Label defenseLabel = new Label("Defense : " + oldStats.defense() + " -> " + newDefense);
        Label initiativeLabel = new Label("Initiative : " + oldStats.initiative() + " -> " + newInitiative);

        hpLabel.getStyleClass().add("levelup-stat-label");
        attackLabel.getStyleClass().add("levelup-stat-label");
        defenseLabel.getStyleClass().add("levelup-stat-label");
        initiativeLabel.getStyleClass().add("levelup-stat-label");

        statsContainer.getChildren().addAll(hpLabel, attackLabel, defenseLabel, initiativeLabel);
    }

    /**
     * Makes the confirm button visible after a bonus choice has been previewed.
     */
    public void displayConfirmButton(){
        confirmButton.setVisible(true);
        confirmButton.setManaged(true);
    }

    @FXML void onBonus1Selected() { handleBonusSelected(0); }
    @FXML void onBonus2Selected() { handleBonusSelected(1); }
    @FXML void onBonus3Selected() { handleBonusSelected(2); }

    private void handleBonusSelected(int index) {
        SoundController.getInstance().playButtonClick();
        if (listener != null) {
            listener.onBonusSelected(index);
            bonusIndex = index;
        }
    }

    @FXML
        void onConfirmSelected() {
        SoundController.getInstance().playButtonClick();
        if (listener != null && bonusIndex >= 0) {
            listener.onBonusConfirmed(bonusIndex);
        }
    }
}
