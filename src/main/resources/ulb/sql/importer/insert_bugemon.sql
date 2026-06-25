INSERT INTO bugemon(id, nom, type, pv, attaque, defense, magic_attaque, magic_defense, initiative, sprite, starter, domain_expansion_id)
VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
ON CONFLICT (id) DO UPDATE SET
    magic_attaque = EXCLUDED.magic_attaque,
    magic_defense = EXCLUDED.magic_defense,
    domain_expansion_id = EXCLUDED.domain_expansion_id
