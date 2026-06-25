INSERT INTO team_members(team_id, slot, bugemon_id)
VALUES (?, ?, ?)
ON CONFLICT (team_id, slot) DO NOTHING
