package ulb.controller.session;

import ulb.model.user.User;
import ulb.repository.GameRepository;

/**
 * Manages the user session lifecycle.
 *
 * <p>This class owns the single responsibility of storing and clearing the
 * authenticated user inside the shared {@link GameRepository}, keeping
 * login/logout logic out of {@link ulb.controller.ApplicationController}.</p>
 */
public class SessionManager {

    private final GameRepository gameRepository;

    public SessionManager(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    /**
     * Stores the authenticated user as the active session user.
     *
     * @param user the user that just logged in
     */
    public void login(User user) {
        gameRepository.setCurrentUser(user);
    }

    /**
     * Clears the active session by replacing the current user with an
     * anonymous (empty) user.
     */
    public void logout() {
        gameRepository.setCurrentUser(new User());
    }
}
