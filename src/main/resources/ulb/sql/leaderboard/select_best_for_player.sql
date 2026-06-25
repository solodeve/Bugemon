SELECT id, score
FROM leaderboard
WHERE player_id = ?
ORDER BY score DESC, id ASC
LIMIT 1
