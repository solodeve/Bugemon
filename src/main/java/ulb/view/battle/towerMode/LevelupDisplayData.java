package ulb.view.battle.towerMode;

import java.util.List;
import ulb.model.domain.Element;


/**
 * View-only representation of a level-up screen.
 *
 * <p>Contains the title, sprite information and bonus display data
 * required by LevelupView.</p>
 */
public record LevelupDisplayData(
        String title,
        String spritePath,
        Element element,
        List<BonusDisplayData> bonuses
) {}