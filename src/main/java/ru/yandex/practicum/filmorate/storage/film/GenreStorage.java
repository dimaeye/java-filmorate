package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.exception.ObjectNotFoundException;

import java.util.List;
import java.util.Set;

public interface GenreStorage {
    Genre getGenre(int genreId) throws ObjectNotFoundException;

    List<Genre> getAllGenres();

    List<Genre> getFilmGenres(int filmId);

    void addFilmGenres(int filmId, Set<Integer> genreIds);

    void deleteFilmGenres(int filmId);

}
