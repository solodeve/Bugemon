SELECT id, name
FROM team
WHERE LOWER(name) IN (${placeholders})
