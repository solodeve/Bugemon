package ulb.service.team;

import ulb.model.domain.Bugemon;
import ulb.model.domain.Element;
import ulb.model.team.Team;
import ulb.repository.IGameRepository;

import java.util.List;
import java.util.Random;

/**
 * Creates randomized enemy teams from the game catalog.
 *
 * <p>Extracted from generateTeam so that the
 * {@code Team} entity no longer depends on the repository layer.</p>
 */
public class EnemyTeamFactory {

    private final IGameRepository repository;

    public EnemyTeamFactory(IGameRepository repository) {
        this.repository = repository;
    }

    /**
     * Generates a randomized enemy team of at most {@code teamSize} Bugemons.
     *
     * @param teamSize maximum number of Bugemons in the team.
     * @param seed     seed for reproducible randomness.
     * @return a new {@link Team} named "Enemy Team".
     */
    public Team create(int teamSize, long seed) {
        Team randomTeam = new Team("Enemy Team");
        List<Bugemon> catalog = repository.getAllBugemons();
        List<Bugemon> allBugemons = catalog.stream().filter(b -> b.getElement() != Element.ALL).toList();
        int catalogSize = allBugemons.size();
        Random random = new Random(seed);

        for (int i = 0; i < teamSize && catalogSize > 0; i++) {
            int index = (int) (random.nextDouble() * catalogSize);
            randomTeam.add(new Bugemon(allBugemons.get(index)));
        }

        return randomTeam;
    }
}
