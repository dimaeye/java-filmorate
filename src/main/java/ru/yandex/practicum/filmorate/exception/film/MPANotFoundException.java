package ru.yandex.practicum.filmorate.exception.film;

public class MPANotFoundException extends RuntimeException {
    public MPANotFoundException(int mpaId) {
        super("MPA с идентификатором " + mpaId + " не найден!");
    }
}
