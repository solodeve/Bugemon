package ulb.view.battle.animation;

import javafx.animation.Animation;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

/**
 * Elemental animation for MECHA attacks.
 */
class MechaAnimation extends ElementAnimation {

    private static final Color[] PALETTE = {
        Color.web("#cfd8dc"), Color.web("#90a4ae"),
        Color.web("#607d8b"), Color.web("#26c6da"), Color.web("#00e5ff")
    };

    private static final int BOOTUP_RAIL_COUNT = 8;
    private static final int ALLOY_BOLT_COUNT = 9;
    private static final int HEAVY_BOLT_COUNT = 3;
    private static final int FOLLOWER_PART_COUNT = 7;
    private static final int EMP_ARC_COUNT = 6;
    private static final int SPARK_COUNT = 14;

    private static final double RETICLE_RADIUS = 32;
    private static final double RETICLE_STROKE_WIDTH = 2.2;
    private static final double RETICLE_GLOW_RADIUS = 10;
    private static final double RETICLE_START_SCALE = 1.9;
    private static final double RETICLE_END_SCALE = 0.65;
    private static final double RETICLE_PEAK_OPACITY = 0.8;
    private static final double RETICLE_PEAK_MS = 90;
    private static final double RETICLE_END_MS = 250;

    private static final double BOOTUP_RAIL_WIDTH_BASE = 30;
    private static final double BOOTUP_RAIL_WIDTH_VARIANCE = 20;
    private static final double BOOTUP_RAIL_HEIGHT = 2.5;
    private static final double BOOTUP_RAIL_ANGLE_STEP_DEG = 45.0;
    private static final double BOOTUP_RAIL_Y_OFFSET = 1.25;
    private static final double BOOTUP_RAIL_GLOW_RADIUS = 9;
    private static final double BOOTUP_RAIL_START_SCALE = 0.25;
    private static final double BOOTUP_RAIL_PEAK_SCALE = 1.0;
    private static final double BOOTUP_RAIL_END_SCALE = 1.6;
    private static final double BOOTUP_RAIL_PEAK_OPACITY = 0.85;
    private static final double BOOTUP_RAIL_PEAK_MS = 90;
    private static final double BOOTUP_RAIL_END_MS = 255;

    private static final double HEAVY_BOLT_BASE_SIZE = 9;
    private static final double HEAVY_BOLT_SIZE_VARIANCE = 3;
    private static final double LIGHT_BOLT_BASE_SIZE = 5;
    private static final double LIGHT_BOLT_SIZE_VARIANCE = 2.5;
    private static final double BOLT_SPREAD_ANGLE_DEG = 35;
    private static final double HEAVY_BOLT_GLOW_RADIUS = 12;
    private static final double HEAVY_BOLT_CENTER_INDEX = 1.0;
    private static final double HEAVY_BOLT_SPREAD_STEP = 28;
    private static final double LIGHT_BOLT_SPREAD = 52;
    private static final double HEAVY_BOLT_DURATION = 430;
    private static final double LIGHT_BOLT_DURATION_BASE = 360;
    private static final double LIGHT_BOLT_DURATION_VARIANCE = 100;
    private static final double HEAVY_BOLT_DELAY_BASE = 135;
    private static final double HEAVY_BOLT_DELAY_STEP = 35;
    private static final double LIGHT_BOLT_DELAY_BASE = 175;
    private static final double LIGHT_BOLT_DELAY_VARIANCE = 90;
    private static final double HEAVY_BOLT_SPIN_BASE = 220;
    private static final double HEAVY_BOLT_SPIN_VARIANCE = 130;
    private static final double LIGHT_BOLT_SPIN_BASE = 300;
    private static final double LIGHT_BOLT_SPIN_VARIANCE = 240;

    private static final double CORE_ORB_RADIUS = 12;
    private static final double CORE_ORB_ALPHA = 0.92;
    private static final double CORE_ORB_GLOW_RADIUS = 18;
    private static final double CORE_ORB_START_SCALE = 0.6;
    private static final double CORE_ORB_END_SCALE = 1.4;
    private static final double CORE_ORB_START_MS = 170;
    private static final double CORE_ORB_APPEAR_MS = 215;
    private static final double CORE_ORB_TRAVEL_MS = 585;
    private static final double CORE_ORB_END_OPACITY = 0.9;
    private static final double CORE_ORB_FADE_MS = 640;

    private static final double FOLLOWER_PART_BASE_SIZE = 4;
    private static final double FOLLOWER_PART_SIZE_VARIANCE = 3;
    private static final double FOLLOWER_PART_WIDTH_PAD = 1.5;
    private static final double FOLLOWER_PART_HEIGHT_MIN = 1.8;
    private static final double FOLLOWER_PART_HEIGHT_FACTOR = 0.55;
    private static final double FOLLOWER_PART_ARC = 2.2;
    private static final double FOLLOWER_PART_ROTATION_DEG = 360;
    private static final double FOLLOWER_PART_GLOW_RADIUS = 7;
    private static final double FOLLOWER_PART_START_BASE = 235;
    private static final double FOLLOWER_PART_START_STEP = 26.0;
    private static final double FOLLOWER_PART_TRAIL_BASE = 0.75;
    private static final double FOLLOWER_PART_TRAIL_STEP = 0.035;
    private static final double FOLLOWER_PART_DRIFT = 22;
    private static final double FOLLOWER_PART_SPIN_BASE = 180;
    private static final double FOLLOWER_PART_SPIN_VARIANCE = 220;
    private static final double FOLLOWER_PART_START_SCALE = 0.6;
    private static final double FOLLOWER_PART_FADE_IN_MS = 38;
    private static final double FOLLOWER_PART_PEAK_OPACITY = 0.95;
    private static final double FOLLOWER_PART_MID_MS = 300;
    private static final double FOLLOWER_PART_MID_OPACITY = 0.75;
    private static final double FOLLOWER_PART_END_MS = 360;
    private static final double FOLLOWER_PART_END_SCALE = 0.85;
    private static final double FOLLOWER_PART_END_DRIFT_FACTOR = 0.4;

    private static final double EMP_RING_RADIUS = 20;
    private static final double EMP_RING_STROKE_WIDTH = 4.2;
    private static final double EMP_RING_GLOW_RADIUS = 14;
    private static final double EMP_RING_START_SCALE = 0.25;
    private static final double EMP_RING_PEAK_SCALE = 4.1;
    private static final double EMP_RING_PEAK_OPACITY = 0.88;
    private static final double EMP_RING_START_MS = 600;
    private static final double EMP_RING_PEAK_MS = 700;
    private static final double EMP_RING_END_MS = 880;

    private static final double EMP_ARC_WIDTH_BASE = 36;
    private static final double EMP_ARC_WIDTH_VARIANCE = 22;
    private static final double EMP_ARC_HEIGHT = 2.4;
    private static final double EMP_ARC_ALPHA = 0.75;
    private static final double EMP_ARC_Y_OFFSET = 1.2;
    private static final double EMP_ARC_ANGLE_STEP_DEG = 60;
    private static final double EMP_ARC_ANGLE_VARIANCE_DEG = 12;
    private static final double EMP_ARC_GLOW_RADIUS = 8;
    private static final double EMP_ARC_DELAY_BASE = 650;
    private static final double EMP_ARC_DELAY_STEP = 25.0;
    private static final double EMP_ARC_START_SCALE = 0.25;
    private static final double EMP_ARC_PEAK_SCALE = 1.25;
    private static final double EMP_ARC_END_SCALE = 1.7;
    private static final double EMP_ARC_PEAK_OPACITY = 0.9;
    private static final double EMP_ARC_PEAK_MS = 45;
    private static final double EMP_ARC_END_MS = 95;

    private static final double SPARK_WIDTH_BASE = 4;
    private static final double SPARK_WIDTH_VARIANCE = 5;
    private static final double SPARK_HEIGHT = 1.8;
    private static final double SPARK_ROTATION_DEG = 360;
    private static final double SPARK_GLOW_RADIUS = 6;
    private static final double SPARK_ANGLE_DEG = 360;
    private static final double SPARK_DISTANCE_BASE = 18;
    private static final double SPARK_DISTANCE_VARIANCE = 52;
    private static final double SPARK_DELAY_BASE = 625;
    private static final double SPARK_DELAY_VARIANCE = 45;
    private static final double SPARK_FADE_IN_MS = 35;
    private static final double SPARK_FADE_IN_OPACITY = 0.9;
    private static final double SPARK_END_MS = 280;

    private static final double ALLOY_BOLT_FADE_IN_MS = 45;
    private static final double ALLOY_BOLT_MID_FACTOR = 0.45;
    private static final double ALLOY_BOLT_MID_X_FACTOR = 0.55;
    private static final double ALLOY_BOLT_HEAVY_Y_OFFSET = -14;
    private static final double ALLOY_BOLT_LIGHT_Y_JITTER = 14;
    private static final double ALLOY_BOLT_MID_ROTATION_FACTOR = 0.5;
    private static final double ALLOY_BOLT_END_OPACITY = 0.92;
    private static final double ALLOY_BOLT_FADE_OUT_DELAY = 70;

    MechaAnimation(AnchorPane overlay, AnimationUtils utils) {
        super(overlay, utils);
    }

    @Override
    Animation build(double sx, double sy, double tx, double ty) {
        List<Animation> parts = new ArrayList<>();

        double endX = tx - sx;
        double endY = ty - sy;

        addTargetingReticle(sx, sy, parts);
        addBootupGrid(sx, sy, parts);
        addCoreOrb(sx, sy, endX, endY, parts);
        addFollowerParts(sx, sy, endX, endY, parts);
        addAlloyBolts(sx, sy, endX, endY, parts);
        addEmpRing(tx, ty, parts);
        addEmpArcs(tx, ty, parts);
        addSparks(tx, ty, parts);

        return new ParallelTransition(parts.toArray(new Animation[0]));
    }

    private void addTargetingReticle(double sx, double sy, List<Animation> parts) {
        Circle reticle = new Circle(RETICLE_RADIUS, Color.TRANSPARENT);
        reticle.setLayoutX(sx);
        reticle.setLayoutY(sy);
        reticle.setStroke(Color.web("#b2ebf2"));
        reticle.setStrokeWidth(RETICLE_STROKE_WIDTH);
        glow(reticle, Color.web("#00e5ff"), RETICLE_GLOW_RADIUS);
        overlay.getChildren().add(reticle);
        Timeline tl = new Timeline(
            kf(0, reticle.opacityProperty(), 0.0, reticle.scaleXProperty(), RETICLE_START_SCALE, reticle.scaleYProperty(), RETICLE_START_SCALE),
            kf(RETICLE_PEAK_MS, reticle.opacityProperty(), RETICLE_PEAK_OPACITY),
            kf(RETICLE_END_MS, reticle.opacityProperty(), 0.0, reticle.scaleXProperty(), RETICLE_END_SCALE, reticle.scaleYProperty(), RETICLE_END_SCALE)
        );
        tl.setOnFinished(e -> overlay.getChildren().remove(reticle));
        parts.add(tl);
    }

    private void addBootupGrid(double sx, double sy, List<Animation> parts) {
        for (int i = 0; i < BOOTUP_RAIL_COUNT; i++) {
            Rectangle rail = new Rectangle(BOOTUP_RAIL_WIDTH_BASE + RNG.nextDouble() * BOOTUP_RAIL_WIDTH_VARIANCE, BOOTUP_RAIL_HEIGHT, PALETTE[i % PALETTE.length]);

            double angle = i * BOOTUP_RAIL_ANGLE_STEP_DEG;

            rail.setLayoutX(sx - rail.getWidth() * HALF);
            rail.setLayoutY(sy - BOOTUP_RAIL_Y_OFFSET);
            rail.setRotate(angle);
            glow(rail, Color.web("#00e5ff"), BOOTUP_RAIL_GLOW_RADIUS);
            overlay.getChildren().add(rail);
            Timeline tl = new Timeline(
                kf(0, rail.opacityProperty(), 0.0, rail.scaleXProperty(), BOOTUP_RAIL_START_SCALE),
                kf(BOOTUP_RAIL_PEAK_MS, rail.opacityProperty(), BOOTUP_RAIL_PEAK_OPACITY, rail.scaleXProperty(), BOOTUP_RAIL_PEAK_SCALE),
                kf(BOOTUP_RAIL_END_MS, rail.opacityProperty(), 0.0, rail.scaleXProperty(), BOOTUP_RAIL_END_SCALE)
            );
            tl.setOnFinished(e -> overlay.getChildren().remove(rail));
            parts.add(tl);
        }
    }

    private void addAlloyBolts(double sx, double sy, double endX, double endY, List<Animation> parts) {
        double flyAngle = Math.toDegrees(Math.atan2(endY, endX));
        for (int i = 0; i < ALLOY_BOLT_COUNT; i++) {
            spawnAlloyBolt(i, sx, sy, endX, endY, flyAngle, parts);
        }
    }

    private void addCoreOrb(double sx, double sy, double endX, double endY, List<Animation> parts) {
        Circle orb = attach(circle(CORE_ORB_RADIUS, Color.web("#26c6da", CORE_ORB_ALPHA)));
        orb.setLayoutX(sx);
        orb.setLayoutY(sy);
        glow(orb, Color.web("#00e5ff"), CORE_ORB_GLOW_RADIUS);
        timeline(orb, parts,
            kf(CORE_ORB_START_MS, orb.opacityProperty(), 0.0, orb.translateXProperty(), 0.0, orb.translateYProperty(), 0.0, orb.scaleXProperty(), CORE_ORB_START_SCALE, orb.scaleYProperty(), CORE_ORB_START_SCALE),
            kf(CORE_ORB_APPEAR_MS, orb.opacityProperty(), 1.0, orb.scaleXProperty(), 1.0, orb.scaleYProperty(), 1.0),
            kf(CORE_ORB_TRAVEL_MS, orb.translateXProperty(), endX, orb.translateYProperty(), endY, orb.opacityProperty(), CORE_ORB_END_OPACITY),
            kf(CORE_ORB_FADE_MS, orb.opacityProperty(), 0.0, orb.scaleXProperty(), CORE_ORB_END_SCALE, orb.scaleYProperty(), CORE_ORB_END_SCALE));
    }

    private void addFollowerParts(double sx, double sy, double endX, double endY, List<Animation> parts) {
        for (int i = 0; i < FOLLOWER_PART_COUNT; i++) {
            spawnFollowerPart(i, sx, sy, endX, endY, parts);
        }
    }

    private void addEmpRing(double tx, double ty, List<Animation> parts) {
        Circle ring = new Circle(EMP_RING_RADIUS, Color.TRANSPARENT);
        ring.setLayoutX(tx);
        ring.setLayoutY(ty);
        ring.setStroke(Color.web("#26c6da"));
        ring.setStrokeWidth(EMP_RING_STROKE_WIDTH);
        glow(ring, Color.web("#00e5ff"), EMP_RING_GLOW_RADIUS);
        overlay.getChildren().add(ring);
        Timeline tl = new Timeline(
            kf(EMP_RING_START_MS, ring.opacityProperty(), 0.0, ring.scaleXProperty(), EMP_RING_START_SCALE, ring.scaleYProperty(), EMP_RING_START_SCALE),
            kf(EMP_RING_PEAK_MS, ring.opacityProperty(), EMP_RING_PEAK_OPACITY, ring.scaleXProperty(), EMP_RING_PEAK_SCALE, ring.scaleYProperty(), EMP_RING_PEAK_SCALE),
            kf(EMP_RING_END_MS, ring.opacityProperty(), 0.0)
        );
        tl.setOnFinished(e -> overlay.getChildren().remove(ring));
        parts.add(tl);
    }

    private void addEmpArcs(double tx, double ty, List<Animation> parts) {
        for (int i = 0; i < EMP_ARC_COUNT; i++) {
            spawnEmpArc(i, tx, ty, parts);
        }
    }

    private void addSparks(double tx, double ty, List<Animation> parts) {
        for (int i = 0; i < SPARK_COUNT; i++) {
            spawnSpark(i, tx, ty, parts);
        }
    }

    private void spawnAlloyBolt(int i, double sx, double sy, double endX, double endY, double flyAngle, List<Animation> parts) {
        boolean heavy = i < HEAVY_BOLT_COUNT;
        Polygon bolt = attach(crystalShard(heavy ? HEAVY_BOLT_BASE_SIZE + RNG.nextDouble() * HEAVY_BOLT_SIZE_VARIANCE : LIGHT_BOLT_BASE_SIZE + RNG.nextDouble() * LIGHT_BOLT_SIZE_VARIANCE));
        bolt.setFill(PALETTE[i % PALETTE.length]);
        bolt.setLayoutX(sx);
        bolt.setLayoutY(sy);
        bolt.setRotate(flyAngle + (heavy ? 0 : centered(BOLT_SPREAD_ANGLE_DEG)));
        if (heavy) glow(bolt, Color.web("#26c6da"), HEAVY_BOLT_GLOW_RADIUS);

        double ex = endX + (heavy ? (i - HEAVY_BOLT_CENTER_INDEX) * HEAVY_BOLT_SPREAD_STEP : centered(LIGHT_BOLT_SPREAD));
        double dur = heavy ? HEAVY_BOLT_DURATION : LIGHT_BOLT_DURATION_BASE + RNG.nextDouble() * LIGHT_BOLT_DURATION_VARIANCE;
        double delay = heavy ? HEAVY_BOLT_DELAY_BASE + i * HEAVY_BOLT_DELAY_STEP : LIGHT_BOLT_DELAY_BASE + RNG.nextDouble() * LIGHT_BOLT_DELAY_VARIANCE;
        double spin = heavy ? HEAVY_BOLT_SPIN_BASE + RNG.nextDouble() * HEAVY_BOLT_SPIN_VARIANCE : LIGHT_BOLT_SPIN_BASE + RNG.nextDouble() * LIGHT_BOLT_SPIN_VARIANCE;

        animateAlloyBolt(parts, bolt, heavy, endY, ex, dur, delay, spin);
    }

    private void spawnFollowerPart(int i, double sx, double sy, double endX, double endY, List<Animation> parts) {
        double size = FOLLOWER_PART_BASE_SIZE + RNG.nextDouble() * FOLLOWER_PART_SIZE_VARIANCE;
        Rectangle part = attach(new Rectangle(size + FOLLOWER_PART_WIDTH_PAD, Math.max(FOLLOWER_PART_HEIGHT_MIN, size * FOLLOWER_PART_HEIGHT_FACTOR), PALETTE[(i + 1) % PALETTE.length]));
        part.setArcWidth(FOLLOWER_PART_ARC);
        part.setArcHeight(FOLLOWER_PART_ARC);
        part.setLayoutX(sx - part.getWidth() * HALF);
        part.setLayoutY(sy - part.getHeight() * HALF);
        part.setRotate(RNG.nextDouble() * FOLLOWER_PART_ROTATION_DEG);
        if (i % 2 == 0) glow(part, Color.web("#80deea"), FOLLOWER_PART_GLOW_RADIUS);

        double start = FOLLOWER_PART_START_BASE + i * FOLLOWER_PART_START_STEP;
        double trail = FOLLOWER_PART_TRAIL_BASE + i * FOLLOWER_PART_TRAIL_STEP;
        double driftX = centered(FOLLOWER_PART_DRIFT);
        double driftY = centered(FOLLOWER_PART_DRIFT);
        double spin = FOLLOWER_PART_SPIN_BASE + RNG.nextDouble() * FOLLOWER_PART_SPIN_VARIANCE;

        animateFollowerPart(parts, part, start, endX, endY, trail, driftX, driftY, spin);
    }

    private void spawnEmpArc(int i, double tx, double ty, List<Animation> parts) {
        Rectangle arc = attach(new Rectangle(EMP_ARC_WIDTH_BASE + RNG.nextDouble() * EMP_ARC_WIDTH_VARIANCE, EMP_ARC_HEIGHT, Color.web("#00e5ff", EMP_ARC_ALPHA)));
        arc.setLayoutX(tx - arc.getWidth() * HALF);
        arc.setLayoutY(ty - EMP_ARC_Y_OFFSET);
        arc.setRotate(i * EMP_ARC_ANGLE_STEP_DEG + RNG.nextDouble() * EMP_ARC_ANGLE_VARIANCE_DEG);
        glow(arc, Color.web("#80deea"), EMP_ARC_GLOW_RADIUS);

        double delay = EMP_ARC_DELAY_BASE + i * EMP_ARC_DELAY_STEP;

        timeline(arc, parts, kf(delay, arc.opacityProperty(), 0.0, arc.scaleXProperty(), EMP_ARC_START_SCALE),
            kf(delay + EMP_ARC_PEAK_MS, arc.opacityProperty(), EMP_ARC_PEAK_OPACITY, arc.scaleXProperty(), EMP_ARC_PEAK_SCALE),
            kf(delay + EMP_ARC_END_MS, arc.opacityProperty(), 0.0, arc.scaleXProperty(), EMP_ARC_END_SCALE));
    }

    private void spawnSpark(int i, double tx, double ty, List<Animation> parts) {
        Rectangle spark = attach(new Rectangle(SPARK_WIDTH_BASE + RNG.nextDouble() * SPARK_WIDTH_VARIANCE, SPARK_HEIGHT, PALETTE[i % PALETTE.length]));
        spark.setLayoutX(tx);
        spark.setLayoutY(ty);
        spark.setRotate(RNG.nextDouble() * SPARK_ROTATION_DEG);
        glow(spark, Color.web("#b2ebf2"), SPARK_GLOW_RADIUS);

        double a = Math.toRadians(RNG.nextDouble() * SPARK_ANGLE_DEG);
        double d = SPARK_DISTANCE_BASE + RNG.nextDouble() * SPARK_DISTANCE_VARIANCE;
        double delay = SPARK_DELAY_BASE + RNG.nextDouble() * SPARK_DELAY_VARIANCE;

        timeline(spark, parts, kf(delay, spark.opacityProperty(), 0.0, spark.translateXProperty(), 0.0, spark.translateYProperty(), 0.0),
            kf(delay + SPARK_FADE_IN_MS, spark.opacityProperty(), SPARK_FADE_IN_OPACITY),
            kf(delay + SPARK_END_MS, spark.opacityProperty(), 0.0, spark.translateXProperty(), Math.cos(a) * d, spark.translateYProperty(), Math.sin(a) * d));
    }

    private double centered(double amplitude) {
        return (RNG.nextDouble() - HALF) * amplitude;
    }

    private void animateAlloyBolt(List<Animation> parts, Polygon bolt, boolean heavy, double endY, double ex, double dur, double delay, double spin) {
        timeline(bolt, parts, kf(delay, bolt.opacityProperty(), 0.0, bolt.translateXProperty(), 0.0, bolt.translateYProperty(), 0.0, bolt.rotateProperty(), bolt.getRotate()),
            kf(delay + ALLOY_BOLT_FADE_IN_MS, bolt.opacityProperty(), 1.0),
            kf(delay + dur * ALLOY_BOLT_MID_FACTOR, bolt.translateXProperty(), ex * ALLOY_BOLT_MID_X_FACTOR, bolt.translateYProperty(), endY * ALLOY_BOLT_MID_FACTOR + (heavy ? ALLOY_BOLT_HEAVY_Y_OFFSET : centered(ALLOY_BOLT_LIGHT_Y_JITTER)),
                bolt.rotateProperty(), bolt.getRotate() + spin * ALLOY_BOLT_MID_ROTATION_FACTOR),
            kf(delay + dur, bolt.translateXProperty(), ex, bolt.translateYProperty(), endY, bolt.opacityProperty(), ALLOY_BOLT_END_OPACITY, bolt.rotateProperty(), bolt.getRotate() + spin),
            kf(delay + dur + ALLOY_BOLT_FADE_OUT_DELAY, bolt.opacityProperty(), 0.0));
    }

    private void animateFollowerPart(List<Animation> parts, Rectangle part, double start, double endX, double endY, double trail, double driftX, double driftY, double spin) {
        timeline(part, parts, kf(start, part.opacityProperty(), 0.0, part.translateXProperty(), 0.0, part.translateYProperty(), 0.0, part.scaleXProperty(), FOLLOWER_PART_START_SCALE, part.scaleYProperty(), FOLLOWER_PART_START_SCALE,
                part.rotateProperty(), part.getRotate()),
            kf(start + FOLLOWER_PART_FADE_IN_MS, part.opacityProperty(), FOLLOWER_PART_PEAK_OPACITY),
            kf(start + FOLLOWER_PART_MID_MS, part.translateXProperty(), endX * trail + driftX, part.translateYProperty(), endY * trail + driftY, part.opacityProperty(), FOLLOWER_PART_MID_OPACITY,
                part.rotateProperty(), part.getRotate() + spin),
            kf(start + FOLLOWER_PART_END_MS, part.translateXProperty(), endX + driftX * FOLLOWER_PART_END_DRIFT_FACTOR, part.translateYProperty(), endY + driftY * FOLLOWER_PART_END_DRIFT_FACTOR,
                part.opacityProperty(), 0.0, part.scaleXProperty(), FOLLOWER_PART_END_SCALE, part.scaleYProperty(), FOLLOWER_PART_END_SCALE));
    }
}
