CREATE TABLE IF NOT EXISTS screen
(
    screen_id     BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    screen_number INTEGER NOT NULL,
    rows          INTEGER NOT NULL,
    seats         INTEGER NOT NULL
);