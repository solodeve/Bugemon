package ulb.view.battle.animation;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Factories for JavaFX shapes and animation helpers shared across all
 * {@link ElementAnimation} implementations.
 */
public class AnimationUtils {

    private static final double LEAF_TOP_SCALE = 1.6;
    private static final double LEAF_SIDE_X_SCALE = 0.6;
    private static final double LEAF_BOTTOM_SCALE = 0.8;

    private static final int STAR_POINT_COUNT = 8;
    private static final double STAR_ANGLE_STEP_DEG = 45.0;
    private static final double STAR_START_ANGLE_DEG = -90.0;
    private static final int STAR_PARITY_MOD = 2;

    private static final double SHARD_TIP_Y_SCALE = 1.8;
    private static final double SHARD_TOP_X_SCALE = 0.4;
    private static final double SHARD_TOP_Y_SCALE = 0.4;
    private static final double SHARD_MID_X_SCALE = 0.3;
    private static final double SHARD_MID_Y_SCALE = 1.2;
    private static final double SHARD_INNER_Y_SCALE = 0.6;

    private static final double GLOW_SPREAD = 0.45;

    private static final int KEYFRAME_PAIR_STRIDE = 2;
    private static final int KEYFRAME_VALUE_OFFSET = 1;

    private final Random rng = new Random();

    public AnimationUtils() {}

    Random rng() {
        return rng;
    }

    public Circle circle(double radius, Color fill) {
        return new Circle(radius, fill);
    }

    public Polygon leafPolygon(double s) {
        return new Polygon(
            0, -s * LEAF_TOP_SCALE,
            s * LEAF_SIDE_X_SCALE, 0,
            0, s * LEAF_BOTTOM_SCALE,
            -s * LEAF_SIDE_X_SCALE, 0
        );
    }

    public Polygon starPolygon(double outer, double inner) {
        Polygon p = new Polygon();
        for (int i = 0; i < STAR_POINT_COUNT; i++) {
            double a = Math.toRadians(i * STAR_ANGLE_STEP_DEG + STAR_START_ANGLE_DEG);
            double r = (i % STAR_PARITY_MOD == 0) ? outer : inner;
            p.getPoints().addAll(Math.cos(a) * r, Math.sin(a) * r);
        }
        return p;
    }

    public Polygon crystalShard(double s) {
        return new Polygon(
             0,        -s * SHARD_TIP_Y_SCALE,
             s * SHARD_TOP_X_SCALE,  -s * SHARD_TOP_Y_SCALE,
             s * SHARD_MID_X_SCALE,   s * SHARD_MID_Y_SCALE,
             0,         s * SHARD_INNER_Y_SCALE,
            -s * SHARD_MID_X_SCALE,   s * SHARD_MID_Y_SCALE,
            -s * SHARD_TOP_X_SCALE,  -s * SHARD_TOP_Y_SCALE
        );
    }

    public void glow(Node node, Color color, double radius) {
        DropShadow ds = new DropShadow();
        ds.setColor(color);
        ds.setRadius(radius);
        ds.setSpread(GLOW_SPREAD);
        node.setEffect(ds);
    }

    /**
     * Builds a {@link KeyFrame} at {@code ms} milliseconds with one {@link KeyValue} per
     * property/value pair in {@code pairs}.
     *
     * <p>{@code pairs} must be an even-length sequence of alternating
     * {@code WritableValue<Number>} and {@code Number} arguments:
     * <pre>{@code
     * kf(300, node.opacityProperty(), 1.0, node.translateXProperty(), 50.0)
     * }</pre>
     * All key values use {@link javafx.animation.Interpolator#EASE_BOTH}.
     *
     * @throws ClassCastException if the elements of {@code pairs} are not of the expected types
     */
    @SuppressWarnings("unchecked")
    public KeyFrame kf(double ms, Object... pairs) {
        List<KeyValue> kvs = new ArrayList<>();
        for (int i = 0; i < pairs.length; i += KEYFRAME_PAIR_STRIDE) {
            javafx.beans.value.WritableValue<Number> prop =
                (javafx.beans.value.WritableValue<Number>) pairs[i];
            Number val = (Number) pairs[i + KEYFRAME_VALUE_OFFSET];
            kvs.add(new KeyValue(prop, val, Interpolator.EASE_BOTH));
        }
        return new KeyFrame(Duration.millis(ms), kvs.toArray(new KeyValue[0]));
    }
}
