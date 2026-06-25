package ulb.service.battle;

import ulb.configuration.Configuration;
import ulb.model.domain.Bugemon;
import ulb.model.special.DomainExpansion;

/**
 * Holds the mutable domain-expansion state for one battle session:
 * which domain is active, who cast it, and both sides' special-gauge charge.
 *
 * <p>All methods are pure state mutations or reads — no side effects.</p>
 */
class BattleDomainState {

    private DomainExpansion activeDomain;
    private Bugemon activeDomainCaster;
    private int playerSpecialCharge;
    private int enemySpecialCharge;

    void reset() {
        activeDomain = null;
        activeDomainCaster = null;
        playerSpecialCharge = 0;
        enemySpecialCharge = 0;
    }

    DomainExpansion getActiveDomain() { return activeDomain; }
    int getPlayerSpecialCharge()      { return playerSpecialCharge; }

    boolean isPlayerActiveDomainCaster(Bugemon activePlayer) {
        return activeDomainCaster != null && activeDomainCaster == activePlayer;
    }

    String getActiveBackgroundPath() {
        return activeDomain == null
                ? Configuration.DEFAULT_BATTLE_BACKGROUND
                : activeDomain.backgroundSpritePath();
    }

    boolean canPlayerUseDomain(DomainExpansion domain) {
        return domain != null && playerSpecialCharge >= domain.cost();
    }

    boolean canEnemyUseDomain(DomainExpansion domain) {
        return domain != null && enemySpecialCharge >= domain.cost();
    }

    /** Returns {@code true} if the given attacker is the current domain caster (guaranteed hit). */
    boolean isGuaranteedHit(Bugemon attacker) {
        return activeDomain != null && activeDomainCaster != null && activeDomainCaster == attacker;
    }

    void activatePlayer(Bugemon caster, DomainExpansion domain) {
        playerSpecialCharge = Math.max(0, playerSpecialCharge - domain.cost());
        activeDomain = domain;
        activeDomainCaster = caster;
    }

    void activateEnemy(Bugemon caster, DomainExpansion domain) {
        enemySpecialCharge = Math.max(0, enemySpecialCharge - domain.cost());
        activeDomain = domain;
        activeDomainCaster = caster;
    }

    void gainPlayerCharge(int damageTaken, int maxHp) {
        playerSpecialCharge = gainCharge(playerSpecialCharge, damageTaken, maxHp);
    }

    void gainEnemyCharge(int damageTaken, int maxHp) {
        enemySpecialCharge = gainCharge(enemySpecialCharge, damageTaken, maxHp);
    }

    private int gainCharge(int currentCharge, int damageTaken, int maxHp) {
        if (maxHp <= 0 || damageTaken <= 0) return currentCharge;
        int gainedCharge = Math.max(1, Math.round(damageTaken * 100f / maxHp));
        return Math.min(100, currentCharge + gainedCharge);
    }

    /**
     * Clears the active domain if its caster just fainted.
     *
     * @return the domain's name if it was cleared, {@code null} otherwise
     */
    String clearIfCasterFainted(Bugemon deadBugemon) {
        if (activeDomain == null || activeDomainCaster == null || activeDomainCaster != deadBugemon) return null;
        String name = activeDomain.name();
        activeDomain = null;
        activeDomainCaster = null;
        return name;
    }
}
