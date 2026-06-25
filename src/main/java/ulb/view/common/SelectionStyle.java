package ulb.view.common;

/**
 * Triplet of CSS class names for a list item that can be selected or focused.
 *
 * <p>Pass an instance to
 * {@link SelectionStyleSupport#applyStyle(javafx.scene.Node, int, int, SelectionStyle)}
 * instead of three loose strings. Define one constant per list type:
 *
 * <pre>{@code
 * private static final SelectionStyle ROW_STYLE =
 *     new SelectionStyle("team-row", "team-row-selected", "team-row-focused");
 * }</pre>
 *
 * <p>Use {@code selectedIndex = -1} to express "nothing selected".
 *
 * @param base     CSS class for the default (unselected, unfocused) state
 * @param selected CSS class for the selected state
 * @param focused  CSS class for the focused-but-not-selected state
 */
public record SelectionStyle(String base, String selected, String focused) {}
