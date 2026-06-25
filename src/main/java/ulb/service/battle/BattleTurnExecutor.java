package ulb.service.battle;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import ulb.event.BattleMessage;
import ulb.event.BattleMessageType;
import ulb.event.MessageListener;
import ulb.model.domain.BattleState;
import ulb.model.domain.Bugemon;
import ulb.model.domain.Skill;
import ulb.model.domain.effect.StatModifierEffect;
import ulb.model.domain.effect.Target;
import ulb.model.domain.effect.Effect;
import ulb.model.inventory.Item;
import ulb.model.special.DomainBonus;
import ulb.model.special.DomainExpansion;
import ulb.model.team.Team;
import ulb.repository.IGameRepository;

/**
 * Executes battle turns: player actions (skill, item, domain), enemy AI,
 * KO handling, special-charge updates, and all in-battle messaging.
 *
 * <p>Calls back to {@link BattleSystem#battleOver(boolean)} through the
 * {@code onBattleOver} consumer when a team is fully defeated.</p>
 */
class BattleTurnExecutor {

    private final IGameRepository gameRepository;
    private final EnemyBattleAi enemyBattleAi;
    private final BattleDomainState domainState;
    private final Consumer<Boolean> onBattleOver;

    private BattleState state;
    private Bugemon activePlayerBugemon;
    private Bugemon activeEnemyBugemon;
    private Team playerTeam;
    private Team enemyTeam;
    private MessageListener messageUpdate;

    BattleTurnExecutor(IGameRepository gameRepository, EnemyBattleAi enemyBattleAi,
                       BattleDomainState domainState, Consumer<Boolean> onBattleOver) {
        this.gameRepository = gameRepository;
        this.enemyBattleAi = enemyBattleAi;
        this.domainState = domainState;
        this.onBattleOver = onBattleOver;
    }

    void setMessageListener(MessageListener listener) { this.messageUpdate = listener; }

    void reset() {
        state = BattleState.START;
        activePlayerBugemon = null;
        activeEnemyBugemon = null;
        playerTeam = null;
        enemyTeam = null;
        domainState.reset();
    }

    void initialize(Team playerTeam, Team enemyTeam) {
        this.playerTeam = playerTeam;
        this.enemyTeam = enemyTeam;
        state = BattleState.START;
        domainState.reset();

        playerTeam.clearUsedBugemons();
        enemyTeam.clearUsedBugemons();
        activePlayerBugemon = playerTeam.getHealthyBugemon().orElse(null);
        activeEnemyBugemon  = enemyTeam.getHealthyBugemon().orElse(null);

        if (activePlayerBugemon == null) { onBattleOver.accept(false); return; }
        if (activeEnemyBugemon  == null) { onBattleOver.accept(true);  return; }

        playerTeam.addUsedBugemon(activePlayerBugemon);
        enemyTeam.addUsedBugemon(activeEnemyBugemon);
        enterSelectionState();
    }

    BattleState getState()                  { return state; }
    Bugemon getActivePlayerBugemon()        { return activePlayerBugemon; }
    Bugemon getActiveEnemyBugemon()         { return activeEnemyBugemon; }

    DomainExpansion getPlayerDomainExpansion() {
        if (activePlayerBugemon == null || activePlayerBugemon.getDomainExpansionId() == null) return null;
        return gameRepository.getDomainExpansionById(activePlayerBugemon.getDomainExpansionId()).orElse(null);
    }

    DomainExpansion getEnemyDomainExpansion() {
        if (activeEnemyBugemon == null || activeEnemyBugemon.getDomainExpansionId() == null) return null;
        return gameRepository.getDomainExpansionById(activeEnemyBugemon.getDomainExpansionId()).orElse(null);
    }

    DomainExpansion getDomainExpansionFor(Bugemon bugemon) {
        if (bugemon == null || bugemon.getDomainExpansionId() == null) return null;
        return gameRepository.getDomainExpansionById(bugemon.getDomainExpansionId()).orElse(null);
    }

    void enterSelectionState() { state = BattleState.ACTION_SELECTION; }
    void forceEndState()       { state = BattleState.END; }

    /**
     * Switches the player's active Bugemon to the given one.
     */
    void selectBugemon(Bugemon bugemon) {
        if (playerTeam == null || !playerTeam.contains(bugemon)) return;
        if (!bugemon.isHealthy()) {
            postMessage(new BattleMessage(bugemon.getName() + " is down and can't fight.", BattleMessageType.ENEMY_ADVANTAGE));
            return;
        }
        if (bugemon == activePlayerBugemon) {
            postMessage(new BattleMessage(bugemon.getName() + " is already out in the field!", BattleMessageType.ENEMY_ADVANTAGE));
            return;
        }
        state = BattleState.PARTY_SCREEN;
        activePlayerBugemon = bugemon;
        playerTeam.addUsedBugemon(activePlayerBugemon);
        postMessage(new BattleMessage("Let's go " + bugemon.getName() + " !", BattleMessageType.PLAYER_ADVANTAGE, true, false));
        enterSelectionState();
    }

    /**
     * Executes the player's chosen skill and resolves the full turn.
     *
     * @param index The 0-based index of the skill in the active player Bugemon's moveset.
     */
    void executePlayerSkill(int index) {
        if (activePlayerBugemon == null || activeEnemyBugemon == null) return;
        List<Skill> skills = activePlayerBugemon.getSkills();
        if (index < 0 || index >= skills.size()) {
            postMessage(new BattleMessage("This skill isn't available.", BattleMessageType.ENEMY_ADVANTAGE));
            return;}
        state = BattleState.PERFORM_MOVE;
        Skill playerSkill = skills.get(index);
        if (enemyBattleAi.shouldUseDomain(domainState.canEnemyUseDomain(getEnemyDomainExpansion()))) {
            runMove(activePlayerBugemon, activeEnemyBugemon, playerSkill);
            if (state != BattleState.END) executeEnemyDomain();
        } else {
            Optional<Skill> enemySkill = enemyBattleAi.chooseSkill(activeEnemyBugemon);
            enemySkill.ifPresentOrElse(skill -> resolveTurn(playerSkill, skill),
                    () -> runMove(activePlayerBugemon, activeEnemyBugemon, playerSkill));}
        if (state != BattleState.END) enterSelectionState();
    }

    /**
     * Executes the player's item usage as a full turn.
     */
    void executePlayerItem(Item item) {
        if (activePlayerBugemon == null || activeEnemyBugemon == null || item == null) return;
        playerTeam.removeObjectFromInventory(item);
        postMessage(new BattleMessage(activePlayerBugemon.getName() + " uses " + item.name() + "!",
                "black", null, false, false, false, BattleMessageType.SYSTEM, false, false, false, false));
        applyItemEffect(item);
        if (!activePlayerBugemon.isHealthy()) {
            checkBattleOver(activePlayerBugemon);
            if (state == BattleState.END) return;
        }
        state = BattleState.PERFORM_MOVE;
        executeEnemyAction();
        if (state != BattleState.END) enterSelectionState();
    }

    private void applyItemEffect(Item item) {
        Effect effect = item.effect();
        if (effect != null) {
            activePlayerBugemon.applyEffect(effect);
            BattleMessage msg = item.getEffectDescription(activePlayerBugemon);
            if (msg != null) postMessage(msg);
        }
    }

    void executePlayerDomain() {
        if (activePlayerBugemon == null || activeEnemyBugemon == null) return;
        DomainExpansion domain = getPlayerDomainExpansion();
        if (domain == null) {
            postMessage(new BattleMessage(activePlayerBugemon.getName() + " has no Domain Expansion.", BattleMessageType.ENEMY_ADVANTAGE));
            return;
        }
        if (!domainState.canPlayerUseDomain(domain)) {
            postMessage(new BattleMessage("The special gauge is not high enough yet.", BattleMessageType.ENEMY_ADVANTAGE));
            return;
        }
        activatePlayerDomain(domain);
        executeEnemyAction();
        if (state != BattleState.END) enterSelectionState();
    }

    private void activatePlayerDomain(DomainExpansion domain) {
        state = BattleState.PERFORM_MOVE;
        domainState.activatePlayer(activePlayerBugemon, domain);
        postMessage(new BattleMessage(activePlayerBugemon.getName() + " unfolds " + domain.name() + "!", BattleMessageType.PLAYER_ADVANTAGE));
        postMessage(new BattleMessage(domain.description(), BattleMessageType.SYSTEM));
        applyDomainBonuses(activePlayerBugemon, domain);
    }

    private void executeEnemyAction() {
        if (activeEnemyBugemon == null || activePlayerBugemon == null || state == BattleState.END) return;
        if (enemyBattleAi.shouldUseDomain(domainState.canEnemyUseDomain(getEnemyDomainExpansion()))) {
            executeEnemyDomain();
            return;
        }
        enemyBattleAi.chooseSkill(activeEnemyBugemon)
                .ifPresent(skill -> runMove(activeEnemyBugemon, activePlayerBugemon, skill));
    }

    private void executeEnemyDomain() {
        DomainExpansion domain = getEnemyDomainExpansion();
        if (domain == null || activeEnemyBugemon == null) return;
        domainState.activateEnemy(activeEnemyBugemon, domain);
        postMessage(new BattleMessage("The opponent unfolds " + domain.name() + "!", BattleMessageType.ENEMY_ADVANTAGE));
        postMessage(new BattleMessage(domain.description(), BattleMessageType.SYSTEM));
        applyDomainBonuses(activeEnemyBugemon, domain);
    }

    // Move resolution

    private void resolveTurn(Skill playerSkill, Skill enemySkill) {
        PlannedMove playerMove = new PlannedMove(activePlayerBugemon, activeEnemyBugemon, playerSkill);
        PlannedMove enemyMove  = new PlannedMove(activeEnemyBugemon, activePlayerBugemon, enemySkill);
        boolean playerFirst = TurnResolver.shouldPlayerActFirst(playerSkill, enemySkill, activePlayerBugemon, activeEnemyBugemon);
        PlannedMove first  = playerFirst ? playerMove : enemyMove;
        PlannedMove second = first == playerMove ? enemyMove : playerMove;
        runMove(first.source(), first.target(), first.skill());
        if (state == BattleState.END) return;
        if (TurnResolver.canStillAct(second)) runMove(second.source(), second.target(), second.skill());
    }

    /**
     * Executes a single attack move, posts result messages, and triggers KO logic.
     */
    void runMove(Bugemon source, Bugemon target, Skill skill) {
        if (source == null || target == null || skill == null) return;
        int prevHp = target.getHp();
        TurnResolver.resolveMove(source, target, skill, source == activePlayerBugemon, domainState.getActiveDomain(), domainState.isGuaranteedHit(source), this::postMessage, () -> checkBattleOver(target));
        int damage = Math.max(0, prevHp - target.getHp());
        gainSpecialChargeForTarget(target, damage);
    }

    void checkBattleOver(Bugemon dead) {
        if (dead == null || playerTeam == null || enemyTeam == null) return;
        String clearedDomain = domainState.clearIfCasterFainted(dead);
        if (clearedDomain != null) {postMessage(new BattleMessage(clearedDomain + " fades away as its caster falls!", BattleMessageType.SYSTEM));}
        boolean isPlayerDead = playerTeam.contains(dead);
        Team activeTeam = isPlayerDead ? playerTeam : enemyTeam;
        Bugemon next = activeTeam.getHealthyBugemon().orElse(null);
        if (next != null) {
            if (isPlayerDead) activePlayerBugemon = next; else activeEnemyBugemon = next;
            postMessage(new BattleMessage(isPlayerDead ? "Your turn " + next.getName() + " !" : "The opponent sends " + next.getName() + " !", isPlayerDead ? BattleMessageType.PLAYER_ADVANTAGE : BattleMessageType.ENEMY_ADVANTAGE, true, false));
            activeTeam.addUsedBugemon(next);
            state = BattleState.ACTION_SELECTION;
        } else {
            if (isPlayerDead) activePlayerBugemon = null; else activeEnemyBugemon = null;
            onBattleOver.accept(!isPlayerDead);
        }
    }

    private void applyDomainBonuses(Bugemon bugemon, DomainExpansion domain) {
        if (bugemon == null || domain == null) return;
        for (DomainBonus bonus : domain.bonuses()) {
            if (bonus == null || bonus.stat() == null || bonus.modifier() == 0) continue;
            bugemon.applyEffect(new StatModifierEffect(Target.CASTER, bonus.stat(), bonus.modifier()));
            postMessage(new BattleMessage(bugemon.getName() + "'s " + bonus.stat().name().toLowerCase().replace('_', ' ') + " is empowered by the domain!", BattleMessageType.SYSTEM));
        }
    }

    private void gainSpecialChargeForTarget(Bugemon target, int damage) {
        if (target == null || damage <= 0) return;
        if (playerTeam != null && playerTeam.contains(target)) {
            domainState.gainPlayerCharge(damage, target.getMaxHp());
        } else if (enemyTeam != null && enemyTeam.contains(target)) {
            domainState.gainEnemyCharge(damage, target.getMaxHp());
        }
    }

    private void postMessage(BattleMessage message) {
        if (messageUpdate != null && message != null) messageUpdate.onMessageUpdate(message);
    }
}
