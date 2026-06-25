INSERT INTO bugemon_attaque(bugemon_id, attaque_id)
VALUES (?, ?)
ON CONFLICT (attaque_id, bugemon_id) DO NOTHING
