CREATE TABLE IF NOT EXISTS leaderboard (
    id SERIAL PRIMARY KEY,
    player_id INTEGER NOT NULL,
    score INTEGER NOT NULL,
    floor_reached INTEGER NOT NULL DEFAULT 0,
    combats_won INTEGER NOT NULL DEFAULT 0,
    bugemons_defeated INTEGER NOT NULL DEFAULT 0,
    bugemons_lost INTEGER NOT NULL DEFAULT 0,
    flawless_floors INTEGER NOT NULL DEFAULT 0,
    FOREIGN KEY (player_id) REFERENCES "user"(id) ON DELETE CASCADE
);
