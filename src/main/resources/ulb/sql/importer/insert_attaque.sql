INSERT INTO attaque(id, nom, type, description, puissance, precision, priorite, est_magique)
VALUES (?, ?, ?, ?, ?, ?, ?, ?)
ON CONFLICT (id) DO UPDATE SET
    precision = EXCLUDED.precision,
    priorite = EXCLUDED.priorite,
    est_magique = EXCLUDED.est_magique
