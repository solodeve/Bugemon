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
 * Elemental animation for SHADOW attacks.
 */
class ShadowAnimation extends ElementAnimation {

    private static final Color[] PALETTE = {
        Color.web("#0f0a18"), Color.web("#2b1b3a"),
        Color.web("#45215a"), Color.web("#6a1b9a"), Color.web("#b388ff")
    };

    private static final int ORBITING_RUNE_COUNT = 4;
    private static final int SMOKE_GHOST_COUNT = 4;
    private static final int FOLLOWER_ORB_COUNT = 4;
    private static final int SHADOW_RIPPLE_COUNT = 3;
    private static final int VOID_SHARD_COUNT = 12;

    private static final double DARK_SIGIL_RADIUS = 20;
    private static final double DARK_SIGIL_STROKE_WIDTH = 3.0;
    private static final double DARK_SIGIL_GLOW_RADIUS = 12;
    private static final double DARK_SIGIL_START_SCALE = 0.35;
    private static final double DARK_SIGIL_PEAK_OPACITY = 0.9;
    private static final double DARK_SIGIL_PEAK_MS = 60;
    private static final double DARK_SIGIL_END_MS = 225;
    private static final double DARK_SIGIL_END_SCALE = 1.8;

    private static final double ORBITING_RUNE_OUTER_RADIUS = 6;
    private static final double ORBITING_RUNE_INNER_RADIUS = 2.8;
    private static final double ORBITING_RUNE_GLOW_RADIUS = 8;
    private static final double ORBITING_RUNE_ANGLE_STEP_DEG = 90.0;
    private static final double ORBITING_RUNE_DELAY_STEP = 20.0;
    private static final double ORBITING_RUNE_PEAK_MS = 120;
    private static final double ORBITING_RUNE_END_MS = 250;
    private static final double ORBITING_RUNE_PEAK_OPACITY = 0.85;
    private static final double ORBITING_RUNE_PEAK_DISTANCE = 24;
    private static final double ORBITING_RUNE_END_DISTANCE = 34;
    private static final double ORBITING_RUNE_PEAK_ROTATION = 160.0;
    private static final double ORBITING_RUNE_END_ROTATION = 340.0;

    private static final double VOID_LANCE_WIDTH = 56;
    private static final double VOID_LANCE_HEIGHT = 5;
    private static final double VOID_LANCE_X_OFFSET = 28;
    private static final double VOID_LANCE_Y_OFFSET = 2.5;
    private static final double VOID_LANCE_GLOW_RADIUS = 20;
    private static final double VOID_LANCE_START_MS = 225;
    private static final double VOID_LANCE_APPEAR_MS = 248;
    private static final double VOID_LANCE_TRAVEL_MS = 565;
    private static final double VOID_LANCE_END_OPACITY = 0.9;
    private static final double VOID_LANCE_FADE_MS = 600;

    private static final double UMBRA_CORE_RADIUS = 9;
    private static final double UMBRA_CORE_ALPHA = 0.9;
    private static final double UMBRA_CORE_GLOW_RADIUS = 16;
    private static final double UMBRA_CORE_START_MS = 240;
    private static final double UMBRA_CORE_APPEAR_MS = 285;
    private static final double UMBRA_CORE_PEAK_OPACITY = 0.95;
    private static final double UMBRA_CORE_TRAVEL_MS = 570;
    private static final double UMBRA_CORE_END_OPACITY = 0.8;
    private static final double UMBRA_CORE_FADE_MS = 620;
    private static final double UMBRA_CORE_END_SCALE = 1.8;

    private static final double ECLIPSE_FLASH_RADIUS = 32;
    private static final double ECLIPSE_FLASH_ALPHA = 0.7;
    private static final double ECLIPSE_FLASH_GLOW_RADIUS = 28;
    private static final double ECLIPSE_FLASH_START_SCALE = 0.2;
    private static final double ECLIPSE_FLASH_PEAK_SCALE = 2.8;
    private static final double ECLIPSE_FLASH_PEAK_OPACITY = 0.9;
    private static final double ECLIPSE_FLASH_START_MS = 555;
    private static final double ECLIPSE_FLASH_PEAK_MS = 590;
    private static final double ECLIPSE_FLASH_END_MS = 730;

    private static final double SHADOW_RIPPLE_BASE_RADIUS = 12;
    private static final double SHADOW_RIPPLE_RADIUS_STEP = 9;
    private static final double SHADOW_RIPPLE_STROKE_WIDTH = 2.3;
    private static final double SHADOW_RIPPLE_GLOW_RADIUS = 8;
    private static final double SHADOW_RIPPLE_DELAY_BASE = 600;
    private static final double SHADOW_RIPPLE_DELAY_STEP = 48;
    private static final double SHADOW_RIPPLE_START_SCALE = 0.2;
    private static final double SHADOW_RIPPLE_PEAK_SCALE = 3.7;
    private static final double SHADOW_RIPPLE_PEAK_OPACITY = 0.85;
    private static final double SHADOW_RIPPLE_PEAK_MS = 220;
    private static final double SHADOW_RIPPLE_END_MS = 340;

    private static final double VOID_SHARD_BASE_SIZE = 5;
    private static final double VOID_SHARD_SIZE_VARIANCE = 6;
    private static final double VOID_SHARD_ROTATION_DEG = 360;
    private static final double VOID_SHARD_GLOW_RADIUS = 8;
    private static final double VOID_SHARD_ANGLE_STEP_DEG = 30;
    private static final double VOID_SHARD_ANGLE_VARIANCE_DEG = 20;
    private static final double VOID_SHARD_DISTANCE_BASE = 24;
    private static final double VOID_SHARD_DISTANCE_VARIANCE = 44;
    private static final double VOID_SHARD_DELAY_BASE = 580;
    private static final double VOID_SHARD_DELAY_VARIANCE = 28;
    private static final double VOID_SHARD_START_SCALE = 0.3;
    private static final double VOID_SHARD_PEAK_MS = 48;
    private static final double VOID_SHARD_PEAK_SCALE = 1.2;
    private static final double VOID_SHARD_END_MS = 280;
    private static final double VOID_SHARD_END_SCALE = 0.5;

    private static final double FOLLOWER_ORB_BASE_RADIUS = 3.2;
    private static final double FOLLOWER_ORB_RADIUS_STEP = 0.5;
    private static final double FOLLOWER_ORB_ALPHA_BASE = 0.9;
    private static final double FOLLOWER_ORB_ALPHA_STEP = 0.12;
    private static final double FOLLOWER_ORB_GLOW_RADIUS = 10;
    private static final double FOLLOWER_ORB_START_BASE = 280;
    private static final double FOLLOWER_ORB_START_STEP = 28.0;
    private static final double FOLLOWER_ORB_DRIFT = 20;
    private static final double FOLLOWER_ORB_START_SCALE = 0.45;
    private static final double FOLLOWER_ORB_FADE_IN_MS = 38;
    private static final double FOLLOWER_ORB_PEAK_OPACITY = 0.95;
    private static final double FOLLOWER_ORB_END_MS = 300;
    private static final double FOLLOWER_ORB_END_SCALE = 0.8;

    private static final double SMOKE_WIDTH = 42;
    private static final double SMOKE_HEIGHT = 6;
    private static final double SMOKE_ALPHA_BASE = 0.3;
    private static final double SMOKE_ALPHA_STEP = 0.05;
    private static final double SMOKE_X_OFFSET = 22;
    private static final double SMOKE_Y_OFFSET = 3;
    private static final double SMOKE_DELAY_BASE = 225;
    private static final double SMOKE_DELAY_STEP = 14.0;
    private static final double SMOKE_FADE_IN_MS = 18;
    private static final double SMOKE_PEAK_OPACITY_BASE = 0.42;
    private static final double SMOKE_PEAK_OPACITY_STEP = 0.06;
    private static final double SMOKE_END_MS = 300;

    ShadowAnimation(AnchorPane overlay, AnimationUtils utils) {
        super(overlay, utils);
    }

    @Override
    Animation build(double sx, double sy, double tx, double ty) {
        List<Animation> parts = new ArrayList<>();

        double endX     = tx - sx;
        double endY     = ty - sy;

        double flyAngle = Math.toDegrees(Math.atan2(endY, endX));
        addDarkSigil(sx, sy, parts);
        addOrbitingRunes(sx, sy, parts);
        addSmokeTrail(sx, sy, endX, endY, flyAngle, parts);
        addVoidLance(sx, sy, endX, endY, flyAngle, parts);
        addUmbraCore(sx, sy, endX, endY, parts);
        addFollowerOrbs(sx, sy, endX, endY, parts);
        addEclipseFlash(tx, ty, parts);
        addShadowRipples(tx, ty, parts);
        addVoidShards(tx, ty, parts);

        return new ParallelTransition(parts.toArray(new Animation[0]));
    }

    private void addDarkSigil(double sx, double sy, List<Animation> parts) {
        Circle chargeRing = new Circle(DARK_SIGIL_RADIUS, Color.TRANSPARENT);
        chargeRing.setLayoutX(sx);
        chargeRing.setLayoutY(sy);
        chargeRing.setStroke(Color.web("#6a1b9a"));
        chargeRing.setStrokeWidth(DARK_SIGIL_STROKE_WIDTH);
        glow(chargeRing, Color.web("#b388ff"), DARK_SIGIL_GLOW_RADIUS);
        overlay.getChildren().add(chargeRing);
        Timeline tl = new Timeline(
            kf(0, chargeRing.opacityProperty(), 0.0, chargeRing.scaleXProperty(), DARK_SIGIL_START_SCALE, chargeRing.scaleYProperty(), DARK_SIGIL_START_SCALE),
            kf(DARK_SIGIL_PEAK_MS, chargeRing.opacityProperty(), DARK_SIGIL_PEAK_OPACITY),
            kf(DARK_SIGIL_END_MS, chargeRing.opacityProperty(), 0.0, chargeRing.scaleXProperty(), DARK_SIGIL_END_SCALE, chargeRing.scaleYProperty(), DARK_SIGIL_END_SCALE)
        );
        tl.setOnFinished(e -> overlay.getChildren().remove(chargeRing));
        parts.add(tl);
    }

    private void addOrbitingRunes(double sx, double sy, List<Animation> parts) {
        for (int i = 0; i < ORBITING_RUNE_COUNT; i++) {
            spawnOrbitingRune(i, sx, sy, parts);
        }
    }

    private void addSmokeTrail(double sx, double sy, double endX, double endY, double flyAngle, List<Animation> parts) {
        for (int i = 0; i < SMOKE_GHOST_COUNT; i++) {
            spawnSmokeGhost(i, sx, sy, endX, endY, flyAngle, parts);
        }
    }

    private void addVoidLance(double sx, double sy, double endX, double endY, double flyAngle, List<Animation> parts) {
        Rectangle needle = attach(new Rectangle(VOID_LANCE_WIDTH, VOID_LANCE_HEIGHT, Color.web("#d1c4e9")));
        needle.setLayoutX(sx - VOID_LANCE_X_OFFSET);
        needle.setLayoutY(sy - VOID_LANCE_Y_OFFSET);
        needle.setRotate(flyAngle);
        glow(needle, Color.web("#b388ff"), VOID_LANCE_GLOW_RADIUS);
        timeline(needle, parts,
            kf(VOID_LANCE_START_MS, needle.opacityProperty(), 0.0, needle.translateXProperty(), 0.0, needle.translateYProperty(), 0.0),
            kf(VOID_LANCE_APPEAR_MS, needle.opacityProperty(), 1.0),
            kf(VOID_LANCE_TRAVEL_MS, needle.translateXProperty(), endX, needle.translateYProperty(), endY, needle.opacityProperty(), VOID_LANCE_END_OPACITY),
            kf(VOID_LANCE_FADE_MS, needle.opacityProperty(), 0.0));
    }

    private void addUmbraCore(double sx, double sy, double endX, double endY, List<Animation> parts) {
        Circle core = circle(UMBRA_CORE_RADIUS, Color.web("#2b1b3a", UMBRA_CORE_ALPHA));
        core.setLayoutX(sx);
        core.setLayoutY(sy);
        glow(core, Color.web("#b388ff"), UMBRA_CORE_GLOW_RADIUS);
        overlay.getChildren().add(core);
        Timeline tl = new Timeline(
            kf(UMBRA_CORE_START_MS, core.opacityProperty(), 0.0, core.translateXProperty(), 0.0, core.translateYProperty(), 0.0),
            kf(UMBRA_CORE_APPEAR_MS, core.opacityProperty(), UMBRA_CORE_PEAK_OPACITY),
            kf(UMBRA_CORE_TRAVEL_MS, core.translateXProperty(), endX, core.translateYProperty(), endY, core.opacityProperty(), UMBRA_CORE_END_OPACITY),
            kf(UMBRA_CORE_FADE_MS, core.opacityProperty(), 0.0, core.scaleXProperty(), UMBRA_CORE_END_SCALE, core.scaleYProperty(), UMBRA_CORE_END_SCALE)
        );
        tl.setOnFinished(e -> overlay.getChildren().remove(core));
        parts.add(tl);
    }

    private void addFollowerOrbs(double sx, double sy, double endX, double endY, List<Animation> parts) {
        for (int i = 0; i < FOLLOWER_ORB_COUNT; i++) {
            spawnFollowerOrb(i, sx, sy, endX, endY, parts);
        }
    }

    private void addEclipseFlash(double tx, double ty, List<Animation> parts) {
        Circle flash = circle(ECLIPSE_FLASH_RADIUS, Color.web("#ede7f6", ECLIPSE_FLASH_ALPHA));
        flash.setLayoutX(tx);
        flash.setLayoutY(ty);
        glow(flash, Color.web("#6a1b9a"), ECLIPSE_FLASH_GLOW_RADIUS);
        overlay.getChildren().add(flash);
        Timeline tl = new Timeline(
            kf(ECLIPSE_FLASH_START_MS, flash.opacityProperty(), 0.0, flash.scaleXProperty(), ECLIPSE_FLASH_START_SCALE, flash.scaleYProperty(), ECLIPSE_FLASH_START_SCALE),
            kf(ECLIPSE_FLASH_PEAK_MS, flash.opacityProperty(), ECLIPSE_FLASH_PEAK_OPACITY, flash.scaleXProperty(), ECLIPSE_FLASH_PEAK_SCALE, flash.scaleYProperty(), ECLIPSE_FLASH_PEAK_SCALE),
            kf(ECLIPSE_FLASH_END_MS, flash.opacityProperty(), 0.0)
        );
        tl.setOnFinished(e -> overlay.getChildren().remove(flash));
        parts.add(tl);
    }

    private void addShadowRipples(double tx, double ty, List<Animation> parts) {
        for (int i = 0; i < SHADOW_RIPPLE_COUNT; i++) {
            spawnShadowRipple(i, tx, ty, parts);
        }
    }

    private void addVoidShards(double tx, double ty, List<Animation> parts) {
        for (int i = 0; i < VOID_SHARD_COUNT; i++) {
            spawnVoidShard(i, tx, ty, parts);
        }
    }

    private void spawnOrbitingRune(int i, double sx, double sy, List<Animation> parts) {
        Polygon rune = attach(starPolygon(ORBITING_RUNE_OUTER_RADIUS, ORBITING_RUNE_INNER_RADIUS));
        rune.setFill(Color.web("#b388ff"));
        rune.setLayoutX(sx);
        rune.setLayoutY(sy);
        glow(rune, Color.web("#d1c4e9"), ORBITING_RUNE_GLOW_RADIUS);

        double a = Math.toRadians(i * ORBITING_RUNE_ANGLE_STEP_DEG);
        double delay = i * ORBITING_RUNE_DELAY_STEP;

        timeline(rune, parts, kf(delay, rune.opacityProperty(), 0.0, rune.translateXProperty(), 0.0, rune.translateYProperty(), 0.0, rune.rotateProperty(), 0.0),
            kf(delay + ORBITING_RUNE_PEAK_MS, rune.opacityProperty(), ORBITING_RUNE_PEAK_OPACITY,
                rune.translateXProperty(), Math.cos(a) * ORBITING_RUNE_PEAK_DISTANCE, rune.translateYProperty(), Math.sin(a) * ORBITING_RUNE_PEAK_DISTANCE,
                rune.rotateProperty(), ORBITING_RUNE_PEAK_ROTATION),
            kf(delay + ORBITING_RUNE_END_MS, rune.opacityProperty(), 0.0,
                rune.translateXProperty(), Math.cos(a) * ORBITING_RUNE_END_DISTANCE, rune.translateYProperty(), Math.sin(a) * ORBITING_RUNE_END_DISTANCE,
                rune.rotateProperty(), ORBITING_RUNE_END_ROTATION));
    }

    private void spawnSmokeGhost(int i, double sx, double sy, double endX, double endY, double flyAngle, List<Animation> parts) {
        Rectangle ghost = attach(new Rectangle(SMOKE_WIDTH, SMOKE_HEIGHT, Color.web("#6a1b9a", SMOKE_ALPHA_BASE - i * SMOKE_ALPHA_STEP)));
        ghost.setLayoutX(sx - SMOKE_X_OFFSET);
        ghost.setLayoutY(sy - SMOKE_Y_OFFSET);
        ghost.setRotate(flyAngle);

        double delay = SMOKE_DELAY_BASE + i * SMOKE_DELAY_STEP;

        timeline(ghost, parts, kf(delay, ghost.opacityProperty(), 0.0, ghost.translateXProperty(), 0.0, ghost.translateYProperty(), 0.0),
            kf(delay + SMOKE_FADE_IN_MS, ghost.opacityProperty(), SMOKE_PEAK_OPACITY_BASE - i * SMOKE_PEAK_OPACITY_STEP),
            kf(delay + SMOKE_END_MS, ghost.opacityProperty(), 0.0, ghost.translateXProperty(), endX, ghost.translateYProperty(), endY));
    }

    private void spawnFollowerOrb(int i, double sx, double sy, double endX, double endY, List<Animation> parts) {
        Circle orb = attach(circle(FOLLOWER_ORB_BASE_RADIUS + i * FOLLOWER_ORB_RADIUS_STEP, Color.web("#b388ff", FOLLOWER_ORB_ALPHA_BASE - i * FOLLOWER_ORB_ALPHA_STEP)));
        orb.setLayoutX(sx);
        orb.setLayoutY(sy);
        glow(orb, Color.web("#d1c4e9"), FOLLOWER_ORB_GLOW_RADIUS);

        double start = FOLLOWER_ORB_START_BASE + i * FOLLOWER_ORB_START_STEP;
        double driftX = centered(FOLLOWER_ORB_DRIFT);
        double driftY = centered(FOLLOWER_ORB_DRIFT);

        timeline(orb, parts, kf(start, orb.opacityProperty(), 0.0, orb.translateXProperty(), 0.0, orb.translateYProperty(), 0.0, orb.scaleXProperty(), FOLLOWER_ORB_START_SCALE, orb.scaleYProperty(), FOLLOWER_ORB_START_SCALE),
            kf(start + FOLLOWER_ORB_FADE_IN_MS, orb.opacityProperty(), FOLLOWER_ORB_PEAK_OPACITY),
            kf(start + FOLLOWER_ORB_END_MS, orb.opacityProperty(), 0.0, orb.translateXProperty(), endX + driftX, orb.translateYProperty(), endY + driftY,
                orb.scaleXProperty(), FOLLOWER_ORB_END_SCALE, orb.scaleYProperty(), FOLLOWER_ORB_END_SCALE));
    }

    private void spawnShadowRipple(int i, double tx, double ty, List<Animation> parts) {
        Circle ripple = attach(new Circle(SHADOW_RIPPLE_BASE_RADIUS + i * SHADOW_RIPPLE_RADIUS_STEP, Color.TRANSPARENT));
        ripple.setLayoutX(tx);
        ripple.setLayoutY(ty);
        ripple.setStroke(PALETTE[Math.min(i + 1, PALETTE.length - 1)]);
        ripple.setStrokeWidth(SHADOW_RIPPLE_STROKE_WIDTH);
        glow(ripple, Color.web("#6a1b9a"), SHADOW_RIPPLE_GLOW_RADIUS);

        double delay = SHADOW_RIPPLE_DELAY_BASE + i * SHADOW_RIPPLE_DELAY_STEP;

        timeline(ripple, parts, kf(delay, ripple.opacityProperty(), 0.0, ripple.scaleXProperty(), SHADOW_RIPPLE_START_SCALE, ripple.scaleYProperty(), SHADOW_RIPPLE_START_SCALE),
            kf(delay + SHADOW_RIPPLE_PEAK_MS, ripple.opacityProperty(), SHADOW_RIPPLE_PEAK_OPACITY, ripple.scaleXProperty(), SHADOW_RIPPLE_PEAK_SCALE, ripple.scaleYProperty(), SHADOW_RIPPLE_PEAK_SCALE),
            kf(delay + SHADOW_RIPPLE_END_MS, ripple.opacityProperty(), 0.0));
    }

    private void spawnVoidShard(int i, double tx, double ty, List<Animation> parts) {
        Polygon shard = attach(crystalShard(VOID_SHARD_BASE_SIZE + RNG.nextDouble() * VOID_SHARD_SIZE_VARIANCE));
        shard.setFill(PALETTE[i % PALETTE.length]);
        shard.setLayoutX(tx);
        shard.setLayoutY(ty);
        shard.setRotate(RNG.nextDouble() * VOID_SHARD_ROTATION_DEG);
        glow(shard, Color.web("#b388ff"), VOID_SHARD_GLOW_RADIUS);

        double a = Math.toRadians(i * VOID_SHARD_ANGLE_STEP_DEG + RNG.nextDouble() * VOID_SHARD_ANGLE_VARIANCE_DEG);
        double d = VOID_SHARD_DISTANCE_BASE + RNG.nextDouble() * VOID_SHARD_DISTANCE_VARIANCE;
        double delay = VOID_SHARD_DELAY_BASE + RNG.nextDouble() * VOID_SHARD_DELAY_VARIANCE;

        timeline(shard, parts, kf(delay, shard.opacityProperty(), 0.0, shard.translateXProperty(), 0.0, shard.translateYProperty(), 0.0, shard.scaleXProperty(), VOID_SHARD_START_SCALE, shard.scaleYProperty(), VOID_SHARD_START_SCALE),
            kf(delay + VOID_SHARD_PEAK_MS, shard.opacityProperty(), 1.0, shard.scaleXProperty(), VOID_SHARD_PEAK_SCALE, shard.scaleYProperty(), VOID_SHARD_PEAK_SCALE),
            kf(delay + VOID_SHARD_END_MS, shard.opacityProperty(), 0.0, shard.translateXProperty(), Math.cos(a) * d, shard.translateYProperty(), Math.sin(a) * d,
                shard.scaleXProperty(), VOID_SHARD_END_SCALE, shard.scaleYProperty(), VOID_SHARD_END_SCALE));
    }

    private double centered(double amplitude) {
        return (RNG.nextDouble() - HALF) * amplitude;
    }
}
