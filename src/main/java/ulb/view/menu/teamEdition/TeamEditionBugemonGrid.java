package ulb.view.menu.teamEdition;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.IntConsumer;
import java.util.function.Supplier;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import ulb.view.common.SpriteLoader;

/**
 * Manages the available-Bugemon card grid for {@link TeamEditionView}.
 *
 * <p>Builds a FlowPane of card tiles split around a centre "?" slot (Tekken-style).
 * Single click/Enter selects; double click/double-Enter adds to the team.
 * The centre slot triggers a random-Bugemon animation on double activation.</p>
 *
 * <p>Navigation callbacks are injected so this class stays decoupled from
 * {@link TeamEditionKeyNav}.</p>
 */
class TeamEditionBugemonGrid {

    private static final int CARD_WIDTH          = 90;
    private static final int CARD_HEIGHT         = 85;
    private static final int CARD_VGAP           = 8;
    static  final int CARD_ROWS                  = 5;
    private static final int CENTER_SLOT_WIDTH   = 160;
    private static final int CENTER_SLOT_HEIGHT  =
            CARD_ROWS * CARD_HEIGHT + (CARD_ROWS - 1) * CARD_VGAP;
    private static final int IMAGE_WIDTH         = 82;
    private static final int IMAGE_HEIGHT        = 77;
    private static final int LOCKED_SATURATION   = -1;
    private static final double LOCKED_OPACITY   = 0.55;
    private static final long DOUBLE_PRESS_MS    = 400;

    private static final String CARD_CLASS          = "available-card";
    private static final String CARD_SELECTED_CLASS = "available-card-selected";
    private static final String CARD_FOCUSED_CLASS  = "available-card-focused";
    private static final String LOCKED_CARD_CLASS   = "available-card-locked";

    // Mutable state
    private List<TeamEditionView.BugemonCardData> currentBugemons = List.of();
    private List<StackPane> cards = List.of();
    private StackPane centerSlotNode;
    private long lastCardEnterTime = 0;

    // Dependencies
    private final FlowPane container;
    private final Supplier<TeamEditionView.Listener> listenerProvider;
    private final SpriteLoader spriteLoader;
    private final BiFunction<KeyCode, Integer, Boolean> cardKeyNav;
    private final IntConsumer onFocusCard;
    private final Runnable onFocusLastSlot;
    private final Supplier<Integer> getSelectedIndex;

    TeamEditionBugemonGrid(
            FlowPane container,
            ScrollPane scrollPane,
            Supplier<TeamEditionView.Listener> listenerProvider,
            SpriteLoader spriteLoader,
            BiFunction<KeyCode, Integer, Boolean> cardKeyNav,
            IntConsumer onFocusCard,
            Runnable onFocusLastSlot,
            Supplier<Integer> getSelectedIndex) {
        this.container        = container;
        this.listenerProvider = listenerProvider;
        this.spriteLoader     = spriteLoader;
        this.cardKeyNav       = cardKeyNav;
        this.onFocusCard      = onFocusCard;
        this.onFocusLastSlot  = onFocusLastSlot;
        this.getSelectedIndex = getSelectedIndex;
        lockScroll(scrollPane);
    }

    void show(List<TeamEditionView.BugemonCardData> bugemons) {
        container.getChildren().clear();
        currentBugemons = bugemons == null ? List.of() : bugemons;
        cards = new ArrayList<>(currentBugemons.size());
        for (int i = 0; i < currentBugemons.size(); i++) {
            cards.add(createCard(i, currentBugemons.get(i)));
        }
        int split = cards.size() / 2;
        for (int i = 0; i < split; i++)              container.getChildren().add(cards.get(i));
        container.getChildren().add(buildCenterSlot());
        for (int i = split; i < cards.size(); i++)   container.getChildren().add(cards.get(i));
    }

    void highlight(int selectedIndex) {
        for (int i = 0; i < cards.size(); i++) {
            boolean accessible = i < currentBugemons.size() && currentBugemons.get(i).accessible();
            applyCardStyle(cards.get(i), i, accessible, selectedIndex);
        }
    }

    void resetHighlight() { highlight(-1); }

    List<StackPane>                            getCards()           { return cards; }
    List<TeamEditionView.BugemonCardData>      getCurrentBugemons() { return currentBugemons; }
    StackPane                                  getCenterSlot()      { return centerSlotNode; }

    private StackPane buildCenterSlot() {
        if (centerSlotNode != null) return centerSlotNode;
        Label label = new Label("?");
        label.getStyleClass().add("tekken-center-slot-label");
        centerSlotNode = new StackPane(label);
        centerSlotNode.setPrefSize(CENTER_SLOT_WIDTH, CENTER_SLOT_HEIGHT);
        centerSlotNode.setMinSize(CENTER_SLOT_WIDTH, CENTER_SLOT_HEIGHT);
        centerSlotNode.setMaxSize(CENTER_SLOT_WIDTH, CENTER_SLOT_HEIGHT);
        centerSlotNode.getStyleClass().add("tekken-center-slot");
        centerSlotNode.setFocusTraversable(true);
        registerCenterSlotHandlers();
        return centerSlotNode;
    }

    private void registerCenterSlotHandlers() {
        centerSlotNode.setOnMouseEntered(e -> applyCenterHighlight(true));
        centerSlotNode.setOnMouseExited(e  -> applyCenterHighlight(centerSlotNode.isFocused()));
        centerSlotNode.focusedProperty().addListener((obs, old, focused) -> applyCenterHighlight(focused));
        centerSlotNode.setOnMouseClicked(e -> {
            centerSlotNode.requestFocus();
            if (e.getClickCount() == 2) startRandomAnimation();
        });
        centerSlotNode.setOnKeyPressed(this::handleCenterSlotKey);
    }

    private void handleCenterSlotKey(KeyEvent e) {
        int split = cards.size() / 2;
        switch (e.getCode()) {
            case ENTER -> { handleCenterEnter();                                    e.consume(); }
            case LEFT  -> { navigateLeft(split);                                    e.consume(); }
            case RIGHT -> { if (split < cards.size()) onFocusCard.accept(split);    e.consume(); }
            default -> {}
        }
    }

    private void navigateLeft(int split) {
        if (split > 0) onFocusCard.accept(split - 1);
        else           onFocusLastSlot.run();
    }

    private void handleCenterEnter() {
        long now = System.currentTimeMillis();
        if ((now - lastCardEnterTime) <= DOUBLE_PRESS_MS) startRandomAnimation();
        lastCardEnterTime = now;
    }

    private void applyCenterHighlight(boolean on) {
        if (centerSlotNode == null) return;
        centerSlotNode.getStyleClass().remove("tekken-center-slot-focused");
        if (on) centerSlotNode.getStyleClass().add("tekken-center-slot-focused");
    }

    private void startRandomAnimation() {
        TeamEditionView.Listener l = listenerProvider.get();
        if (l == null || currentBugemons.isEmpty() || l.isTeamFull()) return;
        List<Integer> eligible   = buildIndexList(true,  l);
        List<Integer> accessible = buildIndexList(false, l);
        if (eligible.isEmpty()) return;
        int finalIndex = eligible.get(new Random().nextInt(eligible.size()));
        buildRandomTimeline(accessible, finalIndex, l).play();
    }

    private Timeline buildRandomTimeline( List<Integer> accessible, int finalIndex, TeamEditionView.Listener l) {
        int[] intervals = {60, 60, 80, 80, 100, 120, 160, 200, 260, 330};
        Random rng      = new Random();
        Timeline timeline = new Timeline();
        double elapsed = 0;
        for (int i = 0; i < intervals.length - 1; i++) {
            elapsed += intervals[i];
            final int idx = accessible.get(rng.nextInt(accessible.size()));
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(elapsed),
                    e -> l.onAvailableBugemonSelected(idx)));
        }
        elapsed += intervals[intervals.length - 1];
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(elapsed), e -> {
            l.onAvailableBugemonSelected(finalIndex);
            l.onAddBugemonRequested();
        }));
        return timeline;
    }

    /** If {@code eligibleOnly}: accessible AND not yet in team. Otherwise: just accessible. */
    private List<Integer> buildIndexList(boolean eligibleOnly, TeamEditionView.Listener l) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < currentBugemons.size(); i++) {
            TeamEditionView.BugemonCardData card = currentBugemons.get(i);
            if (!card.accessible()) continue;
            if (eligibleOnly && l.isBugemonInTeam(card.bugemonId())) continue;
            result.add(i);
        }
        return result;
    }

    private StackPane createCard(int index, TeamEditionView.BugemonCardData bugemon) {
        StackPane card = new StackPane();
        card.setPrefSize(CARD_WIDTH, CARD_HEIGHT);
        card.setMinSize(CARD_WIDTH, CARD_HEIGHT);
        card.setMaxSize(CARD_WIDTH, CARD_HEIGHT);
        card.setFocusTraversable(true);
        applyCardStyle(card, index, bugemon.accessible(), getSelectedIndex.get());
        card.getChildren().add(buildCardImage(bugemon));
        registerCardHandlers(card, index, bugemon);
        return card;
    }

    private void registerCardHandlers(StackPane card, int index, TeamEditionView.BugemonCardData bugemon) {
        card.focusedProperty().addListener((obs, old, focused) -> {
            applyCardStyle(card, index, bugemon.accessible(), getSelectedIndex.get());
            if (focused) { TeamEditionView.Listener l = listenerProvider.get();
                if (l != null) l.onAvailableBugemonSelected(index); }
        });
        card.setOnMouseEntered(e -> { TeamEditionView.Listener l = listenerProvider.get();
            if (l != null) l.onAvailableBugemonSelected(index); });
        card.setOnMouseClicked(e -> {
            card.requestFocus();
            TeamEditionView.Listener l = listenerProvider.get();
            if (l == null) return;
            l.onAvailableBugemonSelected(index);
            if (e.getClickCount() == 2 && bugemon.accessible()) l.onAddBugemonRequested();
        });
        card.setOnKeyPressed(e -> handleCardKey(e, index, bugemon.accessible()));
    }

    private ImageView buildCardImage(TeamEditionView.BugemonCardData bugemon) {
        ImageView img = new ImageView();
        img.setFitWidth(IMAGE_WIDTH);
        img.setFitHeight(IMAGE_HEIGHT);
        img.setPreserveRatio(true);
        img.setImage(spriteLoader.tryLoadSprite(bugemon.bugemonId().toLowerCase() + ".png").orElse(null));
        if (!bugemon.accessible()) {
            ColorAdjust grey = new ColorAdjust();
            grey.setSaturation(LOCKED_SATURATION);
            img.setEffect(grey);
            img.setOpacity(LOCKED_OPACITY);
        }
        return img;
    }

    private void handleCardKey(KeyEvent e, int index, boolean accessible) {
        switch (e.getCode()) {
            case ENTER -> { handleCardEnterKey(index, accessible); e.consume(); }
            case SPACE -> { handleCardSpaceKey(index);             e.consume(); }
            default    -> { if (cardKeyNav.apply(e.getCode(), index)) e.consume(); }
        }
    }

    private void handleCardSpaceKey(int index) {
        TeamEditionView.Listener l = listenerProvider.get();
        if (l != null) l.onAvailableBugemonSelected(index);
    }

    private void handleCardEnterKey(int index, boolean accessible) {
        long now = System.currentTimeMillis();
        boolean isDouble = (now - lastCardEnterTime) <= DOUBLE_PRESS_MS;
        lastCardEnterTime = now;
        TeamEditionView.Listener l = listenerProvider.get();
        if (l != null) {
            l.onAvailableBugemonSelected(index);
            if (isDouble && accessible) l.onAddBugemonRequested();
        }
    }

    private void applyCardStyle(StackPane card, int index, boolean accessible, int selectedIndex) {
        if (card == null) return;
        card.getStyleClass().removeAll(CARD_CLASS, CARD_SELECTED_CLASS, CARD_FOCUSED_CLASS, LOCKED_CARD_CLASS);
        if (index == selectedIndex) { card.getStyleClass().add(CARD_SELECTED_CLASS); return; }
        if (!accessible) { card.getStyleClass().add(card.isFocused() ? CARD_FOCUSED_CLASS : LOCKED_CARD_CLASS); return; }
        card.getStyleClass().add(card.isFocused() ? CARD_FOCUSED_CLASS : CARD_CLASS);
    }

    private void lockScroll(ScrollPane scrollPane) {
        scrollPane.setOnScroll(javafx.event.Event::consume);
        scrollPane.hvalueProperty().addListener((obs, old, v) -> {
            if (v.doubleValue() != 0.0) Platform.runLater(() -> scrollPane.setHvalue(0.0));
        });
        scrollPane.vvalueProperty().addListener((obs, old, v) -> {
            if (v.doubleValue() != 0.0) Platform.runLater(() -> scrollPane.setVvalue(0.0));
        });
    }
}

