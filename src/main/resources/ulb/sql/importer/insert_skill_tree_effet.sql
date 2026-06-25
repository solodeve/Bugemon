INSERT INTO skill_tree_effet(node_id, type, stat, valeur, valeur_pourcent, quantite, categorie, type_cible)
VALUES (?, ?, ?, ?, ?, ?, ?, ?)
ON CONFLICT (node_id) DO NOTHING
