package ulb.view.menu.teamSelection;

import java.util.function.Supplier;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import ulb.controller.SoundController;

/**
 * Manages inline create and rename editing states for {@link TeamSelectionView}.
 *
 * <p>When the user clicks the "+" add-row button, this handler replaces it with an
 * inline text field. When the user triggers a rename, it makes the name field editable.
 * All notifications to the application layer go through the listener supplier so that
 * this helper remains decoupled from the FXML controller lifecycle.</p>
 */
class TeamInlineEditHandler {

    private TextField pendingCreateField;
    private HBox pendingCreateRow;

    private final VBox teamsContainer;
    private final Supplier<TeamSelectionView.Listener> listenerProvider;
    private final Supplier<Button> makeAddRowButton;

    TeamInlineEditHandler(
            VBox teamsContainer,
            Supplier<TeamSelectionView.Listener> listenerProvider,
            Supplier<Button> makeAddRowButton) {
        this.teamsContainer = teamsContainer;
        this.listenerProvider = listenerProvider;
        this.makeAddRowButton = makeAddRowButton;
    }

    /**
     * Resets pending-create state without touching the container.
     * Called when the container is about to be rebuilt entirely via {@code displayTeams}.
     */
    void reset() {
        pendingCreateField = null;
        pendingCreateRow = null;
    }

    /**
     * Replaces the "+" add button with an inline text field + Cancel button.
     * Idempotent: a second call while a pending row is already showing is a no-op.
     */
    void showPendingCreateRow() {
        if (pendingCreateRow != null) {
            return;
        }

        int insertIndex = teamsContainer.getChildren().size() - 1;
        if (insertIndex < 0) {
            insertIndex = teamsContainer.getChildren().size();
        }

        TextField createField = new TextField();
        createField.setPromptText("Team name");
        createField.getStyleClass().add("team-name-field");
        HBox.setHgrow(createField, Priority.ALWAYS);

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> {
            SoundController.getInstance().playButtonClick();
            cancelPendingCreateRow();
        });

        HBox createRow = new HBox(16, createField, cancelButton);
        createRow.getStyleClass().add("team-row");

        createField.setOnAction(e -> submitCreateTeam());
        createField.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) cancelPendingCreateRow();
        });

        pendingCreateField = createField;
        pendingCreateRow = createRow;
        teamsContainer.getChildren().set(insertIndex, createRow);
        Platform.runLater(createField::requestFocus);
    }

    void cancelPendingCreateRow() {
        if (pendingCreateRow == null) {
            return;
        }

        int rowIndex = teamsContainer.getChildren().indexOf(pendingCreateRow);
        if (rowIndex >= 0) {
            teamsContainer.getChildren().set(rowIndex, makeAddRowButton.get());
        }

        pendingCreateField = null;
        pendingCreateRow = null;
    }

    void submitCreateTeam() {
        TeamSelectionView.Listener l = listenerProvider.get();
        if (l == null || pendingCreateField == null) return;
        l.onCreateTeamClicked(pendingCreateField.getText());
    }

    /**
     * Makes {@code field} editable and wires submit on Enter/focus-loss.
     * Escape restores {@code originalName} and exits rename mode without notifying.
     */
    void enableRenameMode(TextField field, int index, String originalName) {
        field.setEditable(true);
        field.setFocusTraversable(true);
        Platform.runLater(() -> {
            field.requestFocus();
            field.selectAll();
        });

        field.setOnAction(e -> submitRename(field, index));
        field.focusedProperty().addListener((obs, old, focused) -> {
            if (!focused && field.isEditable()) submitRename(field, index);
        });
        field.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                field.setText(originalName);
                disableRenameMode(field);
            }
        });
    }

    private void submitRename(TextField field, int index) {
        TeamSelectionView.Listener l = listenerProvider.get();
        if (l != null) l.onRenameTeamClicked(index, field.getText());
        disableRenameMode(field);
    }

    private void disableRenameMode(TextField field) {
        field.setEditable(false);
        field.setFocusTraversable(false);
        field.setOnAction(null);
        field.setOnKeyPressed(null);
    }
}

