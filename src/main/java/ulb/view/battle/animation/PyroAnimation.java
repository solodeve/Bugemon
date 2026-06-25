package ulb.view.battle.animation;

import javafx.animation.Animation;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;

/**
 * Elemental animation for FIRE attacks.
 */
class PyroAnimation extends ElementAnimation {

    private static final Color[] PALETTE = {
        Color.web("#ff1744"), Color.web("#ff6d00"),
        Color.web("#ffea00"), Color.web("#ff5722"), Color.web("#ff8f00")
    };

    private static final int TRAIL_SPARK_COUNT = 16;
    private static final int EMBER_COUNT = 20;

    private static final double FIRE_AURA_RADIUS = 18;
    private static final double FIRE_AURA_ALPHA = 0.3;
    private static final double FIRE_AURA_GLOW_RADIUS = 28;
    private static final double FIRE_AURA_START_SCALE = 0.3;
    private static final double FIRE_AURA_PEAK_SCALE = 2.6;
    private static final double FIRE_AURA_END_SCALE = 1.4;
    private static final double FIRE_AURA_PEAK_OPACITY = 0.85;
    private static final double FIRE_AURA_PEAK_MS = 110;
    private static final double FIRE_AURA_END_MS = 230;

    private static final double FIREBALL_RADIUS = 22;
    private static final double FIREBALL_GLOW_RADIUS = 32;
    private static final double FIREBALL_START_MS = 150;
    private static final double FIREBALL_APPEAR_MS = 200;
    private static final double FIREBALL_TRAVEL_MS = 570;
    private static final double FIREBALL_FADE_MS = 610;
    private static final double FIREBALL_END_OPACITY = 0.9;

    private static final double IMPACT_FLASH_RADIUS = 40;
    private static final double IMPACT_FLASH_ALPHA = 0.7;
    private static final double IMPACT_FLASH_GLOW_RADIUS = 42;
    private static final double IMPACT_FLASH_START_SCALE = 0.1;
    private static final double IMPACT_FLASH_PEAK_SCALE = 3.2;
    private static final double IMPACT_FLASH_PEAK_OPACITY = 0.9;
    private static final double IMPACT_FLASH_START_MS = 545;
    private static final double IMPACT_FLASH_PEAK_MS = 590;
    private static final double IMPACT_FLASH_END_MS = 750;

    private static final double TRAIL_SPARK_MIN_RADIUS = 4;
    private static final double TRAIL_SPARK_RADIUS_VARIANCE = 8;
    private static final double TRAIL_SPARK_GLOW_RADIUS = 10;
    private static final double TRAIL_SPARK_T_START = 0.12;
    private static final double TRAIL_SPARK_T_SPAN = 0.65;
    private static final double TRAIL_SPARK_JITTER = 38;
    private static final double TRAIL_SPARK_DELAY_BASE = 195;
    private static final double TRAIL_SPARK_DELAY_STEP = 22.0;
    private static final double TRAIL_SPARK_START_OFFSET = 8;
    private static final double TRAIL_SPARK_FADE_IN_MS = 75;
    private static final double TRAIL_SPARK_FADE_IN_OPACITY = 0.85;
    private static final double TRAIL_SPARK_FADE_OUT_MS = 290;
    private static final double TRAIL_SPARK_END_JITTER = 28;
    private static final double TRAIL_SPARK_END_Y_BASE = 20;
    private static final double TRAIL_SPARK_END_Y_VARIANCE = 15;

    private static final double EMBER_MIN_RADIUS = 3;
    private static final double EMBER_RADIUS_VARIANCE = 6;
    private static final double EMBER_GLOW_RADIUS = 7;
    private static final double EMBER_ANGLE_DEG = 360.0;
    private static final double EMBER_MIN_DISTANCE = 40;
    private static final double EMBER_DISTANCE_VARIANCE = 65;
    private static final double EMBER_RISE_VARIANCE = 18;
    private static final double EMBER_DELAY_BASE = 560;
    private static final double EMBER_DELAY_VARIANCE = 45;
    private static final double EMBER_FADE_IN_MS = 50;
    private static final double EMBER_FADE_OUT_MS = 300;
    private static final double EMBER_END_Y_OFFSET = 25;

    PyroAnimation(AnchorPane overlay, AnimationUtils utils) {
        super(overlay, utils);
    }

    @Override
    Animation build(double sx, double sy, double tx, double ty) {
        List<Animation> parts = new ArrayList<>();

        double endX = tx - sx;
        double endY = ty - sy;

        addFireAura(sx, sy, parts);
        addFireball(sx, sy, endX, endY, parts);
        addTrailSparks(sx, sy, endX, endY, parts);
        addImpactFlash(tx, ty, parts);
        addEmbers(tx, ty, parts);

        return new ParallelTransition(parts.toArray(new Animation[0]));
    }

    private void addFireAura(double sx, double sy, List<Animation> parts) {
        Circle aura = circle(FIRE_AURA_RADIUS, Color.web("#ff6d00", FIRE_AURA_ALPHA));
        aura.setLayoutX(sx);
        aura.setLayoutY(sy);
        glow(aura, Color.web("#ff6d00"), FIRE_AURA_GLOW_RADIUS);
        overlay.getChildren().add(aura);
        Timeline tl = new Timeline(
            kf(0, aura.scaleXProperty(), FIRE_AURA_START_SCALE, aura.scaleYProperty(), FIRE_AURA_START_SCALE, aura.opacityProperty(), 0.0),
            kf(FIRE_AURA_PEAK_MS, aura.scaleXProperty(), FIRE_AURA_PEAK_SCALE, aura.scaleYProperty(), FIRE_AURA_PEAK_SCALE, aura.opacityProperty(), FIRE_AURA_PEAK_OPACITY),
            kf(FIRE_AURA_END_MS, aura.scaleXProperty(), FIRE_AURA_END_SCALE, aura.scaleYProperty(), FIRE_AURA_END_SCALE, aura.opacityProperty(), 0.0)
        );
        tl.setOnFinished(e -> overlay.getChildren().remove(aura));
        parts.add(tl);
    }

    private void addFireball(double sx, double sy, double endX, double endY, List<Animation> parts) {
        Circle fireball = attach(circle(FIREBALL_RADIUS, Color.web("#ff6d00")));
        fireball.setLayoutX(sx);
        fireball.setLayoutY(sy);
        glow(fireball, Color.web("#ffea00"), FIREBALL_GLOW_RADIUS);
        timeline(fireball, parts,
            kf(FIREBALL_START_MS, fireball.opacityProperty(), 0.0, fireball.translateXProperty(), 0.0, fireball.translateYProperty(), 0.0),
            kf(FIREBALL_APPEAR_MS, fireball.opacityProperty(), 1.0),
            kf(FIREBALL_TRAVEL_MS, fireball.translateXProperty(), endX, fireball.translateYProperty(), endY, fireball.opacityProperty(), FIREBALL_END_OPACITY),
            kf(FIREBALL_FADE_MS, fireball.opacityProperty(), 0.0));
    }

    private void addTrailSparks(double sx, double sy, double endX, double endY, List<Animation> parts) {
        for (int i = 0; i < TRAIL_SPARK_COUNT; i++) {
            spawnTrailSpark(i, sx, sy, endX, endY, parts);
        }
    }

    private void addImpactFlash(double tx, double ty, List<Animation> parts) {
        Circle flash = circle(IMPACT_FLASH_RADIUS, Color.web("#ffea00", IMPACT_FLASH_ALPHA));
        flash.setLayoutX(tx);
        flash.setLayoutY(ty);
        glow(flash, Color.web("#ffea00"), IMPACT_FLASH_GLOW_RADIUS);
        overlay.getChildren().add(flash);
        Timeline tl = new Timeline(
            kf(IMPACT_FLASH_START_MS, flash.opacityProperty(), 0.0, flash.scaleXProperty(), IMPACT_FLASH_START_SCALE, flash.scaleYProperty(), IMPACT_FLASH_START_SCALE),
            kf(IMPACT_FLASH_PEAK_MS, flash.opacityProperty(), IMPACT_FLASH_PEAK_OPACITY, flash.scaleXProperty(), IMPACT_FLASH_PEAK_SCALE, flash.scaleYProperty(), IMPACT_FLASH_PEAK_SCALE),
            kf(IMPACT_FLASH_END_MS, flash.opacityProperty(), 0.0)
        );
        tl.setOnFinished(e -> overlay.getChildren().remove(flash));
        parts.add(tl);
    }

    private void addEmbers(double tx, double ty, List<Animation> parts) {
        for (int i = 0; i < EMBER_COUNT; i++) {
            spawnEmber(i, tx, ty, parts);
        }
    }

    private void spawnTrailSpark(int i, double sx, double sy, double endX, double endY, List<Animation> parts) {
        Circle spark = attach(circle(TRAIL_SPARK_MIN_RADIUS + RNG.nextDouble() * TRAIL_SPARK_RADIUS_VARIANCE, PALETTE[i % PALETTE.length]));
        spark.setLayoutX(sx);
        spark.setLayoutY(sy);
        glow(spark, PALETTE[i % PALETTE.length], TRAIL_SPARK_GLOW_RADIUS);

        double t = TRAIL_SPARK_T_START + i / (double) TRAIL_SPARK_COUNT * TRAIL_SPARK_T_SPAN;
        double jx = endX * t + (RNG.nextDouble() - HALF) * TRAIL_SPARK_JITTER;
        double jy = endY * t + (RNG.nextDouble() - HALF) * TRAIL_SPARK_JITTER;
        double delay = TRAIL_SPARK_DELAY_BASE + i * TRAIL_SPARK_DELAY_STEP;

        timeline(spark, parts, kf(delay, spark.opacityProperty(), 0.0, spark.translateXProperty(), jx - TRAIL_SPARK_START_OFFSET, spark.translateYProperty(), jy - TRAIL_SPARK_START_OFFSET),
            kf(delay + TRAIL_SPARK_FADE_IN_MS, spark.opacityProperty(), TRAIL_SPARK_FADE_IN_OPACITY),
            kf(delay + TRAIL_SPARK_FADE_OUT_MS, spark.opacityProperty(), 0.0, spark.translateXProperty(), jx + randomCentered(TRAIL_SPARK_END_JITTER),
                spark.translateYProperty(), jy + TRAIL_SPARK_END_Y_BASE + RNG.nextDouble() * TRAIL_SPARK_END_Y_VARIANCE));
    }

    private void spawnEmber(int i, double tx, double ty, List<Animation> parts) {
        Circle ember = attach(circle(EMBER_MIN_RADIUS + RNG.nextDouble() * EMBER_RADIUS_VARIANCE, PALETTE[i % PALETTE.length]));
        ember.setLayoutX(tx);
        ember.setLayoutY(ty);
        glow(ember, PALETTE[i % PALETTE.length], EMBER_GLOW_RADIUS);

        double a = Math.toRadians(RNG.nextDouble() * EMBER_ANGLE_DEG);
        double dist = EMBER_MIN_DISTANCE + RNG.nextDouble() * EMBER_DISTANCE_VARIANCE;
        double dx = Math.cos(a) * dist;
        double dy = Math.sin(a) * dist - RNG.nextDouble() * EMBER_RISE_VARIANCE;
        double delay = EMBER_DELAY_BASE + RNG.nextDouble() * EMBER_DELAY_VARIANCE;

        timeline(ember, parts, kf(delay, ember.opacityProperty(), 0.0, ember.translateXProperty(), 0.0, ember.translateYProperty(), 0.0),
            kf(delay + EMBER_FADE_IN_MS, ember.opacityProperty(), 1.0),
            kf(delay + EMBER_FADE_OUT_MS, ember.opacityProperty(), 0.0, ember.translateXProperty(), dx, ember.translateYProperty(), dy + EMBER_END_Y_OFFSET));
    }

    private double randomCentered(double amplitude) {
        return (RNG.nextDouble() - HALF) * amplitude;
    }
}
