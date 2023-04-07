package ru.yandex.practicum.filmorate.exception.film;

public class FilmNotFoundException extends RuntimeException {
    public FilmNotFoundException(int filmId) {
        super("Фильм с идентификатором " + filmId + " не найден!");
    }
}