
CREATE TABLE IF NOT EXISTS film_genre
(
    film_id integer NOT NULL,
    genre_id integer NOT NULL
);

CREATE TABLE IF NOT EXISTS films
(
    id integer NOT NULL auto_increment primary key,
    name text NOT NULL,
    description text NOT NULL,
    release_date timestamp without time zone NOT NULL,
    duration integer NOT NULL,
    mpa_id integer NOT NULL
);

CREATE TABLE IF NOT EXISTS friends
(
    user_id integer NOT NULL,
    friend_id integer NOT NULL,
    status_id integer NOT NULL
);

CREATE TABLE IF NOT EXISTS friendship_status
(
    id integer NOT NULL auto_increment primary key,
    status text NOT NULL,
    CONSTRAINT status_unique UNIQUE (status)
);

CREATE TABLE IF NOT EXISTS genre
(
    id integer NOT NULL auto_increment primary key,
    title text NOT NULL,
    CONSTRAINT genre_title_unique UNIQUE (title)
);

CREATE TABLE IF NOT EXISTS likes
(
    film_id integer NOT NULL,
    user_id integer NOT NULL
);

CREATE TABLE IF NOT EXISTS mpa
(
    id integer NOT NULL auto_increment primary key,
    title text NOT NULL,
    CONSTRAINT mpa_title_unique UNIQUE (title)
);

CREATE TABLE IF NOT EXISTS users
(
    id integer NOT NULL auto_increment primary key,
    email text NOT NULL,
    login text NOT NULL,
    name text NOT NULL,
    birthday timestamp without time zone NOT NULL
);

ALTER TABLE IF EXISTS film_genre
    ADD CONSTRAINT IF NOT EXISTS film_foreign_key  FOREIGN KEY (film_id)
    REFERENCES films (id);

ALTER TABLE IF EXISTS film_genre
    ADD CONSTRAINT IF NOT EXISTS genre_foreign_key FOREIGN KEY (genre_id)
    REFERENCES genre (id);


ALTER TABLE IF EXISTS films
    ADD CONSTRAINT IF NOT EXISTS mpa_foreign_key FOREIGN KEY (mpa_id)
    REFERENCES mpa (id);


ALTER TABLE IF EXISTS friends
    ADD CONSTRAINT IF NOT EXISTS friend_foreign_key FOREIGN KEY (friend_id)
    REFERENCES users (id);


ALTER TABLE IF EXISTS friends
    ADD CONSTRAINT IF NOT EXISTS status_foreign_key FOREIGN KEY (status_id)
    REFERENCES friendship_status (id);


ALTER TABLE IF EXISTS friends
    ADD CONSTRAINT IF NOT EXISTS user_foreign_key FOREIGN KEY (user_id)
    REFERENCES users (id);


ALTER TABLE IF EXISTS likes
    ADD CONSTRAINT IF NOT EXISTS film_like_foreign_key FOREIGN KEY (film_id)
    REFERENCES films (id);


ALTER TABLE IF EXISTS likes
    ADD CONSTRAINT IF NOT EXISTS user_like_foreign_key FOREIGN KEY (user_id)
    REFERENCES users (id);

INSERT INTO mpa(id, title)
    SELECT * FROM (
        SELECT 1, 'G' UNION
        SELECT 2, 'PG' UNION
        SELECT 3, 'PG-13' UNION
        SELECT 4, 'R' UNION
        SELECT 5, 'NC-17'
    )
WHERE not exists(SELECT * FROM mpa);

INSERT INTO genre(id, title)
    SELECT * FROM (
        SELECT 1, 'Комедия' UNION
        SELECT 2, 'Драма' UNION
        SELECT 3, 'Мультфильм' UNION
        SELECT 4, 'Триллер' UNION
        SELECT 5, 'Документальный' UNION
        SELECT 6, 'Боевик'
    )
WHERE not exists(SELECT * FROM genre);

INSERT INTO friendship_status(id, status)
    SELECT * FROM (
        SELECT 1, 'неподтверждённая' UNION
        SELECT 2, 'подтверждённая'
    )
WHERE not exists(SELECT * FROM friendship_status);
