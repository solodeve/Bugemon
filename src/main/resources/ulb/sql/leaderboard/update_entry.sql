UPDATE leaderboard
SET score = ?,
    floor_reached = ?,
    combats_won = ?,
    bugemons_defeated = ?,
    bugemons_lost = ?,
    flawless_floors = ?
WHERE id = ?
