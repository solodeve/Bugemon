INSERT INTO skill_tree_prerequis(node_id, prerequis_id)
VALUES (?, ?)
ON CONFLICT (node_id, prerequis_id) DO NOTHING
