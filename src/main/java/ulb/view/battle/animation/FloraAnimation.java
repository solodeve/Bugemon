package ulb.view.battle.animation;

import javafx.animation.Animation;
import javafx.animation.ParallelTransition;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

import java.util.ArrayList;
import java.util.List;

/**
 * Elemental animation for PLANT attacks.
 */
class FloraAnimation extends ElementAnimation {

    private static final Color[] PALETTE = {
        Color.web("#00e676"), Color.web("#69f0ae"),
        Color.web("#2ecc71"), Color.web("#a5d6a7"), Color.web("#00c853")
    };

    private static final int CHARGE_SPARKLE_COUNT = 6;
    private static final int LEAF_COUNT = 18;
    private static final int IMPACT_RING_COUNT = 3;

    private static final double CHARGE_SPARKLE_RADIUS = 4;
    private static final double CHARGE_SPARKLE_GLOW_RADIUS = 12;
    private static final double CHARGE_SPARKLE_ANGLE_STEP_DEG = 60;
    private static final double CHARGE_SPARKLE_DISTANCE = 32;
    private static final double CHARGE_SPARKLE_END_FACTOR = 1.5;
    private static final double CHARGE_SPARKLE_MID_MS = 200;
    private static final double CHARGE_SPARKLE_END_MS = 370;
    private static final double CHARGE_SPARKLE_PEAK_OPACITY = 0.9;

    private static final double LEAF_BASE_SIZE = 6;
    private static final double LEAF_SIZE_VARIANCE = 5;
    private static final double LEAF_ROTATION_DEG = 360;
    private static final double LEAF_GLOW_RADIUS = 7;
    private static final double LEAF_DELAY_BASE = 80;
    private static final double LEAF_DELAY_STEP = 24.0;
    private static final double LEAF_CENTER_INDEX = LEAF_COUNT / 2.0;
    private static final double LEAF_SPREAD_STEP = 14;
    private static final double LEAF_WAVE_FREQUENCY = 0.6;
    private static final double LEAF_WAVE_AMPLITUDE = 30;
    private static final double LEAF_SPIN_BASE = 360;
    private static final double LEAF_SPIN_VARIANCE = 180;
    private static final double LEAF_MID_MS = 180;
    private static final double LEAF_END_MS = 460;
    private static final double LEAF_FADE_MS = 580;
    private static final double LEAF_MID_FACTOR = 0.5;
    private static final double LEAF_MID_X_JITTER = 35;
    private static final double LEAF_MID_Y_BASE = 40;
    private static final double LEAF_MID_Y_VARIANCE = 25;
    private static final double LEAF_MID_ROTATION_FACTOR = 0.4;
    private static final double LEAF_END_OPACITY = 0.9;

    private static final double IMPACT_RING_BASE_RADIUS = 12;
    private static final double IMPACT_RING_RADIUS_STEP = 7;
    private static final double IMPACT_RING_STROKE_WIDTH = 3;
    private static final double IMPACT_RING_GLOW_RADIUS = 10;
    private static final double IMPACT_RING_DELAY_BASE = 510;
    private static final double IMPACT_RING_DELAY_STEP = 55;
    private static final double IMPACT_RING_START_SCALE = 0.1;
    private static final double IMPACT_RING_PEAK_SCALE = 4.0;
    private static final double IMPACT_RING_PEAK_OPACITY = 0.9;
    private static final double IMPACT_RING_PEAK_MS = 270;
    private static final double IMPACT_RING_END_MS = 420;

    FloraAnimation(AnchorPane overlay, AnimationUtils utils) {
        super(overlay, utils);
    }

    @Override
    Animation build(double sx, double sy, double tx, double ty) {
        List<Animation> parts = new ArrayList<>();

        double endX = tx - sx;
        double endY = ty - sy;
        addChargeSparkles(sx, sy, parts);

        addLeafWave(sx, sy, endX, endY, parts);
        addImpactRings(tx, ty, parts);

        return new ParallelTransition(parts.toArray(new Animation[0]));
    }

    private void addChargeSparkles(double sx, double sy, List<Animation> parts) {
        for (int i = 0; i < CHARGE_SPARKLE_COUNT; i++) {
            spawnChargeSparkle(i, sx, sy, parts);
        }
    }

    private void addLeafWave(double sx, double sy, double endX, double endY, List<Animation> parts) {
        for (int i = 0; i < LEAF_COUNT; i++) {
            spawnLeaf(i, sx, sy, endX, endY, parts);
        }
    }

    private void addImpactRings(double tx, double ty, List<Animation> parts) {
        for (int i = 0; i < IMPACT_RING_COUNT; i++) {
            spawnImpactRing(i, tx, ty, parts);
        }
    }

    private void spawnChargeSparkle(int i, double sx, double sy, List<Animation> parts) {
        Circle spark = attach(circle(CHARGE_SPARKLE_RADIUS, PALETTE[i % PALETTE.length]));
        spark.setLayoutX(sx);
        spark.setLayoutY(sy);
        glow(spark, Color.web("#00e676"), CHARGE_SPARKLE_GLOW_RADIUS);

        double a = Math.toRadians(i * CHARGE_SPARKLE_ANGLE_STEP_DEG);
        double r = CHARGE_SPARKLE_DISTANCE;

        timeline(spark, parts, kf(0, spark.opacityProperty(), 0, spark.translateXProperty(), 0.0, spark.translateYProperty(), 0.0),
            kf(CHARGE_SPARKLE_MID_MS, spark.opacityProperty(), CHARGE_SPARKLE_PEAK_OPACITY, spark.translateXProperty(), Math.cos(a) * r, spark.translateYProperty(), Math.sin(a) * r),
            kf(CHARGE_SPARKLE_END_MS, spark.opacityProperty(), 0.0, spark.translateXProperty(), Math.cos(a) * r * CHARGE_SPARKLE_END_FACTOR,
                spark.translateYProperty(), Math.sin(a) * r * CHARGE_SPARKLE_END_FACTOR));
    }

    private void spawnLeaf(int i, double sx, double sy, double endX, double endY, List<Animation> parts) {
        Polygon leaf = attach(leafPolygon(LEAF_BASE_SIZE + RNG.nextDouble() * LEAF_SIZE_VARIANCE));
        leaf.setFill(PALETTE[i % PALETTE.length]);
        leaf.setLayoutX(sx);
        leaf.setLayoutY(sy);
        leaf.setRotate(RNG.nextDouble() * LEAF_ROTATION_DEG);
        glow(leaf, Color.web("#00e676"), LEAF_GLOW_RADIUS);

        double delay = LEAF_DELAY_BASE + i * LEAF_DELAY_STEP;
        double ex = endX + (i - LEAF_CENTER_INDEX) * LEAF_SPREAD_STEP;
        double ey = endY + Math.sin(i * LEAF_WAVE_FREQUENCY) * LEAF_WAVE_AMPLITUDE;
        double spin = LEAF_SPIN_BASE + RNG.nextDouble() * LEAF_SPIN_VARIANCE;

        timeline(leaf, parts, kf(delay, leaf.opacityProperty(), 0.0, leaf.translateXProperty(), 0.0, leaf.translateYProperty(), 0.0, leaf.rotateProperty(), leaf.getRotate()),
            kf(delay + LEAF_MID_MS, leaf.opacityProperty(), 1.0, leaf.translateXProperty(), ex * LEAF_MID_FACTOR + randomCentered(LEAF_MID_X_JITTER),
                leaf.translateYProperty(), ey * LEAF_MID_FACTOR - LEAF_MID_Y_BASE - RNG.nextDouble() * LEAF_MID_Y_VARIANCE,
                leaf.rotateProperty(), leaf.getRotate() + spin * LEAF_MID_ROTATION_FACTOR),
            kf(delay + LEAF_END_MS, leaf.opacityProperty(), LEAF_END_OPACITY, leaf.translateXProperty(), ex, leaf.translateYProperty(), ey, leaf.rotateProperty(), leaf.getRotate() + spin),
            kf(delay + LEAF_FADE_MS, leaf.opacityProperty(), 0.0));
    }

    private void spawnImpactRing(int i, double tx, double ty, List<Animation> parts) {
        Circle ring = attach(new Circle(IMPACT_RING_BASE_RADIUS + i * IMPACT_RING_RADIUS_STEP, Color.TRANSPARENT));
        ring.setLayoutX(tx);
        ring.setLayoutY(ty);
        ring.setStroke(PALETTE[i]);
        ring.setStrokeWidth(IMPACT_RING_STROKE_WIDTH);
        glow(ring, Color.web("#00e676"), IMPACT_RING_GLOW_RADIUS);
        double delay = IMPACT_RING_DELAY_BASE + i * IMPACT_RING_DELAY_STEP;
        timeline(ring, parts, kf(delay, ring.opacityProperty(), 0.0, ring.scaleXProperty(), IMPACT_RING_START_SCALE, ring.scaleYProperty(), IMPACT_RING_START_SCALE),
            kf(delay + IMPACT_RING_PEAK_MS, ring.opacityProperty(), IMPACT_RING_PEAK_OPACITY, ring.scaleXProperty(), IMPACT_RING_PEAK_SCALE, ring.scaleYProperty(), IMPACT_RING_PEAK_SCALE),
            kf(delay + IMPACT_RING_END_MS, ring.opacityProperty(), 0.0));
    }

    private double randomCentered(double amplitude) {
        return (RNG.nextDouble() - HALF) * amplitude;
    }
}
