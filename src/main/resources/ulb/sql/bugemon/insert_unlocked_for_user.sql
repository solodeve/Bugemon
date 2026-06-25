INSERT INTO user_unlocked_bugemon(user_id, bugemon_id)
VALUES (?, ?)
    ON CONFLICT DO NOTHING
