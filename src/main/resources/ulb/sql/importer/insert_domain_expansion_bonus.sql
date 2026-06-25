INSERT INTO domain_expansion_bonus(expansion_id, stat, modificateur)
VALUES (?, ?, ?)
ON CONFLICT (expansion_id, stat) DO NOTHING
