SELECT o.id, o.nom, o.description, o.categorie, o.sprite,
       e.type    AS effet_type,
       e.cible   AS effet_cible,
       e.stat    AS effet_stat,
       e.modificateur AS effet_modificateur,
       e.valeur  AS effet_valeur
FROM object o
LEFT JOIN effet_object eo ON eo.object_id = o.id
LEFT JOIN effet e ON e.id = eo.effet_id
