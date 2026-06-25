package ulb.service.login;

import ulb.model.infrastructure.DatabaseBugemon;
import ulb.model.infrastructure.UserDao;
import ulb.model.user.User;

/**
 * Service for handling user authentication and registration.
 * 
 * Manages user login and registration operations, including credential validation
 * and database configuration checks.
 */
public class LoginService {
    private final UserDao userDao;
    private final DatabaseBugemon databaseBugemon;

    public LoginService(UserDao userDao, DatabaseBugemon databaseBugemon) {
        this.userDao = userDao;
        this.databaseBugemon = databaseBugemon;
    }

    public User login(String username, String password) {
        validateCredentials(username, password);
        ensureDatabaseConfigured();

        return userDao.authenticate(username, password)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Identifiants invalides. Verifie ton nom d'utilisateur et ton mot de passe."
                ));
    }

    public User register(String username, String password) {
        validateCredentials(username, password);
        ensureDatabaseConfigured();

        if (userDao.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Ce nom d'utilisateur existe deja.");
        }

        return userDao.create(username, password);
    }

    /**
     * Validates user credentials for format and length requirements.
     * 
     * @param username the username to validate
     * @param password the password to validate
     * @throws IllegalArgumentException if credentials do not meet requirements
     */
    private void validateCredentials(String username, String password) {
        if (username == null || password == null || username.isBlank() || password.isBlank()) {
            throw new IllegalArgumentException("Informations invalides.");
        }

        if (password.length() < 4) {
            throw new IllegalArgumentException("Le mot de passe doit contenir au moins 4 caracteres.");
        }
    }

    /**
     * Ensures the database is properly configured before operations.
     * 
     * @throws IllegalStateException if the database is not configured
     */
    private void ensureDatabaseConfigured() {
        if (!databaseBugemon.isConfigured()) {
            throw new IllegalStateException(
                    "La base Neon n'est pas configuree. Verifie db_url, db_user et db_password."
            );
        }
    }

}