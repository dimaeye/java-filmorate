package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.exception.EditObjectException;
import ru.yandex.practicum.filmorate.storage.exception.ObjectNotFoundException;

import java.util.List;

public interface FilmStorage {
    int add(Film film) throws EditObjectException;

    Film get(int filmId) throws ObjectNotFoundException;

    List<Film> getAll();

    List<Film> getTop(int max);

    Film update(Film film) throws ObjectNotFoundException;

    void delete(int filmId) throws ObjectNotFoundException;
}
