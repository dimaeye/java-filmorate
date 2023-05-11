package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface FriendStorage {
    List<User> getAllFriends(int userId);

    void addFriend(int userId, int friendId);

    void deleteFriend(int userId, int friendId);
}
