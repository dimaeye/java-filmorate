package ru.yandex.practicum.filmorate.service.film;

import ru.yandex.practicum.filmorate.exception.film.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.user.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;

import java.util.List;

public interface FilmService {
    Film addFilm(Film film);

    Film updateFilm(Film film) throws FilmNotFoundException;

    Film getFilm(int filmId) throws FilmNotFoundException;

    void deleteFilm(int filmId) throws FilmNotFoundException;

    void addLike(int filmId, int userId) throws FilmNotFoundException, UserNotFoundException;

    void deleteLike(int filmId, int userId) throws FilmNotFoundException, UserNotFoundException;

    List<Film> getTopFilms(int count);

    List<Film> getAllFilms();




}
