package ulb.view.battle.animation;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

import java.util.List;
import java.util.Random;

/**
 * Base class for per-element battle attack animations.
 *
 * <p>Each subclass creates JavaFX nodes, adds them to {@link #overlay}, animates them,
 * and removes them on completion. Nodes are never left in the overlay after the
 * returned {@link javafx.animation.Animation} finishes.
 */
abstract class ElementAnimation {

    protected static final double HALF = 0.5;

    protected final AnchorPane overlay;
    protected final AnimationUtils utils;
    protected final Random RNG;

    ElementAnimation(AnchorPane overlay, AnimationUtils utils) {
        this.overlay = overlay;
        this.utils = utils;
        this.RNG = utils.rng();
    }

    /**
     * Builds the animation for a single attack traveling from source to target.
     *
     * <p>All four parameters are absolute coordinates <em>in the overlay's coordinate
     * space</em> (i.e. in the parent {@link AnchorPane}). The animation starts at
     * ({@code sx}, {@code sy}) and ends at ({@code tx}, {@code ty}).
     *
     * <p>The returned animation is not yet playing; the caller is responsible for
     * attaching an {@code onFinished} handler and calling {@code play()}.
     *
     * @param sx source X — center of the attacking sprite
     * @param sy source Y — center of the attacking sprite
     * @param tx target X — center of the defending sprite
     * @param ty target Y — center of the defending sprite
     */
    abstract Animation build(double sx, double sy, double tx, double ty);

    protected Circle circle(double radius, Color fill) {
        return utils.circle(radius, fill);
    }

    protected Polygon leafPolygon(double s) {
        return utils.leafPolygon(s);
    }

    protected Polygon starPolygon(double outer, double inner) {
        return utils.starPolygon(outer, inner);
    }

    protected Polygon crystalShard(double s) {
        return utils.crystalShard(s);
    }

    protected void glow(Node node, Color color, double radius) {
        utils.glow(node, color, radius);
    }

    protected KeyFrame kf(double ms, Object... pairs) {
        return utils.kf(ms, pairs);
    }

    protected <T extends Node> T attach(T node) {
        overlay.getChildren().add(node);
        return node;
    }

    protected void track(Animation animation, Node node, List<Animation> parts) {
        animation.setOnFinished(e -> overlay.getChildren().remove(node));
        parts.add(animation);
    }

    protected void timeline(Node node, List<Animation> parts, KeyFrame... frames) {
        track(new Timeline(frames), node, parts);
    }
}
