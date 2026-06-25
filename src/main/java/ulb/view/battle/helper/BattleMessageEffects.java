package ulb.view.battle.helper;

import javafx.animation.PauseTransition;
import javafx.util.Duration;
import ulb.controller.SoundController;
import ulb.event.BattleMessageType;
import ulb.view.battle.animation.ElementAnimationPlayer;
import ulb.view.battle.state.ColouredMessage;

/**
 * Coordinates sounds, element animations, and sprite reactions for battle messages.
 * Also maps message types to their display colors.
 */
public final class BattleMessageEffects {
    private static final String DEFAULT_MESSAGE_COLOR = "black";
    private static final String PLAYER_MESSAGE_COLOR = "green";
    private static final String ENEMY_MESSAGE_COLOR = "red";
    private static final String SYSTEM_MESSAGE_COLOR = "blue";
    private static final String STATUS_CURE_MESSAGE_COLOR = "cyan";
    private static final String STATUS_RESET_MESSAGE_COLOR = "magenta";

    private final ElementAnimationPlayer elementAnimationPlayer;
    private final SpriteAnimator spriteAnimator;
    private PauseTransition pendingHurtTransition;

    public BattleMessageEffects(ElementAnimationPlayer elementAnimationPlayer, SpriteAnimator spriteAnimator) {
        this.elementAnimationPlayer = elementAnimationPlayer;
        this.spriteAnimator = spriteAnimator;
    }

    public void playAttackEffects(ColouredMessage message) {
        if (message.attackElement() == null) return;

        if (message.attackMagic()) {
            SoundController.getInstance().playMagicAttack();
        } else {
            SoundController.getInstance().playPhysicalAttack();
        }
        if (elementAnimationPlayer != null) {
            elementAnimationPlayer.play(message.attackElement(), message.playerAttacking(), null);
        }
        if (spriteAnimator != null) {
            spriteAnimator.playAttack(message.playerAttacking(), message.attackMagic());
        }
        if (message.impactSound()) {
            scheduleImpact(message.playerAttacking(), message.attackMagic());
        }
    }

    public void playPassiveSounds(ColouredMessage message) {
        if (message.healingSound()) SoundController.getInstance().playHealingItem();
        if (message.deathSound())   SoundController.getInstance().playDeath();
        if (message.switchSound())  SoundController.getInstance().playSwitch();
        if (message.boostSound())   SoundController.getInstance().playBoost();
    }

    public String colorForType(BattleMessageType type) {
        if (type == null) return DEFAULT_MESSAGE_COLOR;
        return switch (type) {
            case PLAYER_ADVANTAGE -> PLAYER_MESSAGE_COLOR;
            case ENEMY_ADVANTAGE  -> ENEMY_MESSAGE_COLOR;
            case SYSTEM           -> SYSTEM_MESSAGE_COLOR;
            case STATUS_CURE      -> STATUS_CURE_MESSAGE_COLOR;
            case STATUS_RESET     -> STATUS_RESET_MESSAGE_COLOR;
            case NEUTRAL          -> DEFAULT_MESSAGE_COLOR;
        };
    }

    private void scheduleImpact(boolean playerAttacking, boolean magic) {
        boolean playerHurt = !playerAttacking;
        int delayMs = magic ? 540 : 420;
        if (pendingHurtTransition != null) pendingHurtTransition.stop();
        pendingHurtTransition = new PauseTransition(Duration.millis(delayMs));
        pendingHurtTransition.setOnFinished(e -> {
            SoundController.getInstance().playImpact();
            if (spriteAnimator != null) spriteAnimator.playHurt(playerHurt);
        });
        pendingHurtTransition.play();
    }
}
