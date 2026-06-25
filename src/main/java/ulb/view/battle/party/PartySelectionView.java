package ulb.view.battle.party;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ulb.controller.SoundController;
import ulb.model.domain.Bugemon;
import ulb.model.special.DomainExpansion;
import ulb.model.team.Team;
import ulb.view.common.FocusHighlight;
import ulb.view.common.SpriteLoader;

/**
 * Pure view used to display the player's party during battle.
 *
 * <p>Party button content is built by {@link PartyCardBuilder}.
 * The selected-Bugemon detail panel is managed by {@link PartyPreviewPanel}.</p>
 *
 * <p><b>MVC role:</b> View. Forwards all user events to {@link Listener}.</p>
 */
public class PartySelectionView {

    public interface Listener {
        void onBugemonSelected(int index);
        void onBackClicked();
    }

    @FXML private HBox       partyContainer;
    @FXML private Button     btnBack;
    @FXML private ImageView  selectedBugemonSprite;
    @FXML private Label      selectedBugemonStateBadge;
    @FXML private Label      selectedBugemonName;
    @FXML private Label      selectedBugemonElement;
    @FXML private ProgressBar selectedBugemonHpBar;
    @FXML private Label      selectedBugemonHp;
    @FXML private Label      selectedBugemonStatus;
    @FXML private Label      selectedBugemonAttack;
    @FXML private Label      selectedBugemonDefense;
    @FXML private Label      selectedBugemonMagicAttack;
    @FXML private Label      selectedBugemonMagicDefense;
    @FXML private Label      selectedBugemonInitiative;
    @FXML private Button     statsTabButton;
    @FXML private Button     attacksTabButton;
    @FXML private Button     specialTabButton;
    @FXML private VBox       selectedBugemonStatsPanel;
    @FXML private ScrollPane selectedBugemonAttacksPane;
    @FXML private VBox       selectedBugemonAttacksList;
    @FXML private ScrollPane selectedBugemonSpecialPane;
    @FXML private VBox       selectedBugemonSpecialList;

    private final SpriteLoader    spriteLoader  = new SpriteLoader();
    private final List<Button>    partyButtons  = new ArrayList<>();

    private Listener          listener;
    private PartyPreviewPanel preview;

    private javafx.event.EventHandler<KeyEvent> keyNavHandler;

    @FXML
        void initialize() {
        preview = new PartyPreviewPanel(
                selectedBugemonSprite, selectedBugemonStateBadge,
                selectedBugemonName, selectedBugemonElement,
                selectedBugemonHpBar, selectedBugemonHp, selectedBugemonStatus,
                selectedBugemonAttack, selectedBugemonDefense,
                selectedBugemonMagicAttack, selectedBugemonMagicDefense, selectedBugemonInitiative,
                selectedBugemonStatsPanel, selectedBugemonAttacksPane, selectedBugemonAttacksList,
                selectedBugemonSpecialPane, selectedBugemonSpecialList,
                statsTabButton, attacksTabButton, specialTabButton,
                spriteLoader);
        preview.initialRender();

        keyNavHandler = event -> {
            switch (event.getCode()) {
                case LEFT   -> { moveFocusParty(-1); event.consume(); }
                case RIGHT  -> { moveFocusParty(1);  event.consume(); }
                case ESCAPE -> { handleBack();            event.consume(); }
                default -> {}
            }
        };
        // Register key nav and clean it up when the scene changes
        partyContainer.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (oldScene != null) oldScene.removeEventFilter(KeyEvent.KEY_PRESSED, keyNavHandler);
            if (newScene != null) newScene.addEventFilter(KeyEvent.KEY_PRESSED, keyNavHandler);
        });
    }

    public void setListener(Listener listener) { this.listener = listener; }

    public void setDomainExpansionResolver(Function<Bugemon, Optional<DomainExpansion>> resolver) {
        preview.setDomainExpansionResolver(resolver);
    }

    /**
     * Rebuilds the party button row and defaults the preview to {@code activeBugemon}
     * (or the first member if {@code activeBugemon} is null).
     *
     * @param playerTeam    the team to display; {@code null} or empty shows a placeholder
     * @param activeBugemon the currently fighting Bugemon — marked with an "ACTIVE" badge
     */
    public void displayParty(Team playerTeam, Bugemon activeBugemon) {
        ObservableList<Node> children = partyContainer.getChildren();
        children.clear();
        partyButtons.clear();

        if (playerTeam == null || playerTeam.isEmpty()) {
            preview.clear();
            Label empty = new Label("No Bugemon available.");
            empty.getStyleClass().add("placeholder-label");
            children.add(empty);
            return;
        }

        Bugemon defaultPreview = activeBugemon != null
                ? activeBugemon
                : playerTeam.getFirstMember().orElse(null);
        preview.update(defaultPreview, activeBugemon);

        for (int i = 0; i < playerTeam.getMembers().size(); i++) {
            Button btn = buildPartyButton(playerTeam.getMembers().get(i), i, activeBugemon);
            children.add(btn);
            partyButtons.add(btn);
        }
        if (!partyButtons.isEmpty()) Platform.runLater(() -> partyButtons.get(0).requestFocus());
    }

    @FXML void handleBack()        { SoundController.getInstance().playButtonClick(); if (listener != null) listener.onBackClicked(); }
    @FXML void handleShowStats()   { SoundController.getInstance().playButtonClick(); preview.showStats(); }
    @FXML void handleShowAttacks() { SoundController.getInstance().playButtonClick(); preview.showAttacks(); }
    @FXML void handleShowSpecial() { SoundController.getInstance().playButtonClick(); preview.showSpecial(); }

    private Button buildPartyButton(Bugemon bugemon, int index, Bugemon activeBugemon) {
        boolean active = bugemon == activeBugemon;
        Button button = new Button();
        button.setMinSize(128, 152);
        button.setPrefSize(128, 152);
        button.setMaxSize(128, 152);
        button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        button.setGraphic(PartyCardBuilder.buildButtonContent(bugemon, active, spriteLoader));
        PartyCardBuilder.applyButtonStyle(button, bugemon, active);
        button.setOnMouseEntered(e -> { button.requestFocus(); preview.update(bugemon, activeBugemon); });
        button.focusedProperty().addListener((obs, old, focused) -> {
            if (Boolean.TRUE.equals(focused)) preview.update(bugemon, activeBugemon);
        });
        FocusHighlight.apply(button);
        button.setOnAction(e -> {
            SoundController.getInstance().playButtonClick();
            if (listener != null && bugemon.isHealthy()) listener.onBugemonSelected(index);
        });
        if (!bugemon.isHealthy()) button.setOpacity(0.85);
        return button;
    }

    private void moveFocusParty(int delta) {
        for (int i = 0; i < partyButtons.size(); i++) {
            if (partyButtons.get(i).isFocused()) {
                partyButtons.get((i + delta + partyButtons.size()) % partyButtons.size()).requestFocus();
                return;
            }
        }
        if (!partyButtons.isEmpty()) partyButtons.get(0).requestFocus();
    }
}
