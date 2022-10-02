CREATE TABLE IF NOT EXISTS user_refresh_token
(
    refresh_token VARCHAR(40) PRIMARY KEY,
    date_expire   TIMESTAMP NOT NULL,
    user_id       BIGINT REFERENCES user_info (user_id) ON DELETE CASCADE NOT NULL
);