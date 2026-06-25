package ulb.controller.session;

import java.io.IOException;

import ulb.exception.GameDataLoadingException;
import ulb.model.infrastructure.DatabaseBugemon;
import ulb.repository.GameRepository;

/**
 * Responsible for seeding the database and loading the core game data.
 *
 * <p>Seeds the database from SQL files, then loads the game catalog and floor
 * definitions from the database into a fresh {@link GameRepository}.</p>
 */
public class GameDataInitializer {

    /**
     * Seeds the database and loads all game data into a fresh repository.
     *
     * @return a fully initialised {@link GameRepository}
     * @throws GameDataLoadingException if seeding or loading fails
     */
    public GameRepository initialize() throws GameDataLoadingException {
        try {
            new DatabaseBugemon().executeSeedFiles();

            GameRepository repository = new GameRepository();
            repository.initialize();
            return repository;
        } catch (IOException | RuntimeException e) {
            throw new GameDataLoadingException("Unable to load game data from database.", e);
        }
    }
}
