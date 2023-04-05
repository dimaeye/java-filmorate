package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

public interface UserStorage {
    int add(User user);

    User update(User user);

    void delete(int userId);
}
