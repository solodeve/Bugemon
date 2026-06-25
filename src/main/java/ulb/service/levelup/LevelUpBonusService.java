package ulb.service.levelup;

import ulb.model.domain.status.Bonus;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Generates the stat-bonus options presented to the player on level-up.
 */
public class LevelUpBonusService {

    private static final int POINTS_AVAILABLE = 10;
    private static final int HP_MULTIPLIER = 2;
    private static final int INITIATIVE_MULTIPLIER = 2;
    private static final int BONUS_COUNT = 3;

    public List<Bonus> generateBonuses() {
        List<Bonus> bonuses = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < BONUS_COUNT; i++) {
            bonuses.add(generateSingleBonus(random));
        }
        return bonuses;
    }

    private Bonus generateSingleBonus(Random random) {
        int pointsAvailable = POINTS_AVAILABLE;
        int hp = 0, attack = 0, defense = 0, initiative = 0;

        while (pointsAvailable > 0) {
            switch (random.nextInt(4)) {
                case 0 -> { hp += HP_MULTIPLIER; pointsAvailable--; }
                case 1 -> { attack++; pointsAvailable--; }
                case 2 -> { defense++; pointsAvailable--; }
                case 3 -> { initiative += INITIATIVE_MULTIPLIER; pointsAvailable--; }
            }
        }

        return new Bonus(hp, attack, defense, initiative);
    }
}
