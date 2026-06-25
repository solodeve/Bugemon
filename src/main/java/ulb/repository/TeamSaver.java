package ulb.repository;

import ulb.configuration.SQLQueries;
import ulb.model.domain.Bugemon;
import ulb.model.team.Team;

import java.sql.*;
import java.util.List;

/** Handles the INSERT/DELETE logic for persisting teams to the database. */
class TeamSaver {

    private final String teamType;
    private final int userId;

    TeamSaver(String teamType, int userId) {
        this.teamType = teamType;
        this.userId = userId;
    }

    void save(Connection connection, List<Team> teams) throws SQLException {
        boolean hasTeamType     = hasColumn(connection, "team", "team_type");
        boolean hasUserTeam     = hasColumn(connection, "userTeam", "user_id") && userId > 0;
        boolean userTeamHasType = hasUserTeam && hasColumn(connection, "userTeam", "team_type");

        clearExistingTeams(connection, hasUserTeam, userTeamHasType, hasTeamType);

        String teamInsertSql     = hasTeamType ? SQLQueries.INSERT_TEAM_WITH_TYPE : SQLQueries.INSERT_TEAM_SIMPLE;
        String userTeamInsertSql = buildUserTeamInsertSql(hasUserTeam, userTeamHasType);

        PreparedStatement insertTeam = hasTeamType
                ? connection.prepareStatement(teamInsertSql)
                : connection.prepareStatement(teamInsertSql, Statement.RETURN_GENERATED_KEYS);
        PreparedStatement deleteMembers  = connection.prepareStatement(SQLQueries.DELETE_TEAM_MEMBERS_BY_TEAM_ID);
        PreparedStatement insertMember   = connection.prepareStatement(SQLQueries.INSERT_TEAM_MEMBER);
        PreparedStatement insertUserTeam = userTeamInsertSql != null
                ? connection.prepareStatement(userTeamInsertSql) : null;

        try (insertTeam; deleteMembers; insertMember; insertUserTeam) {
            for (Team team : teams) {
                int teamId = insertTeamAndGetId(insertTeam, team, hasTeamType);
                deleteMembers.setInt(1, teamId);
                deleteMembers.executeUpdate();
                insertTeamMembers(insertMember, teamId, team);
                if (insertUserTeam != null) insertUserTeamLinks(insertUserTeam, teamId, userTeamHasType);
            }
            insertMember.executeBatch();
            if (insertUserTeam != null) insertUserTeam.executeBatch();
        }
        cleanupOrphanTeams(connection, hasUserTeam, hasTeamType);
    }

    void deleteByName(Connection connection, String name) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(SQLQueries.DELETE_TEAM_BY_NAME)) {
            stmt.setString(1, name);
            stmt.setString(2, teamType);
            stmt.executeUpdate();
        }
    }

    private String buildUserTeamInsertSql(boolean hasUserTeam, boolean userTeamHasType) {
        if (!hasUserTeam) return null;
        return userTeamHasType ? SQLQueries.INSERT_USERTEAM_WITH_TYPE : SQLQueries.INSERT_USERTEAM_SIMPLE;
    }

    private void clearExistingTeams(Connection connection, boolean hasUserTeam,
                                    boolean userTeamHasType, boolean hasTeamType) throws SQLException {
        if (hasUserTeam) {
            if (userTeamHasType) {
                try (PreparedStatement stmt = connection.prepareStatement(SQLQueries.DELETE_USERTEAM_BY_USER_AND_TYPE)) {
                    stmt.setInt(1, userId);
                    stmt.setString(2, teamType);
                    stmt.executeUpdate();
                }
            } else {
                try (PreparedStatement stmt = connection.prepareStatement(SQLQueries.DELETE_USERTEAM_BY_USER)) {
                    stmt.setInt(1, userId);
                    stmt.executeUpdate();
                }
            }
        } else if (hasTeamType) {
            try (PreparedStatement stmt = connection.prepareStatement(SQLQueries.DELETE_TEAM_MEMBERS_BY_TYPE)) {
                stmt.setString(1, teamType);
                stmt.executeUpdate();
            }
            try (PreparedStatement stmt = connection.prepareStatement(SQLQueries.DELETE_TEAM_BY_TYPE)) {
                stmt.setString(1, teamType);
                stmt.executeUpdate();
            }
        } else {
            try (PreparedStatement stmt = connection.prepareStatement(SQLQueries.DELETE_TEAM_MEMBERS_ALL)) {
                stmt.executeUpdate();
            }
            try (PreparedStatement stmt = connection.prepareStatement(SQLQueries.DELETE_TEAM_ALL)) {
                stmt.executeUpdate();
            }
        }
    }

    private int insertTeamAndGetId(PreparedStatement insertTeam, Team team,
                                   boolean hasTeamType) throws SQLException {
        if (hasTeamType) {
            insertTeam.setString(1, team.getName());
            insertTeam.setString(2, teamType);
            insertTeam.setInt(3, userId);
            try (ResultSet rs = insertTeam.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        } else {
            insertTeam.setString(1, team.getName());
            insertTeam.executeUpdate();
            try (ResultSet keys = insertTeam.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
            }
        }
        return -1;
    }

    private void insertTeamMembers(PreparedStatement insertMember, int teamId, Team team) throws SQLException {
        List<Bugemon> members = team.getMembers();
        for (int slot = 0; slot < members.size(); slot++) {
            Bugemon b = members.get(slot);
            if (b != null && b.getId() != null) {
                insertMember.setInt(1, teamId);
                insertMember.setInt(2, slot);
                insertMember.setString(3, b.getId());
                insertMember.setInt(4, b.getLevel());
                insertMember.setLong(5, b.getXP());
                insertMember.addBatch();
            }
        }
    }

    private void insertUserTeamLinks(PreparedStatement insertUserTeam, int teamId,
                                     boolean userTeamHasType) throws SQLException {
        if (userTeamHasType) {
            insertUserTeam.setInt(1, teamId);
            insertUserTeam.setInt(2, userId);
            insertUserTeam.setString(3, teamType);
        } else {
            insertUserTeam.setInt(1, teamId);
            insertUserTeam.setInt(2, userId);
        }
        insertUserTeam.addBatch();
    }

    private void cleanupOrphanTeams(Connection connection, boolean hasUserTeam,
                                    boolean hasTeamType) throws SQLException {
        if (hasUserTeam && hasTeamType) {
            try (PreparedStatement stmt = connection.prepareStatement(SQLQueries.DELETE_ORPHAN_TEAMS)) {
                stmt.setString(1, teamType);
                stmt.executeUpdate();
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
