package ulb.repository;

/** Contract for per-user Bugemon unlock state. */
public interface IUnlockRepository {

    boolean isBugemonUnlocked(String bugemonId);
    boolean unlockBugemon(String bugemonId);
}
