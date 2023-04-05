package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {
    int add(Film film);

    Film get(int filmId);

    List<Film> getAll();

    Film update(Film film);

    void delete(int filmId);
}
