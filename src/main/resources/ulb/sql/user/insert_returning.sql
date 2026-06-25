INSERT INTO "user"(name, password_hash)
VALUES (?, ?)
RETURNING id, name
