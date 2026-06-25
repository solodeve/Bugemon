DELETE FROM team_members
WHERE team_id IN (
    SELECT id
    FROM team
    WHERE team_type = ?
)
