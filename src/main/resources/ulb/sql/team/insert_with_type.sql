INSERT INTO team(name, team_type, user_id)
VALUES (?, ?, ?)
ON CONFLICT (name, team_type, user_id)
DO UPDATE SET name = EXCLUDED.name
RETURNING id
