package ulb.event;

/**
 * Listener interface for level up events.
 * 
 * Implementations of this interface can subscribe to level up events
 * and react accordingly when a character levels up.
 */
public interface LevelUpListener {
    void onLevelUp(LevelUpEvent event);
}
