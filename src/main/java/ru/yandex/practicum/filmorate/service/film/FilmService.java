package ru.yandex.practicum.filmorate.service.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmService {
    Film addFilm(Film film);

    Film updateFilm(Film film);

    void deleteFilm(int filmId);

    void addLike(int filmId, int userId);

    void deleteLike(int filmId, int userId);

    List<Film> getTopFilms(int count);

    List<Film> getAllFilms();
}
