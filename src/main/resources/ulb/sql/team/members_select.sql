SELECT slot, bugemon_id, niveau, xp
FROM team_members
WHERE team_id = ?
ORDER BY slot
