package ulb.view.battle.towerMode;

import java.util.List;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import ulb.controller.SoundController;
import ulb.model.domain.Bugemon;
import ulb.model.no.Room;
import ulb.service.no.NOBattleOutcome;
import ulb.service.no.NOProgressionResult;

/**
 * JavaFX view for the NO tower hub.
 *
 * <p>Its role is limited to presentation: show progression, render available rooms,
 * and forward player intents (start/quit) to the coordinating layer.</p>
 */
public class NOView {
    private final NORoomCardFactory roomCardFactory = new NORoomCardFactory();

    public interface Listener {
        void onStartBattleClicked();
        void onQuitTowerClicked();
    }

    public enum Status {
        NO_ACTIVE_TOWER,
        NO_AVAILABLE_ROOM
    }

    private Listener listener;
    private EventHandler<KeyEvent> keyHandler;

    @FXML private StackPane rootPane;
    @FXML private Label titleLabel;
    @FXML private Label floorLabel;
    @FXML private Label progressLabel;
    @FXML private Label statusLabel;
    @FXML private VBox unlockedSection;
    @FXML private VBox unlockedBugemonsBox;
    @FXML private FlowPane roomsContainer;
    @FXML private Button startBattleButton;
    @FXML private Button quitTowerButton;

    @FXML
    public void initialize() {
        keyHandler = this::handleKeyPressed;
        startBattleButton.sceneProperty().addListener((obs, old, scene) -> {
            if (old != null) old.removeEventFilter(KeyEvent.KEY_PRESSED, keyHandler);
            if (scene != null) {
                scene.addEventFilter(KeyEvent.KEY_PRESSED, keyHandler);
                Platform.runLater(() -> startBattleButton.requestFocus());
            }
        });
    }

    private void toggleFocus() {
        if (startBattleButton.isFocused()) quitTowerButton.requestFocus();
        else startBattleButton.requestFocus();
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    /**
     * Synchronizes the tower screen with the current run state and available rooms.
     */
    public void displayTowerState(int currentFloor, int totalFloors, int currentRoom, int totalRooms, int enemyRoomCount, List<Room> rooms, Room room) {
        floorLabel.setText("Floor " + currentFloor + " / " + totalFloors);
        progressLabel.setText("Battle " + currentRoom + " / " + totalRooms);
        renderRooms(rooms, room);

        if (room == null) {
            startBattleButton.setDisable(true);
            titleLabel.setText("NO Tower");
            return;
        }

        startBattleButton.setDisable(false);

        titleLabel.setText(
                room.isBossRoom()
                        ? "NO Tower: boss of floor " + currentFloor
                        : "NO Tower: room " + Math.min(currentRoom, Math.max(1, enemyRoomCount)) + " of floor " + currentFloor
        );
    }

    public void displayStatus(String message) {
        statusLabel.setText(message == null ? "" : message);
    }

    public void displayStatus(Status status) {
        displayStatus(switch (status) {
            case NO_ACTIVE_TOWER -> "No NO tower is active.";
            case NO_AVAILABLE_ROOM -> "No room is available.";
        });
    }

    /**
     * Translates progression outcomes into player-facing feedback messages.
     */
    public void displayLastBattleResult(NOProgressionResult result) {
        NOBattleOutcome outcome = result.outcome();
        displayStatus(switch (outcome) {
            case RUN_STARTED    -> "The ascent begins. Clear three battles, then defeat the floor boss.";
            case RUN_LOST       -> "You lost the run in floor " + result.floorNumber() + ".";
            case NO_ROOMS_LEFT  -> "The tower has no more rooms.";
            case AFO_BLOCKED    -> "AFO cannot be defeated. Your journey ends here.";
            case TOWER_CONQUERED -> "You conquered the NO Tower.";
            case FLOOR_CLEARED  -> "Floor " + result.floorNumber() + " cleared. Its bugemons are now playable.";
            case ROOM_CLEARED   -> "Victory. Defeated bugemons are now playable and the next room is unlocked.";
        });
    }

    /**
     * Highlights the Bugemons unlocked by the latest tower progression step.
     */
    public void displayUnlockedBugemons(List<Bugemon> bugemons) {
        if (unlockedSection == null || unlockedBugemonsBox == null) return;

        unlockedBugemonsBox.getChildren().clear();
        boolean hasUnlocks = bugemons != null && !bugemons.isEmpty();
        unlockedSection.setVisible(hasUnlocks);
        unlockedSection.setManaged(hasUnlocks);
        if (!hasUnlocks) return;

        for (Bugemon bugemon : bugemons) {
            Label label = new Label(bugemon.getName());
            label.getStyleClass().add("no-unlocked-label");
            unlockedBugemonsBox.getChildren().add(label);
        }
    }

    @FXML
    private void handleStartBattle() {
        SoundController.getInstance().playButtonClick();
        if (listener != null) listener.onStartBattleClicked();
    }

    @FXML
    private void handleQuitTower() {
        SoundController.getInstance().playButtonClick();
        if (listener != null) listener.onQuitTowerClicked();
    }

    private void renderRooms(List<Room> rooms, Room currentRoom) {
        if (roomsContainer == null) return;

        roomsContainer.getChildren().clear();
        if (rooms == null || rooms.isEmpty()) return;

        for (Room room : rooms) {
            roomsContainer.getChildren().add(roomCardFactory.createRoomCard(room, room == currentRoom));
        }
    }

    private void handleKeyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case ENTER -> handleEnterPressed(event);
            case ESCAPE -> triggerQuit(event);
            case LEFT, RIGHT -> toggleSelection(event);
            default -> {}
        }
    }

    private void handleEnterPressed(KeyEvent event) {
        if (!startBattleButton.isDisabled()) handleStartBattle();
        event.consume();
    }

    private void triggerQuit(KeyEvent event) {
        handleQuitTower();
        event.consume();
    }

    private void toggleSelection(KeyEvent event) {
        toggleFocus();
        event.consume();
    }

}
