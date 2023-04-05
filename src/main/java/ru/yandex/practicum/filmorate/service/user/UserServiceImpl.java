package ru.yandex.practicum.filmorate.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserServiceImpl(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    @Override
    public User addUser(User user) {
        userStorage.add(user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        return userStorage.update(user);
    }

    @Override
    public void deleteUser(int userId) {
        userStorage.delete(userId);
    }

    @Override
    public void addFriend(int userId, int friendId) {
        User user = userStorage.get(userId);
        User friend = userStorage.get(friendId);

        user.addFriend(friend.getId());
        userStorage.update(user);
    }

    @Override
    public void deleteFriend(int userId, int friendId) {
        User user = userStorage.get(userId);
        User friend = userStorage.get(friendId);

        user.deleteFriend(friend.getId());
        userStorage.update(user);
    }

    @Override
    public List<User> getFriends(int userId) {
        return userStorage.get(userId)
                .getAllFriends().stream()
                .map(userStorage::get)
                .collect(Collectors.toList());
    }
}