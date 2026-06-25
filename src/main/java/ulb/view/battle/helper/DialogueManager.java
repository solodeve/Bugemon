package ulb.view.battle.helper;

import javafx.scene.control.Label;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Typed-text dialogue queue for the battle screen.
 *
 * <p>Messages are revealed character-by-character and advance on each space press.
 * Pressing space mid-animation completes the current message instantly rather than
 * skipping to the next one. Once the queue drains, an optional callback fires so the
 * controller can resume the turn.
 */
public class DialogueManager {

    private record QueuedMessage(String text, String color, Runnable onStartDisplay, Runnable onDisplayed) {}

    private final Label currentMessageLabel;
    private Runnable onQueueEmpty;

    private final Queue<QueuedMessage> messageQueue = new LinkedList<>();
    private Timeline activeTimeline;
    private QueuedMessage currentMessage;
    private boolean currentMessageFullyDisplayed = true;
    private boolean queueEmptyNotified = true;

    /**
     * @param currentMessageLabel label to animate; {@code null} is accepted in headless
     *                            test contexts — the queue still drains and callbacks fire
     */
    public DialogueManager(Label currentMessageLabel) {
        this.currentMessageLabel = currentMessageLabel;
    }

    public void setOnQueueEmpty(Runnable onQueueEmpty) {
        this.onQueueEmpty = onQueueEmpty;
    }

    /**
     * Enqueues a message with optional before/after callbacks.
     *
     * @param mustAppearAlone when {@code true}, pending messages are discarded so this one
     *                        plays next — used for turn-ending lines ("X fainted")
     */
    public void addMessage(String message, String color, Runnable onStartDisplay, Runnable onDisplayed,
                           boolean autoDisplay, boolean mustAppearAlone) {
        if (mustAppearAlone) {
            messageQueue.clear();
        }
        messageQueue.add(new QueuedMessage(message, color, onStartDisplay, onDisplayed));
        queueEmptyNotified = false;
        if (messageQueue.size() == 1 && autoDisplay && currentMessageFullyDisplayed) {
            displayNextMessage();
        }
    }

    /** Convenience overload with {@code mustAppearAlone = false}. */
    public void addMessage(String message, String color, Runnable onStartDisplay, Runnable onDisplayed,
                           boolean autoDisplay) {
        addMessage(message, color, onStartDisplay, onDisplayed, autoDisplay, false);
    }

    /** Completes the current animation if still running, otherwise advances to the next message. */
    public void showNextMessage() {
        if (!currentMessageFullyDisplayed) {
            completeCurrentMessage();
            return;
        }
        displayNextMessage();
    }

    public boolean isMessageQueueEmpty() {
        return messageQueue.isEmpty() && currentMessageFullyDisplayed;
    }

    private void displayNextMessage() {
        QueuedMessage msg = messageQueue.poll();
        if (msg == null) {
            notifyQueueEmpty();
            return;
        }
        currentMessage = msg;
        currentMessageFullyDisplayed = false;
        if (currentMessageLabel != null) {
            currentMessageLabel.setText("");
            currentMessageLabel.setStyle(
                    "-fx-font-size: 16px; " +
                            "-fx-font-weight: bold; " +
                            "-fx-text-fill: " + msg.color() + ";"
            );
            currentMessageLabel.setVisible(true);
            currentMessageLabel.setManaged(true);
            if (currentMessage.onStartDisplay() != null) {
                currentMessage.onStartDisplay().run();
            }
            animateText();
            return;
        }

        currentMessageFullyDisplayed = true;
        if (currentMessage.onStartDisplay() != null) {
            currentMessage.onStartDisplay().run();
        }
        if (currentMessage.onDisplayed() != null) {
            currentMessage.onDisplayed().run();
        }
        if (messageQueue.isEmpty()) {
            notifyQueueEmpty();
        }
    }

    private void animateText() {
        if (currentMessage == null) {
            notifyQueueEmpty();
            return;
        }

        String text = currentMessage.text();
        final int[] i = {0};
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(25), e -> {
                    if (i[0] > text.length()) {
                        completeCurrentMessage();
                    } else {
                        currentMessageLabel.setText(text.substring(0, i[0]));
                        i[0]++;
                    }
                })
        );
        activeTimeline = timeline;
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void completeCurrentMessage() {
        if (currentMessage == null || currentMessageFullyDisplayed) {
            return;
        }

        if (activeTimeline != null) {
            activeTimeline.stop();
            activeTimeline = null;
        }

        if (currentMessageLabel != null) {
            currentMessageLabel.setText(currentMessage.text());
        }

        currentMessageFullyDisplayed = true;
        if (currentMessage.onDisplayed() != null) {
            currentMessage.onDisplayed().run();
        }

        if (messageQueue.isEmpty()) {
            notifyQueueEmpty();
        }
    }

    private void notifyQueueEmpty() {
        if (onQueueEmpty != null && messageQueue.isEmpty() && currentMessageFullyDisplayed && !queueEmptyNotified) {
            queueEmptyNotified = true;
            onQueueEmpty.run();
        }
    }
}
