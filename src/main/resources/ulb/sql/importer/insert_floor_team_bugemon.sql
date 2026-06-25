INSERT INTO floor_team_bugemon(team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic)
VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
ON CONFLICT (team_id, bugemon_id) DO NOTHING
