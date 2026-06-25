package ulb.controller.battle.towerMode;

import java.util.List;

import ulb.model.domain.Bugemon;
import ulb.model.domain.status.Bonus;
import ulb.view.battle.towerMode.BonusDisplayData;
import ulb.view.battle.towerMode.LevelupDisplayData;

/**
 * Builds display data for the level-up screen.
 */
public class LevelupDisplayBuilder {
    /**
     * Converts the selected Bugemon and generated bonuses into display data
     * consumed by LevelupView.
     *
     * @param bugemon Bugemon that reached a new level
     * @param level new level reached by the Bugemon
     * @param bonuses generated bonus options
     * @return display-ready data for the level-up screen
     */
    public LevelupDisplayData build(Bugemon bugemon, int level, List<Bonus> bonuses) {
        List<BonusDisplayData> displayBonuses = bonuses.stream()
                .map(this::toDisplayBonus)
                .toList();

        return new LevelupDisplayData(
                bugemon.getName() + " reached level " + level + " !",
                bugemon.getSprite(),
                bugemon.getElement(),
                displayBonuses
        );
    }

    /**
     * Converts a domain bonus into a view-only bonus representation.
     */
    private BonusDisplayData toDisplayBonus(Bonus bonus) {
        return new BonusDisplayData(
                bonus.hp(),
                bonus.attack(),
                bonus.defense(),
                bonus.initiative(),
                formatBonus(bonus)
        );
    }

    private String formatBonus(Bonus bonus) {
        StringBuilder text = new StringBuilder();

        if (bonus.hp() > 0) {
            text.append("+").append(bonus.hp()).append(" HP\n");
        }
        if (bonus.attack() > 0) {
            text.append("+").append(bonus.attack()).append(" Attack\n");
        }
        if (bonus.defense() > 0) {
            text.append("+").append(bonus.defense()).append(" Defense\n");
        }
        if (bonus.initiative() > 0) {
            text.append("+").append(bonus.initiative()).append(" Initiative");
        }

        return text.toString().trim();
    }

}
