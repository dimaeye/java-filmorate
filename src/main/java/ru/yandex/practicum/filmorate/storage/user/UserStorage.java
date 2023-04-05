package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {
    int add(User user);

    User get(int userId);

    List<User> getAll();

    User update(User user);

    void delete(int userId);
}
