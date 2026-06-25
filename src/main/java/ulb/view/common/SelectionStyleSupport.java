package ulb.view.common;

import javafx.scene.Node;

/**
 * Utility class for applying mutually exclusive CSS style classes to list-like
 * items based on their selection and focus state.
 *
 * <p>Each item can be in one of three states:
 * <ul>
 *   <li><b>selected</b> — {@code index == selectedIndex}</li>
 *   <li><b>focused-but-not-selected</b> — the node has keyboard focus</li>
 *   <li><b>base</b> — neither selected nor focused</li>
 * </ul>
 *
 * <p>Call {@link #applyStyle} whenever the selection changes or a focus event
 * occurs to keep styles consistent. Use {@code selectedIndex = -1} to express
 * "nothing selected".
 *
 * <p>Current call-sites: {@code TeamEditionView} (team slots),
 * {@code TeamSelectionView} (team rows).
 */
public final class SelectionStyleSupport {

    private SelectionStyleSupport() {}

    /**
     * Applies exactly one of {@link SelectionStyle#selected()},
     * {@link SelectionStyle#focused()}, or {@link SelectionStyle#base()} to
     * {@code node}, removing the other two first.
     *
     * @param node          the node to style; ignored if {@code null}
     * @param index         zero-based index of this node in its list
     * @param selectedIndex zero-based index of the currently selected item;
     *                      use {@code -1} for "nothing selected"
     * @param style         the CSS class triplet to use
     */
    public static void applyStyle(Node node, int index, int selectedIndex, SelectionStyle style) {
        if (node == null) {
            return;
        }

        node.getStyleClass().removeAll(style.base(), style.selected(), style.focused());
        if (index == selectedIndex) {
            node.getStyleClass().add(style.selected());
            return;
        }

        node.getStyleClass().add(node.isFocused() ? style.focused() : style.base());
    }
}
