DELETE FROM userTeam
WHERE user_id = ?
  AND team_id IN (
      SELECT id
      FROM team
      WHERE team_type = ?
  )
