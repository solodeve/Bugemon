CREATE TABLE IF NOT EXISTS object (
    id TEXT PRIMARY KEY,
    nom TEXT NOT NULL,
    description TEXT NOT NULL,
    categorie TEXT NOT NULL,
    sprite TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS starting_inventory (
    item_id  TEXT PRIMARY KEY,
    quantite INT  NOT NULL DEFAULT 1,
    FOREIGN KEY (item_id) REFERENCES object(id)
);