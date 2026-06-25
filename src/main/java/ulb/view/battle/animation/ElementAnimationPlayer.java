package ulb.view.battle.animation;

import javafx.animation.Animation;
import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import ulb.model.domain.Element;

/**
 * Entry point for elemental attack animations.
 * Delegates to a per-element {@link ElementAnimation} implementation.
 *
 * <p>Sprite center coordinates fall back to FXML-derived constants when
 * live sprite references are not yet set via {@link #setSprites}.</p>
 */
public class ElementAnimationPlayer {

    private static final double PLAYER_CENTER_X = 360.0;
    private static final double PLAYER_CENTER_Y = 510.0;
    private static final double ENEMY_CENTER_X  = 730.0;
    private static final double ENEMY_CENTER_Y  = 360.0;

    private final AnchorPane overlay;
    private final AnimationUtils animationUtils;
    private ImageView playerSprite;
    private ImageView enemySprite;

    public ElementAnimationPlayer(AnchorPane overlay) {
        this.overlay = overlay;
        this.animationUtils = new AnimationUtils();
    }

    /**
     * Provides live sprite references so that animation origins are derived from the
     * sprites' actual on-screen bounds rather than from the hardcoded fallback constants.
     *
     * <p>Call this whenever the battle scene swaps active Bugemon sprites. Either
     * argument may be {@code null} — the corresponding side will fall back to its
     * constant center.
     */
    public void setSprites(ImageView playerSprite, ImageView enemySprite) {
        this.playerSprite = playerSprite;
        this.enemySprite  = enemySprite;
    }

    /**
     * Plays the attack animation for the given element and invokes {@code onComplete}
     * when it finishes.
     *
     * @param element         determines which {@link ElementAnimation} subclass is used;
     *                        must not be {@code null}
     * @param playerAttacking {@code true} if the projectile travels from the player's
     *                        sprite toward the enemy; {@code false} for the reverse
     * @param onComplete      called after the animation ends; may be {@code null}
     * @throws IllegalArgumentException if {@code element} is {@code null}
     */
    public void play(Element element, boolean playerAttacking, Runnable onComplete) {
        if (element == null) throw new IllegalArgumentException("Element cannot be null.");

        double[] source = attackCenter(playerAttacking);
        double[] target = defenseCenter(playerAttacking);

        Animation animation = selectAnimation(element).build(source[0], source[1], target[0], target[1]);
        animation.setOnFinished(e -> runIfPresent(onComplete));
        animation.play();
    }

    private ElementAnimation selectAnimation(Element element) {
        return switch (element) {
            case PLANT -> new FloraAnimation(overlay, animationUtils);
            case FIRE -> new PyroAnimation(overlay, animationUtils);
            case ICE -> new AquaAnimation(overlay, animationUtils);
            case MECHA -> new MechaAnimation(overlay, animationUtils);
            case LIGHT, ALL -> new LightAnimation(overlay, animationUtils);
            case SHADOW -> new ShadowAnimation(overlay, animationUtils);
        };
    }

    private double[] attackCenter(boolean playerAttacking) {
        return playerAttacking ? playerCenter() : enemyCenter();
    }

    private double[] defenseCenter(boolean playerAttacking) {
        return playerAttacking ? enemyCenter() : playerCenter();
    }

    private double[] playerCenter() {
        return spriteCenter(playerSprite, PLAYER_CENTER_X, PLAYER_CENTER_Y);
    }

    private double[] enemyCenter() {
        return spriteCenter(enemySprite, ENEMY_CENTER_X, ENEMY_CENTER_Y);
    }

    private double[] spriteCenter(ImageView sprite, double fallbackX, double fallbackY) {
        if (sprite == null) return new double[]{fallbackX, fallbackY};
        Bounds b = sprite.getBoundsInParent();
        return new double[]{b.getCenterX(), b.getCenterY()};
    }

    private static void runIfPresent(Runnable r) {
        if (r != null) r.run();
    }
}
