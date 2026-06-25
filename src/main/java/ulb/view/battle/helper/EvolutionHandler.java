package ulb.view.battle.helper;

import javafx.animation.PauseTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import ulb.configuration.Configuration;
import ulb.model.domain.Element;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import ulb.view.battle.state.BattleUiState;
import ulb.view.common.SpriteLoader;

/**
 * Triggers and tracks the player Bugemon's evolution during battle.
 *
 * <p>Evolution fires at most once per Bugemon per session (tracked by ID in {@link #evolvedIds}).
 * It triggers when HP drops below {@code EVOLUTION_HP_THRESHOLD} and a video exists for that ID.
 * After the video, the sprite switches to the evolved artwork and flips from front to back view
 * after a short pause.
 *
 * <p>Setup order: {@link #setEvolutionPlayer} → {@link #setCallbacks} → {@link #restoreEvolvedState}.
 */
public class EvolutionHandler {

    private static final Duration FRONT_SPRITE_DURATION  = Duration.seconds(1.5);
    private static final int EVOLUTION_HP_THRESHOLD = 20;

    private final ImageView playerSprite;
    private final SpriteLoader spriteLoader;

    private EvolutionPlayer evolutionPlayer;
    private final PauseTransition spriteTransition;

    private final Set<String> evolvedIds = new HashSet<>();
    private boolean triggered;
    private String spriteOverride;
    private String displayedId;
    private boolean backSpriteVisible;

    private Runnable onEvolutionCompleted;
    private Runnable onControlsDisable;
    private Runnable onControlsEnable;

    public EvolutionHandler(ImageView playerSprite, SpriteLoader spriteLoader) {
        this.playerSprite = playerSprite;
        this.spriteLoader = spriteLoader;
        this.spriteTransition = new PauseTransition(FRONT_SPRITE_DURATION);
    }

    public void setEvolutionPlayer(EvolutionPlayer evolutionPlayer) {
        this.evolutionPlayer = evolutionPlayer;
    }

    /**
     * @param onControlsDisable    called when the evolution video starts — disable battle UI
     * @param onControlsEnable     called when the video ends — re-enable battle UI
     * @param onEvolutionCompleted called when the video ends — notify the controller to
     *                             save the evolved state and continue the turn
     */
    public void setCallbacks(Runnable onControlsDisable, Runnable onControlsEnable, Runnable onEvolutionCompleted) {
        this.onControlsDisable = onControlsDisable;
        this.onControlsEnable = onControlsEnable;
        this.onEvolutionCompleted = onEvolutionCompleted;
    }

    /**
     * Pre-populates already-evolved IDs so that returning to a battle scene after a pause
     * does not re-trigger evolution for Bugemon that went through it earlier.
     */
    public void restoreEvolvedState(Set<String> ids) {
        evolvedIds.addAll(ids);
    }

    public void triggerShortcut(String currentBugemonId) {
        if (triggered || evolutionPlayer == null) return;
        if (currentBugemonId == null) return;
        if (evolutionPlayer.hasNoEvolutionVideo(currentBugemonId)) return;
        triggerEvolution(currentBugemonId);
    }

    /**
     * Triggers evolution when {@code 0 < player.currentHp() < EVOLUTION_HP_THRESHOLD}.
     * Guards are in place so evolution fires at most once per Bugemon per session.
     */
    public void checkConditions(BattleUiState.BugemonCardState player, BattleUiState.BugemonCardState enemy) {
        if (triggered || evolutionPlayer == null || player == null || enemy == null) return;
        if (evolutionPlayer.hasNoEvolutionVideo(player.id())) return;
        if (player.currentHp() < EVOLUTION_HP_THRESHOLD && player.currentHp() > 0) {
            triggerEvolution(player.id());
        }
    }

    /**
     * Updates the player sprite for the given Bugemon state.
     * On a Bugemon swap the front sprite shows first; after {@code FRONT_SPRITE_DURATION}
     * it flips to the back sprite to match the battle camera angle.
     * If the Bugemon has already evolved, the evolved artwork is used instead.
     */
    public void updatePlayerSprite(BattleUiState.BugemonCardState bugemonState, Element bugemonElement) {
        if (playerSprite == null) return;

        if (bugemonState == null) {
            stopTransition();
            displayedId = null;
            backSpriteVisible = false;
            playerSprite.setImage(null);
            return;
        }

        boolean bugemonChanged = !Objects.equals(displayedId, bugemonState.id());
        if (bugemonChanged) {
            boolean alreadyEvolved = evolvedIds.contains(bugemonState.id());
            spriteOverride = alreadyEvolved ? bugemonState.id() : null;
            triggered      = alreadyEvolved;
        }

        Optional<Image> frontSprite;
        Optional<Image> backSprite;
        if (spriteOverride != null) {
            String frontPath = Configuration.bugemonEvolvedSpriteResourcePath(spriteOverride + ".png");
            String backPath  = Configuration.bugemonBackSpriteResourcePath(spriteOverride + "_evolved.png");
            frontSprite = spriteLoader.tryLoadSprite(frontPath);
            backSprite  = spriteLoader.tryLoadSprite(backPath).or(() -> frontSprite);
        } else {
            frontSprite = spriteLoader.loadSpriteForBugemon(bugemonState.sprite(), bugemonElement, true, false);
            backSprite  = spriteLoader.loadSpriteForBugemon(bugemonState.sprite(), bugemonElement, true, true)
                    .or(() -> frontSprite);
        }

        if (bugemonChanged) {
            displayedId = bugemonState.id();
            backSpriteVisible = false;
            stopTransition();
            playerSprite.setImage(frontSprite.orElse(backSprite.orElse(null)));

            if (backSprite.isPresent()) {
                Image backImage = backSprite.get();
                spriteTransition.setOnFinished(e -> {
                    if (Objects.equals(displayedId, bugemonState.id())) {
                        playerSprite.setImage(backImage);
                        backSpriteVisible = true;
                    }
                });
                spriteTransition.playFromStart();
            }
            return;
        }

        playerSprite.setImage((backSpriteVisible ? backSprite : frontSprite.or(() -> backSprite)).orElse(null));
    }

    public String getDisplayedId() {
        return displayedId;
    }

    private void triggerEvolution(String bugemonId) {
        triggered = true;
        if (onControlsDisable != null) onControlsDisable.run();
        evolutionPlayer.play(bugemonId, () -> {
            showEvolvedSprite(bugemonId);
            if (onControlsEnable != null) onControlsEnable.run();
            if (onEvolutionCompleted != null) onEvolutionCompleted.run();
        });
    }

    private void showEvolvedSprite(String evolvedSpriteName) {
        spriteOverride = evolvedSpriteName;
        evolvedIds.add(evolvedSpriteName);

        String frontPath = Configuration.bugemonEvolvedSpriteResourcePath(evolvedSpriteName + ".png");
        String backPath  = Configuration.bugemonBackSpriteResourcePath(evolvedSpriteName + "_evolved.png");
        Optional<Image> front = spriteLoader.tryLoadSprite(frontPath);
        Optional<Image> back  = spriteLoader.tryLoadSprite(backPath).or(() -> front);

        stopTransition();
        backSpriteVisible = false;
        playerSprite.setImage(front.orElse(back.orElse(null)));
        playerSprite.setVisible(true);

        if (back.isPresent()) {
            Image backImage = back.get();
            spriteTransition.setOnFinished(e -> {
                playerSprite.setImage(backImage);
                backSpriteVisible = true;
            });
            spriteTransition.playFromStart();
        }
    }

    private void stopTransition() {
        spriteTransition.stop();
        spriteTransition.setOnFinished(null);
    }
}
