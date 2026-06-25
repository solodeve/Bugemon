package ulb.controller.session;

import ulb.model.domain.GameMode;
import ulb.model.team.Team;

/**
 * Strategy interface for game-mode-specific behaviour.
 *
 * <p>Each concrete strategy encapsulates how a particular game mode starts a
 * game and how it reacts when the battle ends, removing the need for
 * if/else branching on a {@link GameMode} constant inside
 * {@link ulb.controller.ApplicationController}.</p>
 */
public interface GameModeStrategy {

    /**
     * Begins the game flow for the given player team.
     *
     * @param playerTeam the team chosen by the player
     */
    void startGameWithTeam(Team playerTeam);

    /**
     * Handles the outcome of a battle that just finished.
     *
     * @param won {@code true} if the player won the battle
     */
    void handleBattleFinished(boolean won);

    /**
     * Returns the {@link GameMode} constant that this strategy represents.
     * Used wherever a raw enum value is still required (e.g. persistence type).
     */
    GameMode getGameMode();
}
