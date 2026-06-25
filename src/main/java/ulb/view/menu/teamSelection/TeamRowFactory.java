package ulb.view.menu.teamSelection;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import ulb.configuration.Configuration;
import ulb.controller.SoundController;
import ulb.model.team.Team;
import ulb.view.common.SelectionStyleSupport;

/**
 * Builds team-row HBoxes and the add-row button for {@link TeamSelectionView}.
 * All interaction events are forwarded via callbacks supplied at construction time.
 */
class TeamRowFactory {

    @FunctionalInterface
    interface RenameModeCallback {
        void enable(TextField field, int index, String originalName);
    }

    @FunctionalInterface
    interface KeyNavCallback {
        boolean handle(KeyCode code, int rowIndex, int columnIndex);
    }

    private static final int ICON_SIZE = 30;
    static final int ROW_SPACING = 16;
    static final String ADD_ROW_CLASS = "add-row";
    static final String ADD_ROW_FOCUSED_CLASS = "add-row-focused";

    private final IntSupplier getHighlightedIndex;
    private final IntConsumer onTeamSelected;
    private final IntConsumer onDeleteClicked;
    private final RenameModeCallback onRenameMode;
    private final KeyNavCallback onKeyNav;
    private final Runnable onAddClicked;

    TeamRowFactory(IntSupplier getHighlightedIndex, IntConsumer onTeamSelected,
                   IntConsumer onDeleteClicked, RenameModeCallback onRenameMode,
                   KeyNavCallback onKeyNav, Runnable onAddClicked) {
        this.getHighlightedIndex = getHighlightedIndex;
        this.onTeamSelected = onTeamSelected;
        this.onDeleteClicked = onDeleteClicked;
        this.onRenameMode = onRenameMode;
        this.onKeyNav = onKeyNav;
        this.onAddClicked = onAddClicked;
    }

    TeamSelectionView.TeamRowNodes buildTeamRow(Team team, int index) {
        TextField nameField = buildNameField(team.getName(), index);
        Button renameButton = buildRenameButton(nameField, index, team.getName());
        Button deleteButton = buildDeleteButton(index);
        HBox row = buildRow(nameField, renameButton, deleteButton, index);
        return new TeamSelectionView.TeamRowNodes(row, renameButton, deleteButton);
    }

    Button buildAddRowButton() {
        Button button = new Button("+");
        button.setMaxWidth(Double.MAX_VALUE);
        button.setPrefHeight(78);
        button.getStyleClass().add(ADD_ROW_CLASS);
        button.getProperties().put("focus-managed", true);
        button.focusedProperty().addListener((obs, old, focused) -> {
            button.getStyleClass().removeAll(ADD_ROW_CLASS, ADD_ROW_FOCUSED_CLASS);
            button.getStyleClass().add(Boolean.TRUE.equals(focused) ? ADD_ROW_FOCUSED_CLASS : ADD_ROW_CLASS);
        });
        button.setOnAction(e -> {
            SoundController.getInstance().playButtonClick();
            onAddClicked.run();
        });
        return button;
    }

    private TextField buildNameField(String name, int index) {
        TextField field = new TextField(name);
        field.setEditable(false);
        field.setFocusTraversable(false);
        field.getStyleClass().add("team-name-field");
        HBox.setHgrow(field, Priority.ALWAYS);
        field.setOnMouseClicked(e -> onTeamSelected.accept(index));
        return field;
    }

    private Button buildRenameButton(TextField nameField, int index, String originalName) {
        Button button = new Button();
        button.setGraphic(buildIcon(Configuration.RENAME_ICON_PATH));
        button.setOnAction(e -> {
            SoundController.getInstance().playButtonClick();
            onTeamSelected.accept(index);
            onRenameMode.enable(nameField, index, originalName);
        });
        button.setOnKeyPressed(e -> onKeyNav.handle(e.getCode(), index, TeamSelectionView.TEAM_COLUMN_RENAME));
        return button;
    }

    private Button buildDeleteButton(int index) {
        Button button = new Button();
        button.setGraphic(buildIcon(Configuration.BIN_ICON_PATH));
        button.setOnAction(e -> {
            SoundController.getInstance().playButtonClick();
            onTeamSelected.accept(index);
            onDeleteClicked.accept(index);
        });
        button.setOnKeyPressed(e -> onKeyNav.handle(e.getCode(), index, TeamSelectionView.TEAM_COLUMN_DELETE));
        return button;
    }

    private HBox buildRow(TextField nameField, Button renameButton, Button deleteButton, int index) {
        HBox row = new HBox(ROW_SPACING, nameField, renameButton, deleteButton);
        row.setMinHeight(Region.USE_PREF_SIZE);
        row.setFocusTraversable(true);
        SelectionStyleSupport.applyStyle(row, index, getHighlightedIndex.getAsInt(), TeamSelectionView.TEAM_ROW_STYLE);
        row.focusedProperty().addListener((obs, old, focused) ->
            SelectionStyleSupport.applyStyle(row, index, getHighlightedIndex.getAsInt(), TeamSelectionView.TEAM_ROW_STYLE)
        );
        row.setOnMouseClicked(e -> { row.requestFocus(); onTeamSelected.accept(index); });
        row.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.SPACE) {
                onTeamSelected.accept(index);
                e.consume();
            } else if (onKeyNav.handle(e.getCode(), index, TeamSelectionView.TEAM_COLUMN_SELECT)) {
                e.consume();
            }
        });
        return row;
    }

    private ImageView buildIcon(String resourcePath) {
        ImageView view = new ImageView(loadImage(resourcePath));
        view.setFitWidth(ICON_SIZE);
        view.setFitHeight(ICON_SIZE);
        view.setPreserveRatio(true);
        return view;
    }

    private Image loadImage(String resourcePath) {
        try (InputStream stream = getClass().getResourceAsStream(resourcePath)) {
            if (stream == null) throw new IllegalStateException("Icon not found: " + resourcePath);
            return new Image(stream);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to load icon: " + resourcePath, e);
        }
    }
}

