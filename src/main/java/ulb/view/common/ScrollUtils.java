package ulb.view.common;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;

/**
 * Utility class for scrolling a {@link ScrollPane} to keep a selected or focused
 * node centred in its viewport.
 *
 * <p>Call {@link #ensureVisible} from any place where the selection changes
 * (keyboard navigation, click, data refresh). Views typically wrap the call in a
 * one-argument helper that captures their concrete scroll pane and container:
 *
 * <pre>{@code
 * private void scrollToVisible(Node node) {
 *     ScrollUtils.ensureVisible(myScrollPane, myContainer, node);
 * }
 * }</pre>
 *
 * <p>Current call-sites: {@code LeaderboardView}, {@code TeamSelectionView},
 * {@code TeamEditionView}.
 */
public final class ScrollUtils {

    private ScrollUtils() {}

    /**
     * Scrolls {@code scrollPane} so that {@code node} is centred in the viewport.
     *
     * <p>Does nothing when:
     * <ul>
     *   <li>any argument is {@code null};</li>
     *   <li>{@code node} is not a direct child of {@code container};</li>
     *   <li>the content fits entirely within the viewport;</li>
     *   <li>the viewport has not yet been laid out ({@code height <= 0}).</li>
     * </ul>
     *
     * @param scrollPane the scroll pane to adjust
     * @param container  the direct content node of the scroll pane
     * @param node       the child of {@code container} to bring into view
     */
    public static void ensureVisible(ScrollPane scrollPane, Node container, Node node) {
        if (scrollPane == null || container == null || node == null) return;
        if (!(container instanceof Parent parent)
                || !parent.getChildrenUnmodifiable().contains(node)) return;

        Bounds viewportBounds = scrollPane.getViewportBounds();
        if (viewportBounds.getHeight() <= 0
                || container.getBoundsInLocal().getHeight() <= viewportBounds.getHeight()) return;

        Bounds containerBounds = container.getBoundsInLocal();
        Bounds nodeBounds = container.sceneToLocal(node.localToScene(node.getBoundsInLocal()));
        double contentHeight = containerBounds.getHeight();
        double viewportHeight = viewportBounds.getHeight();
        double targetCenter = nodeBounds.getMinY() + (nodeBounds.getHeight() / 2.0);
        double targetValue = (targetCenter - (viewportHeight / 2.0)) / (contentHeight - viewportHeight);
        scrollPane.setVvalue(Math.clamp(targetValue, 0.0, 1.0));
    }
}
