package ulb.view.battle.helper;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * Drives sprite animations (idle float, attack lunge/cast, hurt shake) for both Bugemon.
 * The enemy idle is started 700 ms into its cycle so the two sprites bob out of phase.
 */
public class SpriteAnimator {

    private static final double   IDLE_AMPLITUDE = 7.0;
    private static final Duration IDLE_CYCLE     = Duration.millis(1800);
    private static final double   LUNGE_DISTANCE = 78.0;

    private final ImageView playerSprite;
    private final ImageView enemySprite;

    private Timeline playerIdle;
    private Timeline enemyIdle;

    public SpriteAnimator(ImageView playerSprite, ImageView enemySprite) {
        this.playerSprite = playerSprite;
        this.enemySprite  = enemySprite;
    }

    public void startIdle() {
        playerIdle = buildIdleTimeline(playerSprite);
        enemyIdle  = buildIdleTimeline(enemySprite);
        playerIdle.play();
        enemyIdle.playFrom(Duration.millis(700));
    }

    private Timeline buildIdleTimeline(ImageView sprite) {
        Timeline tl = new Timeline(
            new KeyFrame(Duration.ZERO,
                new KeyValue(sprite.translateYProperty(),  0.0, Interpolator.EASE_BOTH)),
            new KeyFrame(IDLE_CYCLE.multiply(0.25),
                new KeyValue(sprite.translateYProperty(), -IDLE_AMPLITUDE, Interpolator.EASE_BOTH)),
            new KeyFrame(IDLE_CYCLE.multiply(0.75),
                new KeyValue(sprite.translateYProperty(), +IDLE_AMPLITUDE, Interpolator.EASE_BOTH)),
            new KeyFrame(IDLE_CYCLE,
                new KeyValue(sprite.translateYProperty(),  0.0, Interpolator.EASE_BOTH))
        );
        tl.setCycleCount(Animation.INDEFINITE);
        return tl;
    }

    public void playAttack(boolean playerAttacking, boolean isMagic) {
        if (isMagic) {
            playMagicalAttack(playerAttacking);
        } else {
            playPhysicalAttack(playerAttacking);
        }
    }

    private void playPhysicalAttack(boolean playerAttacking) {
        ImageView attacker = playerAttacking ? playerSprite : enemySprite;
        double lunge = (playerAttacking ? 1 : -1) * LUNGE_DISTANCE;

        Timeline tl = new Timeline(
            new KeyFrame(Duration.ZERO,
                new KeyValue(attacker.translateXProperty(),  0.0,   Interpolator.EASE_OUT),
                new KeyValue(attacker.scaleXProperty(),      1.0,   Interpolator.EASE_OUT),
                new KeyValue(attacker.scaleYProperty(),      1.0,   Interpolator.EASE_OUT)),
            new KeyFrame(Duration.millis(75),
                new KeyValue(attacker.translateXProperty(), -lunge * 0.18, Interpolator.EASE_OUT),
                new KeyValue(attacker.scaleXProperty(),      0.84,         Interpolator.EASE_OUT),
                new KeyValue(attacker.scaleYProperty(),      1.16,         Interpolator.EASE_OUT)),
            new KeyFrame(Duration.millis(185),
                new KeyValue(attacker.translateXProperty(),  lunge,  Interpolator.EASE_OUT),
                new KeyValue(attacker.scaleXProperty(),      1.28,   Interpolator.EASE_OUT),
                new KeyValue(attacker.scaleYProperty(),      0.88,   Interpolator.EASE_OUT)),
            new KeyFrame(Duration.millis(310),
                new KeyValue(attacker.translateXProperty(),  lunge * 0.1, Interpolator.EASE_IN),
                new KeyValue(attacker.scaleXProperty(),      1.0,         Interpolator.EASE_IN),
                new KeyValue(attacker.scaleYProperty(),      1.0,         Interpolator.EASE_IN)),
            new KeyFrame(Duration.millis(420),
                new KeyValue(attacker.translateXProperty(),  0.0, Interpolator.EASE_IN))
        );
        tl.play();
    }

    private void playMagicalAttack(boolean playerAttacking) {
        ImageView attacker = playerAttacking ? playerSprite : enemySprite;
        double lean = playerAttacking ? 18.0 : -18.0;
        Color elemColor = Color.web("#a78bfa");

        DropShadow glow = new DropShadow();
        glow.setColor(elemColor);
        glow.setRadius(0);
        glow.setSpread(0.5);
        attacker.setEffect(glow);

        Timeline tl = new Timeline(
            new KeyFrame(Duration.ZERO,
                new KeyValue(attacker.translateXProperty(), 0.0,   Interpolator.EASE_BOTH),
                new KeyValue(attacker.scaleXProperty(),     1.0,   Interpolator.EASE_BOTH),
                new KeyValue(attacker.scaleYProperty(),     1.0,   Interpolator.EASE_BOTH),
                new KeyValue(glow.radiusProperty(),         0.0,   Interpolator.EASE_BOTH)),
            new KeyFrame(Duration.millis(120),
                new KeyValue(attacker.translateXProperty(), lean,  Interpolator.EASE_OUT),
                new KeyValue(attacker.scaleXProperty(),     0.93,  Interpolator.EASE_OUT),
                new KeyValue(attacker.scaleYProperty(),     1.07,  Interpolator.EASE_OUT),
                new KeyValue(glow.radiusProperty(),         22.0,  Interpolator.EASE_OUT)),
            new KeyFrame(Duration.millis(280),
                new KeyValue(attacker.translateXProperty(), lean * 1.5, Interpolator.EASE_OUT),
                new KeyValue(attacker.scaleXProperty(),     1.20,       Interpolator.EASE_OUT),
                new KeyValue(attacker.scaleYProperty(),     1.20,       Interpolator.EASE_OUT),
                new KeyValue(glow.radiusProperty(),         38.0,       Interpolator.EASE_OUT)),
            new KeyFrame(Duration.millis(400),
                new KeyValue(attacker.translateXProperty(), -lean, Interpolator.EASE_IN),
                new KeyValue(attacker.scaleXProperty(),     0.96,  Interpolator.EASE_IN),
                new KeyValue(attacker.scaleYProperty(),     1.04,  Interpolator.EASE_IN),
                new KeyValue(glow.radiusProperty(),         12.0,  Interpolator.EASE_IN)),
            new KeyFrame(Duration.millis(540),
                new KeyValue(attacker.translateXProperty(), 0.0,   Interpolator.EASE_IN),
                new KeyValue(attacker.scaleXProperty(),     1.0,   Interpolator.EASE_IN),
                new KeyValue(attacker.scaleYProperty(),     1.0,   Interpolator.EASE_IN),
                new KeyValue(glow.radiusProperty(),         0.0,   Interpolator.EASE_IN))
        );
        tl.setOnFinished(e -> attacker.setEffect(null));
        tl.play();
    }

    public void playHurt(boolean playerHurt) {
        ImageView target = playerHurt ? playerSprite : enemySprite;

        Timeline shake = new Timeline(
            new KeyFrame(Duration.ZERO,        new KeyValue(target.translateXProperty(),  0.0, Interpolator.LINEAR)),
            new KeyFrame(Duration.millis(45),  new KeyValue(target.translateXProperty(), -16.0, Interpolator.LINEAR)),
            new KeyFrame(Duration.millis(90),  new KeyValue(target.translateXProperty(), +16.0, Interpolator.LINEAR)),
            new KeyFrame(Duration.millis(130), new KeyValue(target.translateXProperty(), -13.0, Interpolator.LINEAR)),
            new KeyFrame(Duration.millis(170), new KeyValue(target.translateXProperty(), +13.0, Interpolator.LINEAR)),
            new KeyFrame(Duration.millis(210), new KeyValue(target.translateXProperty(),  -8.0, Interpolator.LINEAR)),
            new KeyFrame(Duration.millis(250), new KeyValue(target.translateXProperty(),  +8.0, Interpolator.LINEAR)),
            new KeyFrame(Duration.millis(285), new KeyValue(target.translateXProperty(),  -3.0, Interpolator.LINEAR)),
            new KeyFrame(Duration.millis(320), new KeyValue(target.translateXProperty(),   0.0, Interpolator.LINEAR))
        );

        ColorAdjust ca = new ColorAdjust();
        target.setEffect(ca);
        Timeline flash = new Timeline(
            new KeyFrame(Duration.ZERO,
                new KeyValue(ca.hueProperty(),        0.0,  Interpolator.LINEAR),
                new KeyValue(ca.brightnessProperty(), 0.0,  Interpolator.LINEAR),
                new KeyValue(ca.saturationProperty(), 0.0,  Interpolator.LINEAR)),
            new KeyFrame(Duration.millis(55),
                new KeyValue(ca.hueProperty(),        -1.0, Interpolator.EASE_OUT),
                new KeyValue(ca.brightnessProperty(),  0.7, Interpolator.EASE_OUT),
                new KeyValue(ca.saturationProperty(),  1.0, Interpolator.EASE_OUT)),
            new KeyFrame(Duration.millis(160),
                new KeyValue(ca.hueProperty(),        -0.9, Interpolator.LINEAR),
                new KeyValue(ca.brightnessProperty(),  0.3, Interpolator.LINEAR)),
            new KeyFrame(Duration.millis(380),
                new KeyValue(ca.hueProperty(),         0.0, Interpolator.EASE_IN),
                new KeyValue(ca.brightnessProperty(),  0.0, Interpolator.EASE_IN),
                new KeyValue(ca.saturationProperty(),  0.0, Interpolator.EASE_IN))
        );
        flash.setOnFinished(e -> target.setEffect(null));

        new ParallelTransition(shake, flash).play();
    }

    /** Resets transform and effect, then restarts idle — called on Bugemon swap. */
    public void resetSprite(boolean isPlayer) {
        ImageView sprite = isPlayer ? playerSprite : enemySprite;
        sprite.setTranslateX(0);
        sprite.setTranslateY(0);
        sprite.setEffect(null);
        sprite.setOpacity(1.0);
        sprite.setRotate(0);
        sprite.setScaleX(1.0);
        sprite.setScaleY(1.0);

        stopIdle(isPlayer);
        Timeline fresh = buildIdleTimeline(sprite);
        if (isPlayer) {
            playerIdle = fresh;
            playerIdle.play();
        } else {
            enemyIdle = fresh;
            enemyIdle.playFrom(Duration.millis(700));
        }
    }

    private void stopIdle(boolean isPlayer) {
        Timeline idle = isPlayer ? playerIdle : enemyIdle;
        if (idle != null) idle.stop();
    }
}
