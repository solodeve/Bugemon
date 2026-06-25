SELECT lr.niveau,
       a.id, a.nom, a.type, a.description, a.puissance, a.precision, a.priorite, a.est_magique
FROM level_reward lr
JOIN attaque a ON a.id = lr.attaque_id
ORDER BY lr.niveau
