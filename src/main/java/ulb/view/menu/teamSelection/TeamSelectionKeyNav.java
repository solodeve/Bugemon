package ulb.view.menu.teamSelection;

import java.util.List;
import java.util.function.Supplier;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import ulb.view.common.ScrollUtils;

/**
 * Manages all keyboard navigation for {@link TeamSelectionView}.
 *
 * <p>Handles UP/DOWN/LEFT/RIGHT navigation between team rows, the add-row button,
 * and the three bottom action buttons (Back, Edit Team, Start Game).
 *
 * <ul>
 *   <li>{@link #handle} â€” used as the {@link TeamRowFactory.KeyNavCallback}</li>
 *   <li>{@link #wireAddRowNavigation} â€” called each time a new add-row button is created</li>
 *   <li>{@link #focusFirstInteractiveNode} â€” called on load and after each list rebuild</li>
 *   <li>{@code handleXxxKeyPressed} â€” forwarded from FXML {@code onKeyPressed} handlers</li>
 * </ul>
 */
class TeamSelectionKeyNav {

    private final List<TeamSelectionView.TeamRowNodes> interactiveTeamRows;
    private final Supplier<Button> getAddRowButton;
    private final Button btnBack;
    private final Button btnEditTeam;
    private final Button btnStartGame;
    private final ScrollPane scrollPane;
    private final VBox container;

    TeamSelectionKeyNav(
            List<TeamSelectionView.TeamRowNodes> interactiveTeamRows,
            Supplier<Button> getAddRowButton,
            Button btnBack,
            Button btnEditTeam,
            Button btnStartGame,
            ScrollPane scrollPane,
            VBox container) {
        this.interactiveTeamRows = interactiveTeamRows;
        this.getAddRowButton = getAddRowButton;
        this.btnBack = btnBack;
        this.btnEditTeam = btnEditTeam;
        this.btnStartGame = btnStartGame;
        this.scrollPane = scrollPane;
        this.container = container;
    }

    boolean handle(KeyCode code, int rowIndex, int columnIndex) {
        if (code == null) return false;
        return switch (code) {
            case UP    -> focusAbove(rowIndex, columnIndex);
            case DOWN  -> focusBelow(rowIndex, columnIndex);
            case LEFT  -> focusHorizontal(rowIndex, columnIndex, -1);
            case RIGHT -> focusHorizontal(rowIndex, columnIndex, 1);
            default    -> false;
        };
    }

    /** Wires UP/DOWN/RIGHT navigation onto a freshly created add-row button. */
    void wireAddRowNavigation(Button addRow) {
        addRow.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case UP -> {
                    focusTeamNode(interactiveTeamRows.size() - 1, TeamSelectionView.TEAM_COLUMN_SELECT);
                    e.consume();
                }
                case DOWN -> {
                    focusBottomButton(TeamSelectionView.TEAM_COLUMN_SELECT);
                    e.consume();
                }
                case RIGHT -> {
                    focusBottomButton(TeamSelectionView.TEAM_COLUMN_RENAME);
                    e.consume();
                }
                default -> {}
            }
        });
    }

    void focusFirstInteractiveNode() {
        if (!interactiveTeamRows.isEmpty()) {
            focusTeamNode(0, TeamSelectionView.TEAM_COLUMN_SELECT);
            return;
        }
        Button addRow = getAddRowButton.get();
        if (addRow != null) {
            requestFocus(addRow);
            return;
        }
        requestFocus(btnBack);
    }

    void handleBackKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.UP) {
            Button addRow = getAddRowButton.get();
            if (addRow != null) {
                requestFocus(addRow);
            } else if (!interactiveTeamRows.isEmpty()) {
                focusTeamNode(interactiveTeamRows.size() - 1, TeamSelectionView.TEAM_COLUMN_SELECT);
            } else {
                return;
            }
            event.consume();
            return;
        }
        if (event.getCode() == KeyCode.RIGHT) {
            requestFocus(btnEditTeam);
            event.consume();
        }
    }

    void handleEditTeamKeyPressed(KeyEvent event) {
        if (handleUpFromBottomButtons(event, TeamSelectionView.TEAM_COLUMN_RENAME)) return;
        if (event.getCode() == KeyCode.LEFT) {
            requestFocus(btnBack);
            event.consume();
            return;
        }
        if (event.getCode() == KeyCode.RIGHT) {
            requestFocus(btnStartGame);
            event.consume();
        }
    }

    void handleStartGameKeyPressed(KeyEvent event) {
        if (handleUpFromBottomButtons(event, TeamSelectionView.TEAM_COLUMN_DELETE)) return;
        if (event.getCode() == KeyCode.LEFT) {
            requestFocus(btnEditTeam);
            event.consume();
        }
    }

    private boolean handleUpFromBottomButtons(KeyEvent event, int columnHint) {
        if (event.getCode() != KeyCode.UP) return false;
        Button addRow = getAddRowButton.get();
        if (addRow != null) {
            requestFocus(addRow);
            event.consume();
            return true;
        }
        if (!interactiveTeamRows.isEmpty()) {
            focusTeamNode(interactiveTeamRows.size() - 1, columnHint);
            event.consume();
            return true;
        }
        return false;
    }

    private boolean focusAbove(int rowIndex, int columnIndex) {
        if (rowIndex > 0) {
            focusTeamNode(rowIndex - 1, columnIndex);
            return true;
        }
        return false;
    }

    private boolean focusBelow(int rowIndex, int columnIndex) {
        if (rowIndex + 1 < interactiveTeamRows.size()) {
            focusTeamNode(rowIndex + 1, columnIndex);
            return true;
        }
        Button addRow = getAddRowButton.get();
        if (addRow != null && columnIndex == TeamSelectionView.TEAM_COLUMN_SELECT) {
            requestFocus(addRow);
            return true;
        }
        focusBottomButton(columnIndex);
        return true;
    }

    private boolean focusHorizontal(int rowIndex, int columnIndex, int delta) {
        int target = columnIndex + delta;
        if (target < TeamSelectionView.TEAM_COLUMN_SELECT || target > TeamSelectionView.TEAM_COLUMN_DELETE) {
            return false;
        }
        focusTeamNode(rowIndex, target);
        return true;
    }

    private void focusTeamNode(int rowIndex, int columnIndex) {
        if (rowIndex < 0 || rowIndex >= interactiveTeamRows.size()) return;
        TeamSelectionView.TeamRowNodes nodes = interactiveTeamRows.get(rowIndex);
        Node target = switch (columnIndex) {
            case TeamSelectionView.TEAM_COLUMN_RENAME -> nodes.renameButton();
            case TeamSelectionView.TEAM_COLUMN_DELETE -> nodes.deleteButton();
            default -> nodes.row();
        };
        requestFocus(target);
    }

    private void focusBottomButton(int columnIndex) {
        Node target = switch (columnIndex) {
            case TeamSelectionView.TEAM_COLUMN_RENAME -> btnEditTeam;
            case TeamSelectionView.TEAM_COLUMN_DELETE -> btnStartGame;
            default -> btnBack;
        };
        requestFocus(target);
    }

    private void requestFocus(Node node) {
        if (node == null) return;
        Platform.runLater(() -> {
            node.requestFocus();
            ScrollUtils.ensureVisible(scrollPane, container, node);
        });
    }
}

