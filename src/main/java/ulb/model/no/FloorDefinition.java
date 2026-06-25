package ulb.model.no;

import ulb.model.team.Team;

import java.util.List;

/**
 * Immutable description of a tower floor as loaded from {@code GameRepository}.
 * Used by the repository to return floor data and by NO/Floor to build
 * the floor structure.
 */
public record FloorDefinition(
        int floorNumber,
        List<Team> enemyTeams,
        Team bossTeam,
        String bossName,
        String bossSpritePath
) {}
