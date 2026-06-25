DELETE FROM team
WHERE team_type = ?
  AND id NOT IN (
      SELECT team_id
      FROM userTeam
  )
