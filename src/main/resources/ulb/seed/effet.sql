CREATE TABLE IF NOT EXISTS effet (
    id SERIAL PRIMARY KEY,
    type TEXT,
    valeur_pourcent INT,
    quantite INT,
    categorie INT,
    type_cible TEXT,
    cible TEXT,
    stat TEXT,
    valeur INT,
    modificateur INT,
    duree TEXT
);

CREATE TABLE IF NOT EXISTS effet_attaque (
    effet_id INT NOT NULL,
    attaque_id TEXT NOT NULL,

    PRIMARY KEY (effet_id, attaque_id),
    FOREIGN KEY (effet_id) REFERENCES effet(id),
    FOREIGN KEY (attaque_id) REFERENCES attaque(id)
);

CREATE TABLE IF NOT EXISTS effet_object (
    effet_id INT NOT NULL,
    object_id TEXT NOT NULL,

    PRIMARY KEY (effet_id, object_id),
    FOREIGN KEY (effet_id) REFERENCES effet(id),
    FOREIGN KEY (object_id) REFERENCES object(id)
);