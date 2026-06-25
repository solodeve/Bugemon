package ulb.view.battle.animation;

import javafx.animation.Animation;
import javafx.animation.ParallelTransition;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;

/**
 * Elemental animation for ICE attacks.
 */
class AquaAnimation extends ElementAnimation {

    private static final Color[] PALETTE = {
        Color.web("#0091ea"), Color.web("#00b0ff"),
        Color.web("#40c4ff"), Color.web("#80d8ff"), Color.web("#b3e5fc")
    };

    private static final int CHARGE_BUBBLE_COUNT = 5;
    private static final int TRAILING_DROPLET_COUNT = 10;
    private static final int RIPPLE_RING_COUNT = 4;
    private static final int SPLASH_DROPLET_COUNT = 12;

    private static final double WATER_ORB_RADIUS = 19;
    private static final double WATER_ORB_ALPHA = 0.85;
    private static final double WATER_ORB_GLOW_RADIUS = 24;
    private static final double WATER_ORB_START_MS = 175;
    private static final double WATER_ORB_APPEAR_MS = 240;
    private static final double WATER_ORB_MID_MS = 460;
    private static final double WATER_ORB_END_MS = 680;
    private static final double WATER_ORB_FADE_MS = 715;
    private static final double WATER_ORB_MID_FACTOR = 0.5;
    private static final double WATER_ORB_ARC_HEIGHT = 92;
    private static final double WATER_ORB_END_OPACITY = 0.9;

    private static final double CHARGE_BUBBLE_MIN_RADIUS = 4;
    private static final double CHARGE_BUBBLE_RADIUS_VARIANCE = 4;
    private static final double CHARGE_BUBBLE_ALPHA = 0.4;
    private static final double CHARGE_BUBBLE_X_JITTER = 28;
    private static final double CHARGE_BUBBLE_STROKE_WIDTH = 1.5;
    private static final double CHARGE_BUBBLE_GLOW_RADIUS = 9;
    private static final double CHARGE_BUBBLE_DELAY_STEP = 42.0;
    private static final double CHARGE_BUBBLE_FADE_IN_MS = 200;
    private static final double CHARGE_BUBBLE_FADE_OUT_MS = 360;
    private static final double CHARGE_BUBBLE_RISE_START = 32;
    private static final double CHARGE_BUBBLE_RISE_END = 52;
    private static final double CHARGE_BUBBLE_PEAK_OPACITY = 0.8;

    private static final double TRAILING_DROPLET_MIN_RADIUS = 4;
    private static final double TRAILING_DROPLET_RADIUS_VARIANCE = 4;
    private static final double TRAILING_DROPLET_GLOW_RADIUS = 5;
    private static final double TRAILING_DROPLET_FRAC_START = 0.12;
    private static final double TRAILING_DROPLET_FRAC_SPAN = 0.72;
    private static final double TRAILING_DROPLET_MID_JITTER = 18;
    private static final double TRAILING_DROPLET_END_JITTER = 12;
    private static final double TRAILING_DROPLET_DELAY_BASE = 215;
    private static final double TRAILING_DROPLET_DELAY_STEP = 22.0;
    private static final double TRAILING_DROPLET_FADE_IN_MS = 75;
    private static final double TRAILING_DROPLET_MID_MS = 210;
    private static final double TRAILING_DROPLET_END_MS = 390;
    private static final double TRAILING_DROPLET_PEAK_OPACITY = 0.75;

    private static final double RIPPLE_RING_BASE_RADIUS = 10;
    private static final double RIPPLE_RING_RADIUS_STEP = 8;
    private static final double RIPPLE_RING_STROKE_WIDTH = 2.5;
    private static final double RIPPLE_RING_GLOW_RADIUS = 8;
    private static final double RIPPLE_RING_DELAY_BASE = 590;
    private static final double RIPPLE_RING_DELAY_STEP = 45;
    private static final double RIPPLE_RING_START_SCALE = 0.2;
    private static final double RIPPLE_RING_PEAK_SCALE = 3.8;
    private static final double RIPPLE_RING_PEAK_OPACITY = 0.85;
    private static final double RIPPLE_RING_PEAK_MS = 250;
    private static final double RIPPLE_RING_END_MS = 400;

    private static final double SPLASH_DROPLET_MIN_RADIUS = 3;
    private static final double SPLASH_DROPLET_RADIUS_VARIANCE = 4;
    private static final double SPLASH_DROPLET_GLOW_RADIUS = 5;
    private static final double SPLASH_DROPLET_ANGLE_STEP_DEG = 30;
    private static final double SPLASH_DROPLET_ANGLE_VARIANCE_DEG = 20;
    private static final double SPLASH_DROPLET_DISTANCE_BASE = 28;
    private static final double SPLASH_DROPLET_DISTANCE_VARIANCE = 48;
    private static final double SPLASH_DROPLET_DROP = 18;
    private static final double SPLASH_DROPLET_DELAY_BASE = 600;
    private static final double SPLASH_DROPLET_DELAY_VARIANCE = 35;
    private static final double SPLASH_DROPLET_FADE_IN_MS = 40;
    private static final double SPLASH_DROPLET_FADE_IN_OPACITY = 0.9;
    private static final double SPLASH_DROPLET_END_MS = 270;
    private static final double SPLASH_DROPLET_END_Y_OFFSET = 24;

    AquaAnimation(AnchorPane overlay, AnimationUtils utils) {
        super(overlay, utils);
    }

    @Override
    Animation build(double sx, double sy, double tx, double ty) {
        List<Animation> parts = new ArrayList<>();

        double endX = tx - sx;
        double endY = ty - sy;

        addChargeBubbles(sx, sy, parts);
        addWaterOrb(sx, sy, endX, endY, parts);
        addTrailingDroplets(sx, sy, endX, endY, parts);
        addRippleRings(tx, ty, parts);
        addSplashDroplets(tx, ty, parts);

        return new ParallelTransition(parts.toArray(new Animation[0]));
    }

    private void addChargeBubbles(double sx, double sy, List<Animation> parts) {
        for (int i = 0; i < CHARGE_BUBBLE_COUNT; i++) {
            spawnChargeBubble(i, sx, sy, parts);
        }
    }

    private void addWaterOrb(double sx, double sy, double endX, double endY, List<Animation> parts) {
        Circle orb = attach(circle(WATER_ORB_RADIUS, Color.web("#0091ea", WATER_ORB_ALPHA)));
        orb.setLayoutX(sx);
        orb.setLayoutY(sy);
        glow(orb, Color.web("#40c4ff"), WATER_ORB_GLOW_RADIUS);
        timeline(orb, parts,
            kf(WATER_ORB_START_MS, orb.opacityProperty(), 0.0, orb.translateXProperty(), 0.0, orb.translateYProperty(), 0.0),
            kf(WATER_ORB_APPEAR_MS, orb.opacityProperty(), 1.0),
            kf(WATER_ORB_MID_MS, orb.translateXProperty(), endX * WATER_ORB_MID_FACTOR, orb.translateYProperty(), endY * WATER_ORB_MID_FACTOR - WATER_ORB_ARC_HEIGHT),
            kf(WATER_ORB_END_MS, orb.translateXProperty(), endX, orb.translateYProperty(), endY, orb.opacityProperty(), WATER_ORB_END_OPACITY),
            kf(WATER_ORB_FADE_MS, orb.opacityProperty(), 0.0));
    }

    private void addTrailingDroplets(double sx, double sy, double endX, double endY, List<Animation> parts) {
        for (int i = 0; i < TRAILING_DROPLET_COUNT; i++) {
            spawnTrailingDroplet(i, sx, sy, endX, endY, parts);
        }
    }

    private void addRippleRings(double tx, double ty, List<Animation> parts) {
        for (int i = 0; i < RIPPLE_RING_COUNT; i++) {
            spawnRippleRing(i, tx, ty, parts);
        }
    }

    private void addSplashDroplets(double tx, double ty, List<Animation> parts) {
        for (int i = 0; i < SPLASH_DROPLET_COUNT; i++) {
            spawnSplashDroplet(i, tx, ty, parts);
        }
    }

    private void spawnChargeBubble(int i, double sx, double sy, List<Animation> parts) {
        Circle bubble = attach(circle(CHARGE_BUBBLE_MIN_RADIUS + RNG.nextDouble() * CHARGE_BUBBLE_RADIUS_VARIANCE, Color.web("#0091ea", CHARGE_BUBBLE_ALPHA)));
        bubble.setLayoutX(sx + (RNG.nextDouble() - HALF) * CHARGE_BUBBLE_X_JITTER);
        bubble.setLayoutY(sy);
        bubble.setStroke(Color.web("#80d8ff"));
        bubble.setStrokeWidth(CHARGE_BUBBLE_STROKE_WIDTH);
        glow(bubble, Color.web("#40c4ff"), CHARGE_BUBBLE_GLOW_RADIUS);
        double delay = i * CHARGE_BUBBLE_DELAY_STEP;
        timeline(bubble, parts, kf(delay, bubble.opacityProperty(), 0.0, bubble.translateYProperty(), 0.0),
            kf(delay + CHARGE_BUBBLE_FADE_IN_MS, bubble.opacityProperty(), CHARGE_BUBBLE_PEAK_OPACITY, bubble.translateYProperty(), -CHARGE_BUBBLE_RISE_START),
            kf(delay + CHARGE_BUBBLE_FADE_OUT_MS, bubble.opacityProperty(), 0.0, bubble.translateYProperty(), -CHARGE_BUBBLE_RISE_END));
    }

    private void spawnTrailingDroplet(int i, double sx, double sy, double endX, double endY, List<Animation> parts) {
        Circle drop = attach(circle(TRAILING_DROPLET_MIN_RADIUS + RNG.nextDouble() * TRAILING_DROPLET_RADIUS_VARIANCE, PALETTE[i % PALETTE.length]));
        drop.setLayoutX(sx);
        drop.setLayoutY(sy);
        glow(drop, Color.web("#40c4ff"), TRAILING_DROPLET_GLOW_RADIUS);
        double frac = TRAILING_DROPLET_FRAC_START + i / (double) TRAILING_DROPLET_COUNT * TRAILING_DROPLET_FRAC_SPAN;
        double[] mid = jitter(endX * frac * WATER_ORB_MID_FACTOR, endY * frac * WATER_ORB_MID_FACTOR - WATER_ORB_ARC_HEIGHT * frac, TRAILING_DROPLET_MID_JITTER);
        double[] end = jitter(endX * frac, endY * frac, TRAILING_DROPLET_END_JITTER);
        double delay = TRAILING_DROPLET_DELAY_BASE + i * TRAILING_DROPLET_DELAY_STEP;
        timeline(drop, parts, kf(delay, drop.opacityProperty(), 0.0, drop.translateXProperty(), 0.0, drop.translateYProperty(), 0.0),
            kf(delay + TRAILING_DROPLET_FADE_IN_MS, drop.opacityProperty(), TRAILING_DROPLET_PEAK_OPACITY),
            kf(delay + TRAILING_DROPLET_MID_MS, drop.translateXProperty(), mid[0], drop.translateYProperty(), mid[1]),
            kf(delay + TRAILING_DROPLET_END_MS, drop.opacityProperty(), 0.0, drop.translateXProperty(), end[0], drop.translateYProperty(), end[1]));
    }

    private void spawnRippleRing(int i, double tx, double ty, List<Animation> parts) {
        Circle ring = attach(new Circle(RIPPLE_RING_BASE_RADIUS + i * RIPPLE_RING_RADIUS_STEP, Color.TRANSPARENT));
        ring.setLayoutX(tx);
        ring.setLayoutY(ty);
        ring.setStroke(PALETTE[i % PALETTE.length]);
        ring.setStrokeWidth(RIPPLE_RING_STROKE_WIDTH);
        glow(ring, Color.web("#40c4ff"), RIPPLE_RING_GLOW_RADIUS);
        double delay = RIPPLE_RING_DELAY_BASE + i * RIPPLE_RING_DELAY_STEP;
        timeline(ring, parts, kf(delay, ring.opacityProperty(), 0.0, ring.scaleXProperty(), RIPPLE_RING_START_SCALE, ring.scaleYProperty(), RIPPLE_RING_START_SCALE),
            kf(delay + RIPPLE_RING_PEAK_MS, ring.opacityProperty(), RIPPLE_RING_PEAK_OPACITY, ring.scaleXProperty(), RIPPLE_RING_PEAK_SCALE, ring.scaleYProperty(), RIPPLE_RING_PEAK_SCALE),
            kf(delay + RIPPLE_RING_END_MS, ring.opacityProperty(), 0.0));
    }

    private void spawnSplashDroplet(int i, double tx, double ty, List<Animation> parts) {
        Circle splash = attach(circle(SPLASH_DROPLET_MIN_RADIUS + RNG.nextDouble() * SPLASH_DROPLET_RADIUS_VARIANCE, PALETTE[i % PALETTE.length]));
        splash.setLayoutX(tx);
        splash.setLayoutY(ty);
        glow(splash, Color.web("#40c4ff"), SPLASH_DROPLET_GLOW_RADIUS);
        double a = Math.toRadians(i * SPLASH_DROPLET_ANGLE_STEP_DEG + RNG.nextDouble() * SPLASH_DROPLET_ANGLE_VARIANCE_DEG);
        double dist = SPLASH_DROPLET_DISTANCE_BASE + RNG.nextDouble() * SPLASH_DROPLET_DISTANCE_VARIANCE;
        double dx = Math.cos(a) * dist;
        double dy = Math.sin(a) * dist - SPLASH_DROPLET_DROP;
        double delay = SPLASH_DROPLET_DELAY_BASE + RNG.nextDouble() * SPLASH_DROPLET_DELAY_VARIANCE;
        timeline(splash, parts, kf(delay, splash.opacityProperty(), 0.0, splash.translateXProperty(), 0.0, splash.translateYProperty(), 0.0),
            kf(delay + SPLASH_DROPLET_FADE_IN_MS, splash.opacityProperty(), SPLASH_DROPLET_FADE_IN_OPACITY),
            kf(delay + SPLASH_DROPLET_END_MS, splash.opacityProperty(), 0.0, splash.translateXProperty(), dx, splash.translateYProperty(), dy + SPLASH_DROPLET_END_Y_OFFSET));
    }

    private double[] jitter(double x, double y, double amplitude) {
        return new double[]{x + (RNG.nextDouble() - HALF) * amplitude, y + (RNG.nextDouble() - HALF) * amplitude};
    }
}
