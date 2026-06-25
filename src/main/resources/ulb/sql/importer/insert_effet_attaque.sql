INSERT INTO effet_attaque(effet_id, attaque_id)
VALUES (?, ?)
ON CONFLICT DO NOTHING
