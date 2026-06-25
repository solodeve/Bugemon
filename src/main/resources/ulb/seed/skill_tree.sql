CREATE TABLE IF NOT EXISTS skill_tree_node (
    id           TEXT    PRIMARY KEY,
    nom          TEXT    NOT NULL,
    description  TEXT,
    cout         INT     NOT NULL DEFAULT 0,
    max_niveau   INT     NOT NULL DEFAULT 1,
    position_x   INT,
    position_y   INT,
    deverrouille BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS skill_tree_prerequis (
    node_id      TEXT NOT NULL,
    prerequis_id TEXT NOT NULL,
    PRIMARY KEY (node_id, prerequis_id),
    FOREIGN KEY (node_id) REFERENCES skill_tree_node(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS skill_tree_effet (
    node_id        TEXT  PRIMARY KEY,
    type           TEXT,
    stat           TEXT,
    valeur         FLOAT,
    valeur_pourcent INT,
    quantite       INT,
    categorie      TEXT,
    type_cible     TEXT,
    FOREIGN KEY (node_id) REFERENCES skill_tree_node(id) ON DELETE CASCADE
);
