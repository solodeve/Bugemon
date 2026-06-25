INSERT INTO level_reward(niveau, attaque_id)
VALUES (?, ?)
ON CONFLICT (niveau) DO UPDATE SET attaque_id = EXCLUDED.attaque_id
