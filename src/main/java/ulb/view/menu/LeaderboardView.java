package ulb.view.menu;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;
import ulb.configuration.Configuration;
import ulb.model.leaderboard.LeaderboardEntry;
import ulb.controller.SoundController;
import javafx.scene.text.Font;
import java.util.ArrayList;
import java.util.List;
import ulb.view.common.ScrollUtils;

/**
 * Pure view for displaying the leaderboard.
 *
 * <p>Displays a scrollable, ranked list of entries (rank, player name, score).
 * Keyboard navigation mirrors.</p>
 *
 * <p><b>MVC role:</b> View only. Forwards all user events to {@link LeaderboardListener}.</p>
 */
public class LeaderboardView {
    private static final String ENTRY_ROW_CLASS = "entry-row";
    private static final String ENTRY_ROW_FOCUSED_CLASS = "entry-row-focused";
    private static final String RANK1_CLASS = "rank-1";
    private static final String RANK2_CLASS = "rank-2";
    private static final String RANK3_CLASS = "rank-3";

    private static final int ROW_SPACING = 16;

    public interface LeaderboardListener {
        void onBackClicked();
    }

    private LeaderboardListener listener;
    private final List<HBox> entryRows = new ArrayList<>();

    @FXML private VBox entriesContainer;
    @FXML private ScrollPane entriesScrollPane;
    @FXML private Button btnBack;


    @FXML
        void initialize() {
        Font.loadFont(getClass().getResourceAsStream(Configuration.FONT_PATH + "/NotoEmoji-VariableFont_wght.ttf"),20);
        Platform.runLater(this::focusFirstRow);
    }

    public void setListener(LeaderboardListener listener) {
        this.listener = listener;
    }

    /**
     * Replaces the current list with {@code entries} and focuses the first row.
     *
     * <p>Each row gets keyboard navigation: UP/DOWN moves between rows; DOWN on the
     * last row transfers focus to the Back button, and UP on the Back button returns
     * focus to the last row (wired via {@link #handleBackKeyPressed}).
     * Top-3 rows receive distinct CSS classes ({@code rank-1}, {@code rank-2}, {@code rank-3}).
     */
    public void displayEntries(List<LeaderboardEntry> entries) {
        if (entriesContainer == null) return;
        entriesContainer.setFillWidth(true); //makes sure the children are as wide as the parent container
        entriesContainer.getChildren().clear();
        entryRows.clear();

        for (int i = 0; i < entries.size(); i++) {
            HBox row = createEntryRow(entries.get(i), i + 1);
            entryRows.add(row);
            entriesContainer.getChildren().add(row);
        }

        Platform.runLater(this::focusFirstRow);
    }


    @FXML
        void handleBack() {
        SoundController.getInstance().playButtonClick();
        if (listener != null) listener.onBackClicked();
    }

    @FXML
        void handleBackKeyPressed(javafx.scene.input.KeyEvent event) {
        if (event.getCode() == KeyCode.UP) {
            focusLastRow();
            event.consume();
        }
    }

    private HBox createEntryRow(LeaderboardEntry entry, int rank) {
        HBox row = buildEntryRowBox(entry, rank);
        int rowIndex = rank - 1;
        configureFocusStyle(row, rank);
        configureRowKeyNavigation(row, rowIndex);
        return row;
    }

    private HBox buildEntryRowBox(LeaderboardEntry entry, int rank) {
        Label rankLabel = new Label(rankEmoji(rank) + " #" + rank);
        rankLabel.getStyleClass().add("rank-label");
        rankLabel.setStyle("-fx-font-family: 'Noto Emoji';");
        Label nameLabel = new Label(entry.getUsername());
        nameLabel.getStyleClass().add("name-label");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        HBox row = getHBox(entry, rankLabel, nameLabel, spacer);
        row.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(row, Priority.ALWAYS);
        row.setMinHeight(Region.USE_PREF_SIZE);
        row.setFocusTraversable(true);
        row.getStyleClass().add(baseClassForRank(rank));
        return row;
    }

    private void configureFocusStyle(HBox row, int rank) {
        row.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                row.getStyleClass().setAll(baseClassForRank(rank));
            } else {
                row.getStyleClass().setAll(ENTRY_ROW_FOCUSED_CLASS);
            }
            ensureNodeVisible(row);
        });
    }

    private void configureRowKeyNavigation(HBox row, int rowIndex) {
        row.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP -> {
                    if (rowIndex > 0) requestFocus(entryRows.get(rowIndex - 1));
                    event.consume();
                }
                case DOWN -> {
                    if (rowIndex + 1 < entryRows.size()) requestFocus(entryRows.get(rowIndex + 1));
                    else requestFocus(btnBack);
                    event.consume();
                }
            }
        });
    }

    @NotNull
    private static HBox getHBox(LeaderboardEntry entry, Label rankLabel, Label nameLabel,Region spacer) {
        Label scoreLabel = new Label(entry.score() + " pts");
        scoreLabel.getStyleClass().add("score-label");

        return new HBox(ROW_SPACING, rankLabel, nameLabel,spacer, scoreLabel);
    }

    private String rankEmoji(int rank) {
        return switch (rank) {
            case 1 -> "🥇";
            case 2 -> "🥈";
            case 3 -> "🥉";
            default -> "  ";
        };
    }

    private String baseClassForRank(int rank) {
        return switch (rank) {
            case 1 -> RANK1_CLASS;
            case 2 -> RANK2_CLASS;
            case 3 -> RANK3_CLASS;
            default -> ENTRY_ROW_CLASS;
        };
    }

    private void focusFirstRow() {
        if (!entryRows.isEmpty()) requestFocus(entryRows.getFirst());
        else requestFocus(btnBack);
    }

    private void focusLastRow() {
        if (!entryRows.isEmpty()) requestFocus(entryRows.getLast());
    }

    private void requestFocus(Node node) {
        if (node == null) return;
        Platform.runLater(() -> {
            node.requestFocus();
            ensureNodeVisible(node);
        });
    }

    private void ensureNodeVisible(Node node) {
        ScrollUtils.ensureVisible(entriesScrollPane, entriesContainer, node);
    }
}
