INSERT INTO skill_tree_node(id, nom, description, cout, max_niveau, position_x, position_y, deverrouille)
VALUES (?, ?, ?, ?, ?, ?, ?, ?)
ON CONFLICT (id) DO NOTHING
