SELECT t.id, t.name
FROM team t
JOIN userTeam ut ON t.id = ut.team_id
WHERE ut.user_id = ?
ORDER BY t.name
