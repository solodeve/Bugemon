SELECT id, name
FROM team
WHERE team_type = ? AND user_id = ?
ORDER BY name
