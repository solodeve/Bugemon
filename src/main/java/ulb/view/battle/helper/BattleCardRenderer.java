package ulb.view.battle.helper;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import ulb.model.domain.Element;
import ulb.view.battle.state.BattleUiState;
import ulb.view.common.SpriteLoader;

/**
 * Renders the battle HUD cards (names, elements, HP/XP, and sprites) from UI state.
 * Handles sprite swaps, HP bar animations, and player evolution visuals.
 */
public final class BattleCardRenderer {
    private final Label playerNameLabel;
    private final Label enemyNameLabel;
    private final Label playerElementLabel;
    private final Label enemyElementLabel;
    private final Label playerHpLabel;
    private final Label enemyHpLabel;
    private final ProgressBar playerHpBar;
    private final ProgressBar enemyHpBar;
    private final ImageView playerSprite;
    private final ImageView enemySprite;
    private final ProgressBar playerXpBar;
    private final Label playerXpLabel;
    private final SpriteAnimator spriteAnimator;
    private final EvolutionHandler evolutionHandler;
    private final SpriteLoader spriteLoader;
    private String lastPlayerBugemonId;
    private String lastEnemyBugemonId;

    public BattleCardRenderer(
            Label playerNameLabel,
            Label enemyNameLabel,
            Label playerElementLabel,
            Label enemyElementLabel,
            Label playerHpLabel,
            Label enemyHpLabel,
            ProgressBar playerHpBar,
            ProgressBar enemyHpBar,
            ImageView playerSprite,
            ImageView enemySprite,
            ProgressBar playerXpBar,
            Label playerXpLabel,
            SpriteAnimator spriteAnimator,
            EvolutionHandler evolutionHandler,
            SpriteLoader spriteLoader
    ) {
        this.playerNameLabel = playerNameLabel;
        this.enemyNameLabel = enemyNameLabel;
        this.playerElementLabel = playerElementLabel;
        this.enemyElementLabel = enemyElementLabel;
        this.playerHpLabel = playerHpLabel;
        this.enemyHpLabel = enemyHpLabel;
        this.playerHpBar = playerHpBar;
        this.enemyHpBar = enemyHpBar;
        this.playerSprite = playerSprite;
        this.enemySprite = enemySprite;
        this.playerXpBar = playerXpBar;
        this.playerXpLabel = playerXpLabel;
        this.spriteAnimator = spriteAnimator;
        this.evolutionHandler = evolutionHandler;
        this.spriteLoader = spriteLoader;
    }

    public boolean hasPlayerSwap(BattleUiState state) {
        return state != null
                && state.player() != null
                && !java.util.Objects.equals(lastPlayerBugemonId, state.playerId());
    }

    public void updateBugemonCard(BattleUiState.BugemonCardState bugemonState, boolean isPlayer, boolean isFreeBattle, boolean suppressHpAnimation) {
        Label nameLabel = isPlayer ? playerNameLabel : enemyNameLabel;
        Label elementLabel = isPlayer ? playerElementLabel : enemyElementLabel;
        Label hpLabel = isPlayer ? playerHpLabel : enemyHpLabel;
        ProgressBar hpBar = isPlayer ? playerHpBar : enemyHpBar;
        ImageView spriteView = isPlayer ? playerSprite : enemySprite;

        if (bugemonState == null) {
            return;
        }

        String oldId = isPlayer ? lastPlayerBugemonId : lastEnemyBugemonId;
        boolean bugemonSwapped = oldId != null && !oldId.equals(bugemonState.id());

        if (spriteAnimator != null) {
            if (bugemonSwapped) spriteAnimator.resetSprite(isPlayer);
            if (isPlayer) lastPlayerBugemonId = bugemonState.id();
            else lastEnemyBugemonId = bugemonState.id();
        }

        if (isFreeBattle) {
            nameLabel.setText(bugemonState.name());
        } else {
            nameLabel.setText(isPlayer ? bugemonState.name() + " Lvl: " + bugemonState.level() : bugemonState.name());
        }

        Element bugemonElement = bugemonState.element();
        elementLabel.setText(bugemonState.getElementDisplayName());
        int currentHp = Math.max(0, bugemonState.currentHp());
        int maxHp = Math.max(0, bugemonState.maxHp());
        hpLabel.setText("HP : " + currentHp + " / " + maxHp);
        double targetProgress = maxHp == 0 ? 0.0 : Math.max(0.0, Math.min(1.0, (double) currentHp / maxHp));
        if (bugemonSwapped || suppressHpAnimation) hpBar.setProgress(targetProgress);
        else animateProgressBar(hpBar, targetProgress);

        if (isPlayer) {
            evolutionHandler.updatePlayerSprite(bugemonState, bugemonElement);
            return;
        }
        spriteView.setImage(spriteLoader.loadSpriteForBugemon(bugemonState.sprite(), bugemonElement, false, false).orElse(null));
    }

    public void updateXpBar(double progress, long currentXp, long xpForNextLevel) {
        if (playerXpBar == null) {
            return;
        }

        animateProgressBar(playerXpBar, progress);
        if (playerXpLabel != null) {
            playerXpLabel.setText("XP : " + currentXp + " / " + xpForNextLevel);
        }
    }

    private void animateProgressBar(ProgressBar bar, double targetProgress) {
        if (bar == null) return;
        if (bar.getProgress() < 0) {
            bar.setProgress(targetProgress);
            return;
        }
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(bar.progressProperty(), bar.getProgress())),
                new KeyFrame(Duration.millis(400), new KeyValue(bar.progressProperty(), targetProgress))
        );
        timeline.play();
    }
}
