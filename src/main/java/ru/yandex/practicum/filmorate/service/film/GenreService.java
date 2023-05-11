package ru.yandex.practicum.filmorate.service.film;

import ru.yandex.practicum.filmorate.exception.film.GenreNotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

public interface GenreService {
    List<Genre> getAllGenres();

    Genre getGenre(int genreId) throws GenreNotFoundException;
}
