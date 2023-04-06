package ru.yandex.practicum.filmorate.service.user;

import ru.yandex.practicum.filmorate.exception.user.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserService {
    User addUser(User user);

    User updateUser(User user) throws UserNotFoundException;

    User getUser(int userId) throws UserNotFoundException;

    void deleteUser(int userId) throws UserNotFoundException;

    List<User> getAllUsers();

    void addFriend(int userId, int friendId) throws UserNotFoundException;

    void deleteFriend(int userId, int friendId) throws UserNotFoundException;

    List<User> getFriends(int userId) throws UserNotFoundException;
}
