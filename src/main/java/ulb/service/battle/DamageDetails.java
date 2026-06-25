package ulb.service.battle;

/**
 * Record class used to encapsulate the detailed outcome of a single attack.
 */
public record DamageDetails(int damage, float critical, float typeEffectiveness, boolean missed) {
    public int getDamage() { return damage; }
    public float getCritical() { return critical; }
    public float getTypeEffectiveness() { return typeEffectiveness; }
    public boolean isMissed() { return missed; }
}
