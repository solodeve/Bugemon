SELECT l.player_id, l.score, l.floor_reached, l.combats_won, l.bugemons_defeated, l.bugemons_lost, l.flawless_floors, u.name
FROM leaderboard l
JOIN "user" u ON u.id = l.player_id
WHERE l.id = (
    SELECT id FROM leaderboard
    WHERE player_id = l.player_id
    ORDER BY score DESC, id ASC
    LIMIT 1
)
ORDER BY l.score DESC
LIMIT ?
