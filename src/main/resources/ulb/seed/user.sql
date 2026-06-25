-- Use quoted identifier for the user table to avoid conflict with SQL keyword
CREATE TABLE IF NOT EXISTS "user" (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name TEXT NOT NULL,
    password_hash TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS userTeam (
    team_id INTEGER NOT NULL,
    user_id INTEGER NOT NULL,
    team_type VARCHAR(64) NOT NULL DEFAULT 'FREE_BATTLE',
    PRIMARY KEY (team_id, user_id, team_type),
    FOREIGN KEY (team_id) REFERENCES team(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES "user"(id) ON DELETE CASCADE
);

-- Ensure older DBs get the team_type column if the table pre-exists
ALTER TABLE userTeam ADD COLUMN IF NOT EXISTS team_type VARCHAR(64) NOT NULL DEFAULT 'FREE_BATTLE';

CREATE TABLE IF NOT EXISTS user_unlocked_bugemon (
    user_id INTEGER NOT NULL,
    bugemon_id TEXT NOT NULL,
    PRIMARY KEY (user_id, bugemon_id),
    FOREIGN KEY (user_id) REFERENCES "user"(id) ON DELETE CASCADE,
    FOREIGN KEY (bugemon_id) REFERENCES bugemon(id) ON DELETE CASCADE
);

-- CREATE TABLE IF NOT EXISTS inventory (
--     object_id TEXT NOT NULL,
--     user_id INTEGER NOT NULL,
--     PRIMARY KEY (object_id, user_id),
--     FOREIGN KEY (object_id) REFERENCES object(id) ON DELETE CASCADE,
--     FOREIGN KEY (user_id) REFERENCES "user"(id) ON DELETE CASCADE
-- );
