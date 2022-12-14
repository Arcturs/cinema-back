CREATE TABLE IF NOT EXISTS session
(
    session_id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    movie_id   BIGINT REFERENCES movie (movie_id) ON DELETE CASCADE   NOT NULL,
    screen_id  BIGINT REFERENCES screen (screen_id) ON DELETE CASCADE NOT NULL,
    start_time TIMESTAMP                                              NOT NULL,
    end_time   TIMESTAMP                                              NOT NULL,
    price      INTEGER                                                NOT NULL
);