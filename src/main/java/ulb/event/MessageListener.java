package ulb.event;

/**
 * Listener interface for battle message updates.
 * 
 * Implementations of this interface can subscribe to battle message events
 * and react accordingly when battle messages are sent or updated.
 */
public interface MessageListener {
    void onMessageUpdate(BattleMessage battleMessage);
}
