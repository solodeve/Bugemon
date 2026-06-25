INSERT INTO domain_expansion(id, nom, description, background_sprite, boosted_element, cost)
VALUES (?, ?, ?, ?, ?, ?)
ON CONFLICT (id) DO NOTHING
