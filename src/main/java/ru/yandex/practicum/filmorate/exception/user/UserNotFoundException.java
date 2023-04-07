package ru.yandex.practicum.filmorate.exception.user;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(int userId) {
        super("Пользователь с идентификатором " + userId + " не найден!");
    }
}