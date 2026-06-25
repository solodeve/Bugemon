package ulb.event;

/**
 * Listener interface for battle end events.
 * 
 * Implementations of this interface can subscribe to battle end events
 * and react accordingly when a battle concludes.
 */
public interface BattleEndListener {
    void onBattleEnd(BattleEndEvent event);
}
