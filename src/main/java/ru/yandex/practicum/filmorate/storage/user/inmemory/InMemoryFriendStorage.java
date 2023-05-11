package ru.yandex.practicum.filmorate.storage.user.inmemory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.FriendStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Qualifier("InMemoryFriendStorage")
public class InMemoryFriendStorage implements FriendStorage {
    private final UserStorage userStorage;

    @Autowired
    public InMemoryFriendStorage(@Qualifier("InMemoryUserStorage") UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    @Override
    public List<User> getAllFriends(int userId) {
        return userStorage.get(userId).getAllFriends()
                .stream().map(userStorage::get)
                .collect(Collectors.toList());
    }

    @Override
    public void addFriend(int userId, int friendId) {
        User user = userStorage.get(userId);
        user.addFriend(friendId);
    }

    @Override
    public void deleteFriend(int userId, int friendId) {
        User user = userStorage.get(userId);
        user.deleteFriend(friendId);
    }
}
