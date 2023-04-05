package ru.yandex.practicum.filmorate.service.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserService {
    User addUser(User user);

    User updateUser(User user);

    void deleteUser(int userId);

    List<User> getAllUsers();

    void addFriend(int userId, int friendId);

    void deleteFriend(int userId, int friendId);

    List<User> getFriends(int userId);
}
