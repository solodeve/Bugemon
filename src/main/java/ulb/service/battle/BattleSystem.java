package ulb.service.battle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import ulb.controller.battle.towerMode.NoLevelupController;
import ulb.event.BattleEndListener;
import ulb.event.LevelUpListener;
import ulb.event.MessageListener;
import ulb.model.domain.BattleState;
import ulb.model.domain.Bugemon;
import ulb.model.domain.GameMode;
import ulb.model.domain.Skill;
import ulb.model.inventory.Item;
import ulb.model.special.DomainExpansion;
import ulb.model.team.Team;

import ulb.repository.IGameRepository;
import ulb.service.team.EnemyTeamFactory;

/**
 * Orchestrates a turn-based battle between the player and an enemy team.
 *
 * <p>This class is the public API for the battle subsystem. All heavy lifting
 * is delegated to focused helpers:</p>
 * <ul>
 *   <li>{@link BattleTurnExecutor} — turn execution, KO handling, and messaging.</li>
 *   <li>{@link BattleDomainState} — domain-expansion charge and active-domain state.</li>
 *   <li>{@link BattleOutcomeHandler} — post-battle rewards and level-up sequence.</li>
 * </ul>
 */
public class BattleSystem {

    private final List<BattleEndListener> onBattleEndListeners = new ArrayList<>();
    private final IGameRepository gameRepository;
    private final BattleDomainState domainState = new BattleDomainState();
    private final BattleTurnExecutor turnExecutor;
    private final BattleOutcomeHandler outcomeHandler;

    private Team playerTeam;
    private Team enemyTeam;
    private boolean hasWon;
    private String teamPersistenceType = GameMode.FREE_BATTLE.name();

    /**
     * Initializes the battle system with a data repository.
     */
    public BattleSystem(IGameRepository gameRepository) {
        this(gameRepository, new EnemyBattleAi());
    }

    public BattleSystem(IGameRepository gameRepository, EnemyBattleAi enemyBattleAi) {
        this.gameRepository = gameRepository;
        this.outcomeHandler = new BattleOutcomeHandler(gameRepository);
        this.turnExecutor = new BattleTurnExecutor(gameRepository, enemyBattleAi, domainState, this::battleOver);
    }

    /**
     * Sets which logical team type should be used when persisting the player team.
     * This allows FREE_BATTLE and NO_TOWER teams to be saved independently.
     */
    public void setTeamPersistenceType(String teamPersistenceType) {
        this.teamPersistenceType = (teamPersistenceType == null || teamPersistenceType.isBlank())
                ? GameMode.FREE_BATTLE.name() : teamPersistenceType;
    }

    public boolean isFreeBattleMode() {
        return GameMode.FREE_BATTLE.name().equals(teamPersistenceType);
    }

    public String getGameModeName() { return getGameMode().name(); }

    public GameMode getGameMode() {
        if (teamPersistenceType == null || teamPersistenceType.isBlank()) {
            return GameMode.FREE_BATTLE;
        }
        try {
            return GameMode.valueOf(teamPersistenceType);
        } catch (IllegalArgumentException e) {
            return GameMode.FREE_BATTLE;
        }
    }

    public void setOnBattleEnd(BattleEndListener action) {
        onBattleEndListeners.clear();
        if (action != null) onBattleEndListeners.add(action);
    }

    public void setOnMessageUpdate(MessageListener action) {
        turnExecutor.setMessageListener(action);
    }

    public void setOnLevelUp(LevelUpListener action) {
        outcomeHandler.setOnLevelUp(action);
    }

    public void setPlayerTeam(Team team) { this.playerTeam = team; }

    public Bugemon getActiveBugemonOfPlayer()    { return turnExecutor.getActivePlayerBugemon(); }
    public Bugemon getActiveBugemonOfEnemy()     { return turnExecutor.getActiveEnemyBugemon(); }
    public Team getPlayerTeam()                  { return playerTeam; }
    public Team getEnemyTeam()                   { return enemyTeam; }

    public List<Skill> getActivePlayerBugemonSkills() {
        Bugemon player = getActiveBugemonOfPlayer();
        return player == null ? List.of() : player.getSkills();
    }

    public boolean isPlayerInventoryEmpty() {
        return playerTeam == null || playerTeam.isInventoryEmpty();
    }

    public List<Item> getPlayerInventoryItems() {
        if (playerTeam == null || playerTeam.getInventory() == null) return null;
        return playerTeam.getInventoryItems();
    }

    public String getPlayerTeamName() {
        return playerTeam == null ? null : playerTeam.getName();
    }

    public Set<String> getEvolvedPlayerBugemonIds() {
        if (playerTeam == null) return Set.of();
        return playerTeam.getEvolvedMemberIds();
    }

    public void markActivePlayerBugemonEvolved() {
        Bugemon active = getActiveBugemonOfPlayer();
        if (active != null) {
            active.setEvolved(true);
        }
    }

    public String getActivePlayerBugemonId() {
        Bugemon active = getActiveBugemonOfPlayer();
        return active == null ? null : active.getId();
    }

    public String getActivePlayerBugemonName() {
        Bugemon active = getActiveBugemonOfPlayer();
        return active == null ? null : active.getName();
    }

    public boolean getHasWon()                   { return hasWon; }
    public BattleState getBattleState()          { return turnExecutor.getState(); }
    public int getPlayerSpecialCharge()          { return domainState.getPlayerSpecialCharge(); }
    public DomainExpansion getActiveDomain()     { return domainState.getActiveDomain(); }

    public boolean isPlayerActiveDomainCaster() {
        return domainState.isPlayerActiveDomainCaster(turnExecutor.getActivePlayerBugemon());
    }

    public String getActiveBackgroundPath()      { return domainState.getActiveBackgroundPath(); }

    public DomainExpansion getPlayerDomainExpansion() { return turnExecutor.getPlayerDomainExpansion(); }
    public DomainExpansion getEnemyDomainExpansion()  { return turnExecutor.getEnemyDomainExpansion(); }
    public DomainExpansion getDomainExpansionFor(Bugemon bugemon) { return turnExecutor.getDomainExpansionFor(bugemon); }

    public int getPlayerDomainCost() {
        DomainExpansion playerDomainExpansion = getPlayerDomainExpansion();
        return playerDomainExpansion == null ? 0 : playerDomainExpansion.cost();
    }

    public String getPlayerDomainName() {
        DomainExpansion playerDomainExpansion = getPlayerDomainExpansion();
        return playerDomainExpansion == null ? null : playerDomainExpansion.name();
    }

    public boolean canPlayerUseDomain() { return domainState.canPlayerUseDomain(getPlayerDomainExpansion()); }
    public boolean canEnemyUseDomain()  { return domainState.canEnemyUseDomain(getEnemyDomainExpansion()); }

    /** Delegates to {@link BattleOutcomeHandler} — used by NoLevelupController. */
    public Bugemon getCurrentLevelUpBugemon() { return outcomeHandler.getCurrentLevelUpBugemon(); }

    public Set<Bugemon> getWinningBugemons() {
        if (!hasWon || playerTeam == null) return Collections.emptySet();
        return playerTeam.getUsedBugemons();
    }

    public void enterSelectionState() { turnExecutor.enterSelectionState(); }

    public Team createRandomEnemyTeam() {
        return new EnemyTeamFactory(gameRepository).create(
                playerTeam == null ? 0 : playerTeam.size(), System.currentTimeMillis());
    }

    /**
     * Initializes the battle using the provided enemy team if present.
     *
     * @param enemyTeamOverride enemy team to use, or {@code null} to generate one
     */
    public void initializeBattleWithTeam(Team enemyTeamOverride) {
        this.enemyTeam = enemyTeamOverride != null ? enemyTeamOverride : createRandomEnemyTeam();
        if (playerTeam == null || playerTeam.isEmpty()) return;
        playerTeam.initializeInventoryContent(gameRepository.getStartingInventory());
        initializeBattleState(playerTeam, this.enemyTeam);
    }

    /**
     * Initializes a new battle session between two teams.
     *
     * @throws IllegalArgumentException if either team is null
     */
    public void initializeBattleState(Team playerTeam, Team enemyTeam) {
        if (playerTeam == null) throw new IllegalArgumentException("Player team cannot be null");
        if (enemyTeam == null) throw new IllegalArgumentException("Enemy team cannot be null");
        this.playerTeam = playerTeam;
        this.enemyTeam  = enemyTeam;
        this.hasWon     = false;
        outcomeHandler.reset();
        turnExecutor.initialize(playerTeam, enemyTeam);
    }

    /**
     * Concludes the battle: restores team HP, records the result, and delegates
     * the post-battle sequence to {@link BattleOutcomeHandler}.
     *
     * @param won {@code true} if the player won, {@code false} otherwise
     */
    public void battleOver(boolean won) {
        turnExecutor.forceEndState();
        if (isFreeBattleMode() && playerTeam != null) {
            playerTeam.restoreAllHp();
            playerTeam.resetBattleState();
        } else if (playerTeam != null) {
            playerTeam.resetBattleState();
        }
        hasWon = won;
        if (won) outcomeHandler.handleVictory(playerTeam, teamPersistenceType, onBattleEndListeners);
        else     outcomeHandler.handleDefeat(playerTeam, teamPersistenceType, onBattleEndListeners);
    }

    /** Kept for backward compatibility with upstream callers. */
    public void endBattleAndProcess(boolean won) { battleOver(won); }

    /**
     * Delegates to {@link BattleOutcomeHandler} to advance the level-up pagination.
     * Called by {@link NoLevelupController} after the player confirms a bonus.
     */
    public void continueHandleBattleOutcome() {
        outcomeHandler.continueAfterLevelUp(playerTeam, teamPersistenceType, onBattleEndListeners);
    }

    /** Switches the player's active Bugemon to the given one. */
    public void selectBugemon(Bugemon bugemon) { turnExecutor.selectBugemon(bugemon); }

    /**
     * Executes the player's chosen skill and resolves the full turn.
     *
     * @param index The 0-based index of the skill in the active player Bugemon's moveset.
     */
    public void executePlayerSkill(int index) { turnExecutor.executePlayerSkill(index); }

    /**
     * Executes the player's item usage as a full turn.
     *
     * @param item The item to use. Must not be {@code null}.
     */
    public void executePlayerItem(Item item) { turnExecutor.executePlayerItem(item); }

    /**
     * Executes the item selected by its position in the player's inventory.
     *
     * @param itemIndex The 0-based index of the item in the player's inventory.
     */
    public void executePlayerItemAt(int itemIndex) {
        List<Item> items = getPlayerInventoryItems();
        if (items == null) {
            throw new IllegalStateException("Cannot use an item without an initialized player inventory.");
        }
        if (itemIndex < 0 || itemIndex >= items.size()) {
            throw new IllegalArgumentException("Invalid item index: " + itemIndex);
        }
        executePlayerItem(items.get(itemIndex));
    }

    public void executePlayerDomain() { turnExecutor.executePlayerDomain(); }

    /**
     * Executes a single attack move: delegates to {@link TurnResolver}, posts all
     * result messages, and triggers KO logic if the target fainted.
     */
    public void runMove(Bugemon source, Bugemon target, Skill skill) {
        turnExecutor.runMove(source, target, skill);
    }

    public void checkBattleOver(Bugemon deadBugemon) { turnExecutor.checkBattleOver(deadBugemon); }
}
