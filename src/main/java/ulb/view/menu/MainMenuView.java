package ulb.view.menu;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import ulb.controller.SoundController;

import java.util.List;
import ulb.view.common.FocusHighlight;

/**
 * Pure view for the main menu screen.
 *
 * <p>Displays buttons.
 * Each button is wired to an FXML {@code onAction} handler that calls the
 * corresponding method on the {@link MainMenuListener}.</p>
 *
 * <p><b>MVC role:</b> View. Contains no navigation or game logic.</p>
 */
public class MainMenuView {
    private MainMenuListener listener;

    @FXML private StackPane root;
    @FXML private ImageView bgImage;
    @FXML private VBox panelMain;
    @FXML private VBox panelGame;

    @FXML private Button btnGame;
    @FXML private Button btnFreeBattle;
    @FXML private Button btnNoTower;
    @FXML private Button btnLeaderboard;
    @FXML private Button btnRewards;
    @FXML private Button btnSettings;
    @FXML private Button btnLeave;
    @FXML private Button btnBack;

    private javafx.event.EventHandler<KeyEvent> keyNavHandler;

    @FXML
    public void initialize() {
        SoundController.getInstance().playMenuMusic();
        bgImage.fitWidthProperty().bind(root.widthProperty());
        bgImage.fitHeightProperty().bind(root.heightProperty());
        applyFocusHighlights();
        registerKeyNavigation();
    }

    private void applyFocusHighlights() {
        FocusHighlight.apply(btnGame);
        FocusHighlight.apply(btnFreeBattle);
        FocusHighlight.apply(btnLeaderboard);
        FocusHighlight.apply(btnNoTower);
        FocusHighlight.apply(btnRewards);
        FocusHighlight.apply(btnSettings);
        FocusHighlight.apply(btnLeave);
        FocusHighlight.apply(btnBack);
    }

    private void registerKeyNavigation() {
        keyNavHandler = this::handleKeyNav;
        btnGame.sceneProperty().addListener((obs, old, scene) -> {
            if (old != null) old.removeEventFilter(KeyEvent.KEY_PRESSED, keyNavHandler);
            if (scene != null) {
                scene.addEventFilter(KeyEvent.KEY_PRESSED, keyNavHandler);
                Platform.runLater(() -> btnGame.requestFocus());
            }
        });
    }

    private void handleKeyNav(KeyEvent event) {
        List<Button> visible = panelMain.isVisible()
                ? List.of(btnGame, btnSettings, btnLeave)
                : List.of(btnFreeBattle, btnNoTower,btnLeaderboard, btnRewards, btnBack);
        switch (event.getCode()) {
            case UP -> { moveFocus(visible, -1); event.consume(); }
            case DOWN -> { moveFocus(visible, 1); event.consume(); }
            case ESCAPE -> { if (panelGame.isVisible()) { handleBack(); event.consume(); } }
            default -> {}
        }
    }

    private void moveFocus(List<Button> buttons, int delta) {
        for (int i = 0; i < buttons.size(); i++) {
            if (buttons.get(i).isFocused()) {
                buttons.get((i + delta + buttons.size()) % buttons.size()).requestFocus();
                return;
            }
        }
        buttons.getFirst().requestFocus();
    }

    public interface MainMenuListener {
        void onFreeBattleClicked();
        void onNoTowerClicked();
        void onLeaderboardClicked();
        void onRewardsClicked();
        void onSettingsClicked();
        void onQuitClicked();
    }

    public void setListener(MainMenuListener listener) {
        this.listener = listener;
    }

    @FXML
        void handleGame() {
        SoundController.getInstance().playButtonClick();
        panelMain.setVisible(false);
        panelMain.setManaged(false);
        panelGame.setVisible(true);
        panelGame.setManaged(true);
        Platform.runLater(() -> btnFreeBattle.requestFocus());
    }

    @FXML
        void handleBack() {
        SoundController.getInstance().playButtonClick();
        panelGame.setVisible(false);
        panelGame.setManaged(false);
        panelMain.setVisible(true);
        panelMain.setManaged(true);
        Platform.runLater(() -> btnGame.requestFocus());
    }

    @FXML
        void handleFreeBattle() {
        SoundController.getInstance().playButtonClick();
        if (listener != null) {
            listener.onFreeBattleClicked();
        }
    }

    @FXML
        void handleNoTower() {
        SoundController.getInstance().playButtonClick();
        if (listener != null) {
            listener.onNoTowerClicked();
        }
    }

    @FXML
        void handleLeaderBoard() {
        SoundController.getInstance().playButtonClick();
        if (listener != null) {
            listener.onLeaderboardClicked();
        }
    }

    @FXML
        void handleRewards() {
        SoundController.getInstance().playButtonClick();
        if (listener != null) {
            listener.onRewardsClicked();
        }
    }

    @FXML
        void handleSettings(){
        SoundController.getInstance().playButtonClick();
        if (listener != null){
            listener.onSettingsClicked();
        }
    }

    @FXML
        void handleQuit() {
        SoundController.getInstance().playButtonClick();
        SoundController.getInstance().stopMenuMusic();
        if (listener != null) {
            listener.onQuitClicked();
        }
    }
}
