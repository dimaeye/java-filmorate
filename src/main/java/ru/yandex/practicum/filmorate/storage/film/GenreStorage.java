package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.exception.EditObjectException;
import ru.yandex.practicum.filmorate.storage.exception.ObjectNotFoundException;

import java.util.List;
import java.util.Set;

public interface GenreStorage {
    Genre getGenre(int genreId) throws ObjectNotFoundException;

    List<Genre> getAllGenres();

    List<Genre> getFilmGenres(int filmId) throws ObjectNotFoundException;

    void addFilmGenres(Set<Integer> genreIds, int filmId) throws EditObjectException;
}
