INSERT INTO object(id, nom, description, categorie, sprite)
VALUES (?, ?, ?, ?, ?)
ON CONFLICT (id) DO NOTHING
