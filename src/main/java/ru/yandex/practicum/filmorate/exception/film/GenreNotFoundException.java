package ru.yandex.practicum.filmorate.exception.film;

public class GenreNotFoundException extends RuntimeException {
    public GenreNotFoundException(int genreId) {
        super("Жанр с идентификатором " + genreId + " не найден!");
    }
}
