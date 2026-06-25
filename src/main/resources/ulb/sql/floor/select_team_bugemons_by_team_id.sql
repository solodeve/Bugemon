SELECT bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic
FROM floor_team_bugemon
WHERE team_id = ?
