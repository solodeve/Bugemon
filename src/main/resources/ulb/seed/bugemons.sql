CREATE TABLE IF NOT EXISTS bugemon (
    id TEXT PRIMARY KEY,

    nom TEXT NOT NULL,
    type TEXT NOT NULL,
    pv INT NOT NULL,
    attaque INT NOT NULL,
    defense INT NOT NULL,
    magic_attaque INT NOT NULL DEFAULT 0,
    magic_defense INT NOT NULL DEFAULT 0,
    initiative INT NOT NULL,
    sprite TEXT,
    starter BOOLEAN DEFAULT FALSE,
    domain_expansion_id TEXT
);

-- Upgrade existing databases
ALTER TABLE bugemon ADD COLUMN IF NOT EXISTS magic_attaque INT NOT NULL DEFAULT 0;
ALTER TABLE bugemon ADD COLUMN IF NOT EXISTS magic_defense INT NOT NULL DEFAULT 0;
ALTER TABLE bugemon ADD COLUMN IF NOT EXISTS domain_expansion_id TEXT;

CREATE TABLE IF NOT EXISTS bugemon_attaque (
    bugemon_id TEXT NOT NULL,
    attaque_id TEXT NOT NULL,

    PRIMARY KEY (attaque_id, bugemon_id),
    FOREIGN KEY (attaque_id) REFERENCES attaque(id),
    FOREIGN KEY (bugemon_id) REFERENCES bugemon(id)
);