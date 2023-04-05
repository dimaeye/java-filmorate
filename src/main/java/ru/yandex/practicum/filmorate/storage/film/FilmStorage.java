package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

public interface FilmStorage {
    int add(Film film);

    Film update(Film film);

    void delete(int filmId);
}
