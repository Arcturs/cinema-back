CREATE TABLE IF NOT EXISTS movie
(
    movie_id     BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    duration     TIME NOT NULL,
    title        VARCHAR(100) NOT NULL,
    rating       FLOAT NOT NULL DEFAULT 1.0
);