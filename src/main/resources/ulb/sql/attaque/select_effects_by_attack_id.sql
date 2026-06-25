SELECT e.type, e.cible, e.stat, e.modificateur, e.valeur
FROM effet e
JOIN effet_attaque ea ON ea.effet_id = e.id
WHERE ea.attaque_id = ?
