package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.exception.ObjectNotFoundException;

import java.util.List;

public interface UserStorage {
    int add(User user);

    User get(int userId) throws ObjectNotFoundException;

    List<User> getAll();

    User update(User user) throws ObjectNotFoundException;

    void delete(int userId) throws ObjectNotFoundException;
}
