package ulb.view.menu.teamSelection;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ulb.controller.SoundController;
import ulb.model.team.Team;
import ulb.view.common.SelectionStyle;
import ulb.view.common.SelectionStyleSupport;

/**
 * Pure view for selecting the team used to start a battle.
 *
 * <p>Displays a scrollable list of team rows, each with rename and delete actions.
 * The player selects a team (row turns green) then clicks "Start Game".
 * An inline add row is shown when fewer than 5 teams exist.</p>
 *
 * <p>Row construction is delegated to {@link TeamRowFactory}.
 * Inline editing (create / rename) is delegated to {@link TeamInlineEditHandler}.
 * Keyboard navigation is delegated to {@link TeamSelectionKeyNav}.</p>
 *
 * <p><b>MVC role:</b> View. Forwards all user events to {@link Listener}.</p>
 */
public class TeamSelectionView {

    static final int TEAM_COLUMN_SELECT = 0;
    static final int TEAM_COLUMN_RENAME = 1;
    static final int TEAM_COLUMN_DELETE = 2;

    private static final String TEAM_ROW_CLASS = "team-row";
    static final SelectionStyle TEAM_ROW_STYLE =
            new SelectionStyle(TEAM_ROW_CLASS, "team-row-selected", "team-row-focused");

    private static final String STATUS_DEFAULT_CLASS = "status-default";
    private static final String STATUS_ERROR_CLASS   = "status-error";
    private static final String STATUS_SUCCESS_CLASS = "status-success";

    /** Nodes making up a single team row, shared with key-nav and row-factory helpers. */
    record TeamRowNodes(HBox row, Button renameButton, Button deleteButton) {}

    public interface Listener {
        void onTeamSelected(int index);
        void onCreateTeamClicked(String name);
        void onRenameTeamClicked(int index, String newName);
        void onDeleteTeamClicked(int index);
        void onEditTeamClicked();
        void onStartGameClicked();
        void onBackClicked();
    }

    public enum Status {
        CLEAR, TEAM_NAME_REQUIRED, TEAM_NAME_ALREADY_EXISTS, MAX_TEAMS_REACHED,
        SAVE_FAILED, TEAM_CREATED, SELECT_TEAM_FIRST, NEW_NAME_REQUIRED,
        TEAM_RENAMED, DELETE_FAILED, TEAM_DELETED, EMPTY_TEAM
    }

    private Listener listener;
    private int highlightedTeamIndex = -1;
    private Button addRowButton;

    private final List<TeamRowNodes> interactiveTeamRows = new ArrayList<>();

    private TeamRowFactory rowFactory;
    private TeamInlineEditHandler editHandler;
    private TeamSelectionKeyNav keyNav;

    @FXML private VBox teamsContainer;
    @FXML private ScrollPane teamsScrollPane;
    @FXML private Label statusLabel;
    @FXML private Button btnBack;
    @FXML private Button btnStartGame;
    @FXML private Button btnEditTeam;

    @FXML
        void initialize() {
        editHandler = new TeamInlineEditHandler(
                teamsContainer,
                () -> listener,
                this::restoreAddRowButton);

        rowFactory = new TeamRowFactory(
                () -> highlightedTeamIndex,
                this::notifyTeamSelected,
                index -> { if (listener != null) listener.onDeleteTeamClicked(index); },
                editHandler::enableRenameMode,
                this::handleNavKey,
                editHandler::showPendingCreateRow);

        keyNav = new TeamSelectionKeyNav(
                interactiveTeamRows,
                () -> addRowButton,
                btnBack, btnEditTeam, btnStartGame,
                teamsScrollPane, teamsContainer);

        Platform.runLater(keyNav::focusFirstInteractiveNode);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @FXML
        void handleBack() {
        SoundController.getInstance().playButtonClick();
        if (listener != null) listener.onBackClicked();
    }

    @FXML
        void handleStartGame() {
        SoundController.getInstance().playButtonClick();
        SoundController.getInstance().stopMenuMusic();
        if (listener != null) listener.onStartGameClicked();
    }

    @FXML
        void handleEditTeam() {
        SoundController.getInstance().playButtonClick();
        if (listener != null) listener.onEditTeamClicked();
    }

    @FXML
        void handleBackKeyPressed(KeyEvent event) {
        keyNav.handleBackKeyPressed(event);
    }

    @FXML
        void handleEditTeamKeyPressed(KeyEvent event) {
        keyNav.handleEditTeamKeyPressed(event);
    }

    @FXML
        void handleStartGameKeyPressed(KeyEvent event) {
        keyNav.handleStartGameKeyPressed(event);
    }

    /**
     * Rebuilds the team list from scratch.
     *
     * <p>An inline "+" add row is appended when the list has fewer than 5 teams.
     * The first interactive node receives focus via {@code Platform.runLater}.
     */
    public void displayTeams(List<Team> teams) {
        teamsContainer.getChildren().clear();
        interactiveTeamRows.clear();
        editHandler.reset();
        addRowButton = null;

        for (int i = 0; i < teams.size(); i++) {
            TeamRowNodes nodes = rowFactory.buildTeamRow(teams.get(i), i);
            interactiveTeamRows.add(nodes);
            teamsContainer.getChildren().add(nodes.row());
        }

        if (teams.size() < 5) {
            teamsContainer.getChildren().add(restoreAddRowButton());
        }

        Platform.runLater(keyNav::focusFirstInteractiveNode);
    }

    public void highlightSelectedTeam(int selectedIndex) {
        if (interactiveTeamRows.isEmpty()) return;
        for (int i = 0; i < interactiveTeamRows.size(); i++) {
            SelectionStyleSupport.applyStyle(interactiveTeamRows.get(i).row(), i, selectedIndex, TEAM_ROW_STYLE);
        }
        highlightedTeamIndex = selectedIndex;
    }

    public void clearSelectedTeamHighlight() {
        highlightSelectedTeam(-1);
    }

    public void setStartButtonDisabled(boolean disabled) {
        btnStartGame.setDisable(disabled);
    }

    public void setEditButtonDisabled(boolean disabled) {
        btnEditTeam.setDisable(disabled);
    }

    public void showStatus(Status status) {
        showStatus(status, null);
    }

    public void showStatus(Status status, String detail) {
        Status safe = status == null ? Status.CLEAR : status;
        String message = switch (safe) {
            case CLEAR                    -> "";
            case TEAM_NAME_REQUIRED       -> "Team name is required.";
            case TEAM_NAME_ALREADY_EXISTS -> "Team name already exists.";
            case MAX_TEAMS_REACHED        -> "Cannot create team: maximum number of teams reached.";
            case SAVE_FAILED              -> "Cannot save teams: " + safeDetail(detail);
            case TEAM_CREATED             -> "Team created.";
            case SELECT_TEAM_FIRST        -> "Select a team first.";
            case NEW_NAME_REQUIRED        -> "New name is required.";
            case TEAM_RENAMED             -> "Team renamed.";
            case DELETE_FAILED            -> "Cannot delete team.";
            case TEAM_DELETED             -> "Team deleted.";
            case EMPTY_TEAM               -> "Cannot start with an empty team.";
        };
        boolean isSuccess = safe == Status.TEAM_CREATED || safe == Status.TEAM_RENAMED || safe == Status.TEAM_DELETED;
        if (isSuccess) refreshSuccessStatus(message); else refreshStatus(message);
    }

    private void refreshStatus(String message) {
        statusLabel.setText(message == null ? "" : message);
        statusLabel.getStyleClass().removeAll(STATUS_DEFAULT_CLASS, STATUS_ERROR_CLASS, STATUS_SUCCESS_CLASS);
        statusLabel.getStyleClass().add(STATUS_DEFAULT_CLASS);
    }

    private void refreshSuccessStatus(String message) {
        statusLabel.setText(message == null ? "" : message);
        statusLabel.getStyleClass().removeAll(STATUS_DEFAULT_CLASS, STATUS_ERROR_CLASS, STATUS_SUCCESS_CLASS);
        statusLabel.getStyleClass().add(STATUS_SUCCESS_CLASS);
    }

    private String safeDetail(String detail) {
        return detail == null ? "" : detail;
    }

    private void notifyTeamSelected(int index) {
        if (listener != null) listener.onTeamSelected(index);
    }

    private boolean handleNavKey(KeyCode code, int rowIndex, int colIndex) {
        return keyNav != null && keyNav.handle(code, rowIndex, colIndex);
    }

    /**
     * Creates a fresh add-row button, wires keyboard navigation onto it,
     * stores it in {@link #addRowButton}, and returns it.
     */
    private Button restoreAddRowButton() {
        Button btn = rowFactory.buildAddRowButton();
        keyNav.wireAddRowNavigation(btn);
        addRowButton = btn;
        return btn;
    }
}

