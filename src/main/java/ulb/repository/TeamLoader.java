package ulb.repository;

import ulb.configuration.SQLQueries;
import ulb.model.domain.Bugemon;
import ulb.model.domain.Level;
import ulb.model.team.Team;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** Handles the SELECT logic for loading teams from the database. */
class TeamLoader {

    private final String teamType;
    private final int userId;

    TeamLoader(String teamType, int userId) {
        this.teamType = teamType;
        this.userId = userId;
    }

    List<Team> load(Connection connection, IBugemonCatalogRepository catalog) throws SQLException {
        List<Team> teams = new ArrayList<>();
        boolean hasTeamType     = hasColumn(connection, "team", "team_type");
        boolean hasUserId       = hasColumn(connection, "team", "user_id");
        boolean hasUserTeam     = hasColumn(connection, "userTeam", "user_id") && userId > 0;
        boolean userTeamHasType = hasUserTeam && hasColumn(connection, "userTeam", "team_type");
        String sql = buildTeamSelectSql(hasUserTeam, userTeamHasType, hasTeamType, hasUserId);

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            applyTeamSelectParameters(stmt, hasUserTeam, userTeamHasType, hasTeamType, hasUserId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int teamId      = rs.getInt("id");
                    String teamName = rs.getString("name");
                    Team team = new Team(teamName);
                    loadTeamMembers(connection, teamId, team, catalog);
                    teams.add(team);
                }
            }
        }
        return teams;
    }

    private String buildTeamSelectSql(boolean hasUserTeam, boolean userTeamHasType,
                                      boolean hasTeamType, boolean hasUserId) {
        if (hasUserTeam) {
            if (userTeamHasType) return SQLQueries.TEAM_SELECT_BY_USER_AND_TYPE;
            if (hasTeamType)     return SQLQueries.TEAM_SELECT_BY_USER_AND_TEAMTYPE;
            return SQLQueries.TEAM_SELECT_BY_USER;
        }
        if (hasTeamType && hasUserId) return SQLQueries.TEAM_SELECT_BY_TYPE_AND_USER;
        if (hasTeamType)              return SQLQueries.TEAM_SELECT_BY_TYPE;
        return SQLQueries.TEAM_SELECT_ALL;
    }

    private void applyTeamSelectParameters(PreparedStatement stmt, boolean hasUserTeam,
                                           boolean userTeamHasType, boolean hasTeamType,
                                           boolean hasUserId) throws SQLException {
        if (hasUserTeam) {
            stmt.setInt(1, userId);
            if (userTeamHasType || hasTeamType) stmt.setString(2, teamType);
        } else if (hasTeamType && hasUserId) {
            stmt.setString(1, teamType);
            stmt.setInt(2, userId);
        } else if (hasTeamType) {
            stmt.setString(1, teamType);
        }
    }

    private void loadTeamMembers(Connection connection, int teamId, Team team,
                                 IBugemonCatalogRepository catalog) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(SQLQueries.TEAM_MEMBERS_SELECT)) {
            stmt.setInt(1, teamId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String bugemonId = rs.getString("bugemon_id");
                    int niveau = rs.getInt("niveau");
                    long xp    = rs.getLong("xp");
                    if (bugemonId != null) {
                        catalog.getBugemonById(bugemonId).ifPresent(b -> {
                            Bugemon member = b.copy();
                            member.setLevel(new Level(niveau, (int) xp));
                            team.add(member);
                        });
                    }
                }
            }
        }
    }

    private boolean hasColumn(Connection connection, String tableName, String columnName) throws SQLException {
        DatabaseMetaData md = connection.getMetaData();
        for (String name : new String[]{tableName, tableName.toLowerCase(), tableName.toUpperCase()}) {
            try (ResultSet cols = md.getColumns(null, null, name, columnName)) {
                if (cols.next()) return true;
            }
        }
        return false;
    }
}
