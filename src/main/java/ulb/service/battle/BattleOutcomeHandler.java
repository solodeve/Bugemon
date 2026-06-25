package ulb.service.battle;

import ulb.event.BattleEndEvent;
import ulb.event.BattleEndListener;
import ulb.event.LevelUpEvent;
import ulb.event.LevelUpListener;
import ulb.exception.TeamPersistenceException;
import ulb.model.domain.Bugemon;
import ulb.model.domain.GameMode;
import ulb.model.team.Team;
import ulb.repository.IGameRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Handles the post-battle victory sequence: XP distribution, level-up pagination,
 * team persistence, and battle-end notification.
 *
 * <p>This component is stateful across the level-up screen: after a victory,
 * {@link #handleVictory} computes which Bugemons levelled up and starts the
 * pagination. {@link #continueAfterLevelUp} is called once per confirmed bonus
 * until the queue is exhausted, at which point it persists the team and fires
 * the end-of-battle listeners.</p>
 */
public class BattleOutcomeHandler {

    private final IGameRepository gameRepository;

    private LevelUpListener onLevelUp;
    private int levelUpBugemonIndex;
    private List<Bugemon> leveledUpBugemons = new ArrayList<>();
    private Bugemon currentLevelUpBugemon;

    public BattleOutcomeHandler(IGameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public void setOnLevelUp(LevelUpListener listener) {
        this.onLevelUp = listener;
    }

    public Bugemon getCurrentLevelUpBugemon() {
        return currentLevelUpBugemon;
    }

    /**
     * Resets all pagination state for a new battle.
     * Called by BattleSystem at the start of each battle.
     */
    public void reset() {
        levelUpBugemonIndex = 0;
        currentLevelUpBugemon = null;
        leveledUpBugemons = new ArrayList<>();
    }

    /**
     * Starts the post-victory sequence.
     *
     * <p>In free-battle mode, skips XP and fires the battle-end listeners immediately.
     * In NO Tower mode, distributes XP to winning Bugemons, then starts the level-up
     * pagination via {@link #continueAfterLevelUp}.</p>
     *
     * @param playerTeam      the player's team at the end of the battle
     * @param persistenceType the team type key used when saving (e.g. "FREE_BATTLE")
     * @param listeners       battle-end callbacks to fire once the sequence completes
     */
    public void handleVictory(Team playerTeam, String persistenceType, List<BattleEndListener> listeners) {
        if (isFreeBattle(persistenceType)) {
            reset();
            savePlayerTeam(playerTeam, persistenceType);
            fireListeners(listeners, true);
            return;
        }
        applyVictoryLevelUps(playerTeam);
        levelUpBugemonIndex = 0;
        continueAfterLevelUp(playerTeam, persistenceType, listeners);
    }

    private void applyVictoryLevelUps(Team playerTeam) {
        Set<Bugemon> winningBugemons = winningBugemons(playerTeam);
        leveledUpBugemons = new ArrayList<>();

        for (Bugemon bugemon : winningBugemons) {
            int levelBefore = bugemon.getLevel();
            bugemon.addXpAndApplyLevelUps();
            int levelDiff = bugemon.getLevel() - levelBefore;

            for (int i = 0; i < levelDiff; i++) {
                leveledUpBugemons.add(bugemon);
            }
        }
    }

    /**
     * Advances to the next queued level-up, or finishes the sequence if none remain.
     *
     * <p>Called by {@link ulb.controller.battle.towerMode.NoLevelupController} after the player confirms
     * a stat bonus, and internally by {@link #handleVictory} to start the pagination.</p>
     *
     * @param playerTeam      the player's team (needed for final save)
     * @param persistenceType the team type key for saving
     * @param listeners       battle-end callbacks to fire when the sequence is complete
     */
    public void continueAfterLevelUp(Team playerTeam, String persistenceType, List<BattleEndListener> listeners) {
        if (levelUpBugemonIndex < leveledUpBugemons.size()) {
            Bugemon bugemon = leveledUpBugemons.get(levelUpBugemonIndex);
            handleRewards(bugemon);
            levelUpBugemonIndex++;
            currentLevelUpBugemon = bugemon;
            if (onLevelUp != null) {
                onLevelUp.onLevelUp(new LevelUpEvent(bugemon));
            }
            return;
        }
        currentLevelUpBugemon = null;
        savePlayerTeam(playerTeam, persistenceType);
        fireListeners(listeners, true);
    }

    /**
     * Persists the player team under the given type key.
     */
    public void savePlayerTeam(Team playerTeam, String persistenceType) {
        if (playerTeam == null) {
            return;
        }
        try {
            gameRepository.saveTeams(playerTeam, persistenceType);
        } catch (TeamPersistenceException e) {
            throw new IllegalStateException("Unable to save teams after battle.", e);
        }
    }

    /**
     * Handles the post-defeat sequence: saves the team and fires the battle-end listeners.
     *
     * @param playerTeam      the player's team
     * @param persistenceType the team type key for saving
     * @param listeners       battle-end callbacks to fire
     */
    public void handleDefeat(Team playerTeam, String persistenceType,
                             List<BattleEndListener> listeners) {
        currentLevelUpBugemon = null;
        savePlayerTeam(playerTeam, persistenceType);
        fireListeners(listeners, false);
    }


    public void handleRewards(Bugemon leveledUpBugemon) {
            gameRepository.getLevelRewardByLevel(leveledUpBugemon.getLevel()).ifPresent(leveledUpBugemon::learnSkill);
    }


    private boolean isFreeBattle(String persistenceType) {
        return GameMode.FREE_BATTLE.name().equals(persistenceType);
    }

    private Set<Bugemon> winningBugemons(Team playerTeam) {
        return playerTeam == null ? java.util.Collections.emptySet() : playerTeam.getUsedBugemons();
    }

    private void fireListeners(List<BattleEndListener> listeners, boolean won) {
        if (listeners == null || listeners.isEmpty()) {
            return;
        }
        List<BattleEndListener> snapshot = new ArrayList<>(listeners);
        for (BattleEndListener listener : snapshot) {
            listener.onBattleEnd(new BattleEndEvent(won));
        }
    }
}
