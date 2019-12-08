CREATE TABLE telegram.clients
(
    chat_id bigint NOT NULL PRIMARY KEY,
    active boolean NOT NULL,
    first_name text,
    last_name text,
    user_name text NOT NULL,
    title text,
    type text NOT NULL
)