# java-filmorate

Схема БД:
![Screenshot](filmorate-diagram.png)

SQL-запросы для создания таблиц:

```sql
BEGIN;


CREATE TABLE IF NOT EXISTS public.films
(
    id integer NOT NULL,
    name text NOT NULL,
    description text NOT NULL,
    release_date timestamp without time zone NOT NULL,
    duration integer NOT NULL,
    mpa_id integer NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.users
(
    id integer NOT NULL,
    email text NOT NULL,
    login text NOT NULL,
    name text NOT NULL,
    birthday timestamp without time zone NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.mpa
(
    id integer NOT NULL,
    title text NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.genre
(
    id integer NOT NULL,
    title text NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.film_genre
(
    film_id integer NOT NULL,
    genre_id integer NOT NULL
);

CREATE TABLE IF NOT EXISTS public.likes
(
    film_id integer NOT NULL,
    user_id integer NOT NULL
);

CREATE TABLE IF NOT EXISTS public.friendship_status
(
    id integer NOT NULL,
    status text NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.friends
(
    user_id integer NOT NULL,
    friend_id integer NOT NULL,
    status_id integer NOT NULL
);

ALTER TABLE IF EXISTS public.films
    ADD CONSTRAINT mpa_foreign_key FOREIGN KEY (mpa_id)
    REFERENCES public.mpa (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;


ALTER TABLE IF EXISTS public.film_genre
    ADD CONSTRAINT film_foreign_key FOREIGN KEY (film_id)
    REFERENCES public.films (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;


ALTER TABLE IF EXISTS public.film_genre
    ADD CONSTRAINT genre_foreign_key FOREIGN KEY (genre_id)
    REFERENCES public.genre (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;


ALTER TABLE IF EXISTS public.likes
    ADD CONSTRAINT film_foreign_key FOREIGN KEY (film_id)
    REFERENCES public.films (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;


ALTER TABLE IF EXISTS public.likes
    ADD CONSTRAINT user_foreign_key FOREIGN KEY (user_id)
    REFERENCES public.users (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;


ALTER TABLE IF EXISTS public.friends
    ADD CONSTRAINT user_foreign_key FOREIGN KEY (user_id)
    REFERENCES public.users (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;


ALTER TABLE IF EXISTS public.friends
    ADD CONSTRAINT friend_foreign_key FOREIGN KEY (friend_id)
    REFERENCES public.users (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;


ALTER TABLE IF EXISTS public.friends
    ADD CONSTRAINT status_foreign_key FOREIGN KEY (status_id)
    REFERENCES public.friendship_status (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;

END;
```