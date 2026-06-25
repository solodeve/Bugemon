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
 * Elemental animation for LIGHT and ALL attacks.
 */
class LightAnimation extends ElementAnimation {

    private static final Color[] PALETTE = {
        Color.web("#ffffff"), Color.web("#fff9c4"),
        Color.web("#ffecb3"), Color.web("#ffe082"), Color.web("#fff59d")
    };

    private static final int CHARGING_RING_COUNT = 3;
    private static final int PRISM_RAY_COUNT = 7;
    private static final int FOLLOWER_ORB_COUNT = 5;
    private static final int COMET_FRAGMENT_COUNT = 10;
    private static final int SHOCKWAVE_COUNT = 6;
    private static final int STAR_SHARD_COUNT = 8;

    private static final double PRISM_RAY_WIDTH_BASE = 44;
    private static final double PRISM_RAY_WIDTH_VARIANCE = 18;
    private static final double PRISM_RAY_HEIGHT = 2.2;
    private static final double PRISM_RAY_ALPHA = 0.78;
    private static final double PRISM_RAY_Y_OFFSET = 1.1;
    private static final double PRISM_RAY_SPREAD_DEG = 180.0;
    private static final double PRISM_RAY_GLOW_RADIUS = 9;
    private static final double PRISM_RAY_DELAY_STEP = 16.0;
    private static final double PRISM_RAY_START_SCALE = 0.2;
    private static final double PRISM_RAY_PEAK_SCALE = 1.35;
    private static final double PRISM_RAY_END_SCALE = 1.7;
    private static final double PRISM_RAY_PEAK_OPACITY = 0.8;
    private static final double PRISM_RAY_PEAK_MS = 70;
    private static final double PRISM_RAY_END_MS = 210;

    private static final double STAR_BULLET_OUTER_RADIUS = 18;
    private static final double STAR_BULLET_INNER_RADIUS = 8;
    private static final double STAR_BULLET_GLOW_RADIUS = 28;
    private static final double STAR_BULLET_START_MS = 290;
    private static final double STAR_BULLET_APPEAR_MS = 325;
    private static final double STAR_BULLET_TRAVEL_MS = 625;
    private static final double STAR_BULLET_FADE_MS = 660;
    private static final double STAR_BULLET_ROTATION_DEG = 540.0;
    private static final double STAR_BULLET_END_OPACITY = 0.9;

    private static final double HALO_RADIUS = 26;
    private static final double HALO_ALPHA = 0.5;
    private static final double HALO_GLOW_RADIUS = 18;
    private static final double HALO_START_SCALE = 0.3;
    private static final double HALO_PEAK_SCALE = 2.9;
    private static final double HALO_PEAK_OPACITY = 0.9;
    private static final double HALO_START_MS = 620;
    private static final double HALO_PEAK_MS = 690;
    private static final double HALO_END_MS = 820;

    private static final double CHARGING_RING_BASE_RADIUS = 36;
    private static final double CHARGING_RING_RADIUS_STEP = 5;
    private static final double CHARGING_RING_STROKE_WIDTH = 2.5;
    private static final double CHARGING_RING_GLOW_RADIUS = 12;
    private static final double CHARGING_RING_DELAY_STEP = 42.0;
    private static final double CHARGING_RING_START_SCALE = 2.6;
    private static final double CHARGING_RING_END_SCALE = 0.2;
    private static final double CHARGING_RING_PEAK_OPACITY = 0.75;
    private static final double CHARGING_RING_PEAK_MS = 145;
    private static final double CHARGING_RING_END_MS = 310;

    private static final double FOLLOWER_ORB_BASE_RADIUS = 2.8;
    private static final double FOLLOWER_ORB_RADIUS_STEP = 0.45;
    private static final double FOLLOWER_ORB_ALPHA_BASE = 0.95;
    private static final double FOLLOWER_ORB_ALPHA_STEP = 0.12;
    private static final double FOLLOWER_ORB_GLOW_RADIUS = 9;
    private static final double FOLLOWER_ORB_START_BASE = 320;
    private static final double FOLLOWER_ORB_START_STEP = 20.0;
    private static final double FOLLOWER_ORB_DRIFT = 16;
    private static final double FOLLOWER_ORB_START_SCALE = 0.5;
    private static final double FOLLOWER_ORB_FADE_IN_MS = 34;
    private static final double FOLLOWER_ORB_PEAK_OPACITY = 0.95;
    private static final double FOLLOWER_ORB_END_MS = 280;
    private static final double FOLLOWER_ORB_END_SCALE = 0.7;

    private static final double COMET_FRAGMENT_BASE_RADIUS = 3.5;
    private static final double COMET_FRAGMENT_RADIUS_VARIANCE = 3.5;
    private static final double COMET_FRAGMENT_GLOW_RADIUS = 6;
    private static final double COMET_FRAGMENT_T_START = 0.18;
    private static final double COMET_FRAGMENT_T_SPAN = 0.65;
    private static final double COMET_FRAGMENT_JITTER = 18;
    private static final double COMET_FRAGMENT_DELAY_BASE = 335;
    private static final double COMET_FRAGMENT_DELAY_STEP = 18.0;
    private static final double COMET_FRAGMENT_FADE_IN_MS = 50;
    private static final double COMET_FRAGMENT_FADE_IN_OPACITY = 0.85;
    private static final double COMET_FRAGMENT_END_MS = 210;

    private static final double SHOCKWAVE_BASE_RADIUS = 8;
    private static final double SHOCKWAVE_RADIUS_STEP = 7;
    private static final double SHOCKWAVE_STROKE_MIN = 1.5;
    private static final double SHOCKWAVE_STROKE_BASE = 3.0;
    private static final double SHOCKWAVE_STROKE_STEP = 0.3;
    private static final double SHOCKWAVE_GLOW_RADIUS = 9;
    private static final double SHOCKWAVE_DELAY_BASE = 630;
    private static final double SHOCKWAVE_DELAY_STEP = 42;
    private static final double SHOCKWAVE_START_SCALE = 0.1;
    private static final double SHOCKWAVE_PEAK_OPACITY = 0.85;
    private static final double SHOCKWAVE_PEAK_SCALE = 4.8;
    private static final double SHOCKWAVE_PEAK_MS = 260;
    private static final double SHOCKWAVE_END_MS = 400;

    private static final double STAR_SHARD_OUTER_RADIUS = 7;
    private static final double STAR_SHARD_INNER_RADIUS = 3;
    private static final double STAR_SHARD_GLOW_RADIUS = 9;
    private static final double STAR_SHARD_ANGLE_STEP_DEG = 45;
    private static final double STAR_SHARD_ANGLE_VARIANCE_DEG = 14;
    private static final double STAR_SHARD_DISTANCE_BASE = 35;
    private static final double STAR_SHARD_DISTANCE_VARIANCE = 40;
    private static final double STAR_SHARD_DELAY_BASE = 645;
    private static final double STAR_SHARD_DELAY_VARIANCE = 28;
    private static final double STAR_SHARD_FADE_IN_MS = 55;
    private static final double STAR_SHARD_END_MS = 300;

    LightAnimation(AnchorPane overlay, AnimationUtils utils) {
        super(overlay, utils);
    }

    @Override
    Animation build(double sx, double sy, double tx, double ty) {
        List<Animation> parts = new ArrayList<>();

        double endX = tx - sx;
        double endY = ty - sy;

        addChargingRings(sx, sy, parts);
        addPrismRays(sx, sy, parts);
        addStarBullet(sx, sy, endX, endY, parts);
        addFollowerOrbs(sx, sy, endX, endY, parts);
        addCometFragments(sx, sy, endX, endY, parts);
        addShockwaves(tx, ty, parts);
        addHaloBurst(tx, ty, parts);
        addStarShards(tx, ty, parts);

        return new ParallelTransition(parts.toArray(new Animation[0]));
    }

    private void addChargingRings(double sx, double sy, List<Animation> parts) {
        for (int i = 0; i < CHARGING_RING_COUNT; i++) {
            spawnChargingRing(i, sx, sy, parts);
        }
    }

    private void addPrismRays(double sx, double sy, List<Animation> parts) {
        for (int i = 0; i < PRISM_RAY_COUNT; i++) {
            Rectangle ray = new Rectangle(PRISM_RAY_WIDTH_BASE + RNG.nextDouble() * PRISM_RAY_WIDTH_VARIANCE, PRISM_RAY_HEIGHT, Color.web("#ffffff", PRISM_RAY_ALPHA));
            ray.setLayoutX(sx - ray.getWidth() * HALF);
            ray.setLayoutY(sy - PRISM_RAY_Y_OFFSET);
            ray.setRotate(i * (PRISM_RAY_SPREAD_DEG / PRISM_RAY_COUNT));
            glow(ray, Color.web("#fffde7"), PRISM_RAY_GLOW_RADIUS);
            overlay.getChildren().add(ray);

            double delay = i * PRISM_RAY_DELAY_STEP;

            Timeline tl = new Timeline(
                kf(delay, ray.opacityProperty(), 0.0, ray.scaleXProperty(), PRISM_RAY_START_SCALE),
                kf(delay + PRISM_RAY_PEAK_MS, ray.opacityProperty(), PRISM_RAY_PEAK_OPACITY, ray.scaleXProperty(), PRISM_RAY_PEAK_SCALE),
                kf(delay + PRISM_RAY_END_MS, ray.opacityProperty(), 0.0, ray.scaleXProperty(), PRISM_RAY_END_SCALE)
            );
            tl.setOnFinished(e -> overlay.getChildren().remove(ray));
            parts.add(tl);
        }
    }

    private void addStarBullet(double sx, double sy, double endX, double endY, List<Animation> parts) {
        Polygon star = attach(starPolygon(STAR_BULLET_OUTER_RADIUS, STAR_BULLET_INNER_RADIUS));
        star.setFill(Color.web("#ffe082"));
        star.setLayoutX(sx);
        star.setLayoutY(sy);
        glow(star, Color.web("#ffffff"), STAR_BULLET_GLOW_RADIUS);
        timeline(star, parts,
            kf(STAR_BULLET_START_MS, star.opacityProperty(), 0.0, star.translateXProperty(), 0.0, star.translateYProperty(), 0.0, star.rotateProperty(), 0.0),
            kf(STAR_BULLET_APPEAR_MS, star.opacityProperty(), 1.0),
            kf(STAR_BULLET_TRAVEL_MS, star.translateXProperty(), endX, star.translateYProperty(), endY, star.rotateProperty(), STAR_BULLET_ROTATION_DEG, star.opacityProperty(), STAR_BULLET_END_OPACITY),
            kf(STAR_BULLET_FADE_MS, star.opacityProperty(), 0.0));
    }

    private void addFollowerOrbs(double sx, double sy, double endX, double endY, List<Animation> parts) {
        for (int i = 0; i < FOLLOWER_ORB_COUNT; i++) {
            spawnFollowerOrb(i, sx, sy, endX, endY, parts);
        }
    }

    private void addCometFragments(double sx, double sy, double endX, double endY, List<Animation> parts) {
        for (int i = 0; i < COMET_FRAGMENT_COUNT; i++) {
            spawnCometFragment(i, sx, sy, endX, endY, parts);
        }
    }

    private void addShockwaves(double tx, double ty, List<Animation> parts) {
        for (int i = 0; i < SHOCKWAVE_COUNT; i++) {
            spawnShockwave(i, tx, ty, parts);
        }
    }

    private void addHaloBurst(double tx, double ty, List<Animation> parts) {
        Circle halo = circle(HALO_RADIUS, Color.web("#fffde7", HALO_ALPHA));
        halo.setLayoutX(tx);
        halo.setLayoutY(ty);
        glow(halo, Color.web("#fff59d"), HALO_GLOW_RADIUS);
        overlay.getChildren().add(halo);
        Timeline tl = new Timeline(
            kf(HALO_START_MS, halo.opacityProperty(), 0.0, halo.scaleXProperty(), HALO_START_SCALE, halo.scaleYProperty(), HALO_START_SCALE),
            kf(HALO_PEAK_MS, halo.opacityProperty(), HALO_PEAK_OPACITY, halo.scaleXProperty(), HALO_PEAK_SCALE, halo.scaleYProperty(), HALO_PEAK_SCALE),
            kf(HALO_END_MS, halo.opacityProperty(), 0.0)
        );
        tl.setOnFinished(e -> overlay.getChildren().remove(halo));
        parts.add(tl);
    }

    private void addStarShards(double tx, double ty, List<Animation> parts) {
        for (int i = 0; i < STAR_SHARD_COUNT; i++) {
            spawnStarShard(i, tx, ty, parts);
        }
    }

    private void spawnChargingRing(int i, double sx, double sy, List<Animation> parts) {
        Circle ring = attach(new Circle(CHARGING_RING_BASE_RADIUS - i * CHARGING_RING_RADIUS_STEP, Color.TRANSPARENT));
        ring.setLayoutX(sx);
        ring.setLayoutY(sy);
        ring.setStroke(PALETTE[i]);
        ring.setStrokeWidth(CHARGING_RING_STROKE_WIDTH);
        glow(ring, Color.web("#ffe082"), CHARGING_RING_GLOW_RADIUS);

        double delay = i * CHARGING_RING_DELAY_STEP;

        timeline(ring, parts, kf(delay, ring.opacityProperty(), 0.0, ring.scaleXProperty(), CHARGING_RING_START_SCALE, ring.scaleYProperty(), CHARGING_RING_START_SCALE),
            kf(delay + CHARGING_RING_PEAK_MS, ring.opacityProperty(), CHARGING_RING_PEAK_OPACITY),
            kf(delay + CHARGING_RING_END_MS, ring.opacityProperty(), 0.0, ring.scaleXProperty(), CHARGING_RING_END_SCALE, ring.scaleYProperty(), CHARGING_RING_END_SCALE));
    }

    private void spawnFollowerOrb(int i, double sx, double sy, double endX, double endY, List<Animation> parts) {
        Circle orb = attach(circle(FOLLOWER_ORB_BASE_RADIUS + i * FOLLOWER_ORB_RADIUS_STEP, Color.web("#fffde7", FOLLOWER_ORB_ALPHA_BASE - i * FOLLOWER_ORB_ALPHA_STEP)));
        orb.setLayoutX(sx);
        orb.setLayoutY(sy);
        glow(orb, Color.web("#fff59d"), FOLLOWER_ORB_GLOW_RADIUS);

        double start = FOLLOWER_ORB_START_BASE + i * FOLLOWER_ORB_START_STEP;
        double driftX = randomCentered(FOLLOWER_ORB_DRIFT);
        double driftY = randomCentered(FOLLOWER_ORB_DRIFT);

        timeline(orb, parts, kf(start, orb.opacityProperty(), 0.0, orb.translateXProperty(), 0.0, orb.translateYProperty(), 0.0, orb.scaleXProperty(), FOLLOWER_ORB_START_SCALE, orb.scaleYProperty(), FOLLOWER_ORB_START_SCALE),
            kf(start + FOLLOWER_ORB_FADE_IN_MS, orb.opacityProperty(), FOLLOWER_ORB_PEAK_OPACITY),
            kf(start + FOLLOWER_ORB_END_MS, orb.opacityProperty(), 0.0, orb.translateXProperty(), endX + driftX, orb.translateYProperty(), endY + driftY,
                orb.scaleXProperty(), FOLLOWER_ORB_END_SCALE, orb.scaleYProperty(), FOLLOWER_ORB_END_SCALE));
    }

    private void spawnCometFragment(int i, double sx, double sy, double endX, double endY, List<Animation> parts) {
        Circle fragment = attach(circle(COMET_FRAGMENT_BASE_RADIUS + RNG.nextDouble() * COMET_FRAGMENT_RADIUS_VARIANCE, PALETTE[i % PALETTE.length]));
        fragment.setLayoutX(sx);
        fragment.setLayoutY(sy);
        glow(fragment, Color.web("#fff8e1"), COMET_FRAGMENT_GLOW_RADIUS);

        double t = COMET_FRAGMENT_T_START + i / (double) COMET_FRAGMENT_COUNT * COMET_FRAGMENT_T_SPAN;
        double dx = endX * t + randomCentered(COMET_FRAGMENT_JITTER);
        double dy = endY * t + randomCentered(COMET_FRAGMENT_JITTER);
        double delay = COMET_FRAGMENT_DELAY_BASE + i * COMET_FRAGMENT_DELAY_STEP;

        timeline(fragment, parts, kf(delay, fragment.opacityProperty(), 0.0, fragment.translateXProperty(), 0.0, fragment.translateYProperty(), 0.0),
            kf(delay + COMET_FRAGMENT_FADE_IN_MS, fragment.opacityProperty(), COMET_FRAGMENT_FADE_IN_OPACITY),
            kf(delay + COMET_FRAGMENT_END_MS, fragment.opacityProperty(), 0.0, fragment.translateXProperty(), dx, fragment.translateYProperty(), dy));
    }

    private void spawnShockwave(int i, double tx, double ty, List<Animation> parts) {
        Circle wave = attach(new Circle(SHOCKWAVE_BASE_RADIUS + i * SHOCKWAVE_RADIUS_STEP, Color.TRANSPARENT));
        wave.setLayoutX(tx);
        wave.setLayoutY(ty);
        wave.setStroke(PALETTE[i % PALETTE.length]);
        wave.setStrokeWidth(Math.max(SHOCKWAVE_STROKE_MIN, SHOCKWAVE_STROKE_BASE - i * SHOCKWAVE_STROKE_STEP));
        glow(wave, Color.web("#ffe082"), SHOCKWAVE_GLOW_RADIUS);
        double delay = SHOCKWAVE_DELAY_BASE + i * SHOCKWAVE_DELAY_STEP;
        timeline(wave, parts, kf(delay, wave.opacityProperty(), 0.0, wave.scaleXProperty(), SHOCKWAVE_START_SCALE, wave.scaleYProperty(), SHOCKWAVE_START_SCALE),
            kf(delay + SHOCKWAVE_PEAK_MS, wave.opacityProperty(), SHOCKWAVE_PEAK_OPACITY, wave.scaleXProperty(), SHOCKWAVE_PEAK_SCALE, wave.scaleYProperty(), SHOCKWAVE_PEAK_SCALE),
            kf(delay + SHOCKWAVE_END_MS, wave.opacityProperty(), 0.0));
    }

    private void spawnStarShard(int i, double tx, double ty, List<Animation> parts) {
        Polygon shard = attach(starPolygon(STAR_SHARD_OUTER_RADIUS, STAR_SHARD_INNER_RADIUS));
        shard.setFill(PALETTE[i % PALETTE.length]);
        shard.setLayoutX(tx);
        shard.setLayoutY(ty);
        glow(shard, Color.web("#ffe082"), STAR_SHARD_GLOW_RADIUS);

        double angle = Math.toRadians(i * STAR_SHARD_ANGLE_STEP_DEG + RNG.nextDouble() * STAR_SHARD_ANGLE_VARIANCE_DEG);
        double distance = STAR_SHARD_DISTANCE_BASE + RNG.nextDouble() * STAR_SHARD_DISTANCE_VARIANCE;
        double delay = STAR_SHARD_DELAY_BASE + RNG.nextDouble() * STAR_SHARD_DELAY_VARIANCE;

        timeline(shard, parts, kf(delay, shard.opacityProperty(), 0.0, shard.translateXProperty(), 0.0, shard.translateYProperty(), 0.0),
            kf(delay + STAR_SHARD_FADE_IN_MS, shard.opacityProperty(), 1.0),
            kf(delay + STAR_SHARD_END_MS, shard.opacityProperty(), 0.0, shard.translateXProperty(), Math.cos(angle) * distance, shard.translateYProperty(), Math.sin(angle) * distance));
    }

    private double randomCentered(double amplitude) {
        return (RNG.nextDouble() - HALF) * amplitude;
    }
}
