CREATE TABLE IF NOT EXISTS floor (
    numero INT PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS floor_team (
    id              SERIAL PRIMARY KEY,
    floor_numero    INT          NOT NULL,
    is_boss         BOOLEAN      NOT NULL DEFAULT FALSE,
    nom             VARCHAR(128),
    nombre_bugemons INT,
    FOREIGN KEY (floor_numero) REFERENCES floor(numero) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS floor_team_bugemon (
    team_id       INT          NOT NULL,
    bugemon_id    TEXT         NOT NULL,
    niveau        INT          NOT NULL DEFAULT 1,
    xp            INT          NOT NULL DEFAULT 0,
    hp            INT,
    attaque       INT,
    defense       INT,
    initiative    INT,
    max_hp        INT,
    attack_magic  INT,
    defense_magic INT,
    PRIMARY KEY (team_id, bugemon_id),
    FOREIGN KEY (team_id) REFERENCES floor_team(id) ON DELETE CASCADE
);
