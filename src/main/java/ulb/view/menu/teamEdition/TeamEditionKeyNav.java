package ulb.view.menu.teamEdition;

import java.util.List;
import java.util.function.IntConsumer;
import java.util.function.Supplier;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import ulb.view.common.FocusHighlight;
import ulb.view.common.ScrollUtils;

/**
 * Manages all keyboard navigation and focusable-node wiring for {@link TeamEditionView}.
 *
 * <p>Owns:
 * <ul>
 *   <li>Team-slot mouse/key listener setup ({@link #configureTeamSlots})</li>
 *   <li>Back-button key/mouse setup ({@link #configureActionButtons})</li>
 *   <li>Arrow-key routing between cards, the centre slot, team slots and the back button</li>
 * </ul>
 *
 * All mutable grid data is read via suppliers so this class holds no stale references
 * after {@link TeamEditionBugemonGrid#show} rebuilds the card list.
 */
class TeamEditionKeyNav {

    private static final long DOUBLE_PRESS_MS = 400;

    private long lastSlotEnterTime = 0;

    private final List<StackPane> slotPanes;
    private final Supplier<List<StackPane>> getCards;
    private final Supplier<StackPane> getCenterSlot;
    private final Supplier<List<TeamEditionView.BugemonCardData>> getAvailableBugemons;
    private final Button backButton;
    private final ScrollPane availableScrollPane;
    private final FlowPane availableContainer;
    private final Supplier<TeamEditionView.Listener> listenerProvider;
    private final IntConsumer onUpdateSlotStyle;

    TeamEditionKeyNav(
            List<StackPane> slotPanes,
            Supplier<List<StackPane>> getCards,
            Supplier<StackPane> getCenterSlot,
            Supplier<List<TeamEditionView.BugemonCardData>> getAvailableBugemons,
            Button backButton,
            ScrollPane availableScrollPane,
            FlowPane availableContainer,
            Supplier<TeamEditionView.Listener> listenerProvider,
            IntConsumer onUpdateSlotStyle) {
        this.slotPanes            = slotPanes;
        this.getCards             = getCards;
        this.getCenterSlot        = getCenterSlot;
        this.getAvailableBugemons = getAvailableBugemons;
        this.backButton           = backButton;
        this.availableScrollPane  = availableScrollPane;
        this.availableContainer   = availableContainer;
        this.listenerProvider     = listenerProvider;
        this.onUpdateSlotStyle    = onUpdateSlotStyle;
    }

    void configureTeamSlots() {
        for (int i = 0; i < slotPanes.size(); i++) {
            final int idx = i;
            StackPane pane = slotPanes.get(i);
            pane.setFocusTraversable(true);
            onUpdateSlotStyle.accept(idx);
            pane.setOnMouseEntered(e -> notify(l -> l.onTeamSlotSelected(idx)));
            pane.focusedProperty().addListener((obs, old, focused) -> {
                onUpdateSlotStyle.accept(idx);
                if (focused) notify(l -> l.onTeamSlotSelected(idx));
            });
            pane.setOnMouseClicked(e -> {
                pane.requestFocus();
                notify(l -> {
                    l.onTeamSlotSelected(idx);
                    if (e.getClickCount() == 2) l.onRemoveBugemonRequested();
                });
            });
            pane.setOnKeyPressed(e -> handleSlotKeyPressed(e, idx));
        }
    }

    void configureActionButtons() {
        backButton.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.DOWN) { focusFirstAvailableCardOrSlot(); e.consume(); }
        });
        backButton.setOnMouseEntered(e -> backButton.requestFocus());
        FocusHighlight.apply(backButton);
    }

    void focusInitialControl() {
        focusSlot(0);
    }

    boolean handleAvailableCardNavigation(KeyCode code, int cardIndex) {
        if (code == null) return false;
        List<StackPane> cards = getCards.get();
        int split = cards.size() / 2;
        StackPane center = getCenterSlot.get();

        return switch (code) {
            case LEFT -> {
                int target = cardIndex - TeamEditionBugemonGrid.CARD_ROWS;
                if (cardIndex >= split && target < split && center != null) { requestFocus(center); yield true; }
                int found = validCard(target, cards);
                if (found >= 0 && col(found) == col(cardIndex) - 1) { focusCard(found); yield true; }
                focusSlot(slotPanes.size() - 1);
                yield true;
            }
            case RIGHT -> {
                int target = cardIndex + TeamEditionBugemonGrid.CARD_ROWS;
                if (cardIndex < split && target >= split && center != null) { requestFocus(center); yield true; }
                int found = validCard(target, cards);
                if (found >= 0 && col(found) == col(cardIndex) + 1) { focusCard(found); yield true; }
                yield false;
            }
            case UP -> {
                if (row(cardIndex) > 0) {
                    int found = validCard(cardIndex - 1, cards);
                    if (found >= 0 && col(found) == col(cardIndex)) { focusCard(found); yield true; }
                }
                requestFocus(backButton);
                yield true;
            }
            case DOWN -> {
                if (row(cardIndex) < TeamEditionBugemonGrid.CARD_ROWS - 1) {
                    int found = validCard(cardIndex + 1, cards);
                    if (found >= 0 && col(found) == col(cardIndex)) { focusCard(found); yield true; }
                }
                yield false;
            }
            default -> false;
        };
    }

    boolean handleSlotNavigation(KeyCode code, int slotIndex) {
        if (code == null) return false;
        return switch (code) {
            case LEFT  -> { if (slotIndex > 0) { focusSlot(slotIndex - 1); yield true; } yield false; }
            case RIGHT -> {
                if (slotIndex + 1 < slotPanes.size()) { focusSlot(slotIndex + 1); yield true; }
                yield focusFirstAvailableCard();
            }
            case UP, DOWN -> focusFirstAvailableCard();
            default -> false;
        };
    }

    void focusAvailableCard(int index) { focusCard(index); }

    void focusSlot(int index) {
        if (index < 0 || index >= slotPanes.size()) return;
        requestFocus(slotPanes.get(index));
    }

    private void focusFirstAvailableCardOrSlot() {
        if (!focusFirstAvailableCard()) focusSlot(0);
    }

    private boolean focusFirstAvailableCard() {
        List<TeamEditionView.BugemonCardData> bugemons = getAvailableBugemons.get();
        for (int i = 0; i < bugemons.size(); i++) {
            if (bugemons.get(i).accessible()) { focusCard(i); return true; }
        }
        return false;
    }

    private void focusCard(int index) {
        List<StackPane> cards = getCards.get();
        if (index < 0 || index >= cards.size()) return;
        requestFocus(cards.get(index));
    }

    private void handleSlotKeyPressed(KeyEvent e, int slotIndex) {
        if (e.getCode() == KeyCode.ENTER) {
            long now = System.currentTimeMillis();
            boolean isDouble = (now - lastSlotEnterTime) <= DOUBLE_PRESS_MS;
            lastSlotEnterTime = now;
            notify(l -> { l.onTeamSlotSelected(slotIndex); if (isDouble) l.onRemoveBugemonRequested(); });
            e.consume();
        } else if (e.getCode() == KeyCode.SPACE) {
            notify(l -> l.onTeamSlotSelected(slotIndex));
            e.consume();
        } else if (handleSlotNavigation(e.getCode(), slotIndex)) {
            e.consume();
        }
    }

    private void notify(java.util.function.Consumer<TeamEditionView.Listener> action) {
        TeamEditionView.Listener l = listenerProvider.get();
        if (l != null) action.accept(l);
    }

    private void requestFocus(Node node) {
        if (node == null) return;
        Platform.runLater(() -> {
            node.requestFocus();
            ScrollUtils.ensureVisible(availableScrollPane, availableContainer, node);
        });
    }

    private int validCard(int index, List<StackPane> cards) {
        return (index >= 0 && index < cards.size()) ? index : -1;
    }

    private int row(int index) { return Math.max(0, index) % TeamEditionBugemonGrid.CARD_ROWS; }
    private int col(int index) { return Math.max(0, index) / TeamEditionBugemonGrid.CARD_ROWS; }
}

