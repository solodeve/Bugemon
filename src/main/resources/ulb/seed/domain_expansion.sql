CREATE TABLE IF NOT EXISTS domain_expansion (
    id                TEXT PRIMARY KEY,
    nom               TEXT NOT NULL,
    description       TEXT,
    background_sprite TEXT,
    boosted_element   TEXT,
    cost              INT  NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS domain_expansion_bonus (
    expansion_id TEXT NOT NULL,
    stat         TEXT NOT NULL,
    modificateur INT  NOT NULL,
    PRIMARY KEY (expansion_id, stat),
    FOREIGN KEY (expansion_id) REFERENCES domain_expansion(id) ON DELETE CASCADE
);
