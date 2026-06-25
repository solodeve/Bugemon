INSERT INTO "user"(id, name, password_hash)
OVERRIDING SYSTEM VALUE
VALUES (?, ?, '')
ON CONFLICT (id) DO NOTHING
