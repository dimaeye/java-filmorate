package ru.yandex.practicum.filmorate.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.user.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.storage.user.FriendStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserStorage userStorage;
    private final FriendStorage friendStorage;

    @Autowired
    public UserServiceImpl(
            @Qualifier("DbUserStorage")
            UserStorage userStorage,
            @Qualifier("DbFriendStorage")
            FriendStorage friendStorage
    ) {
        this.userStorage = userStorage;
        this.friendStorage = friendStorage;
    }

    @Override
    public User addUser(User user) {
        int userId = userStorage.add(user);
        return getUser(userId);
    }

    @Override
    public User updateUser(User user) throws UserNotFoundException {
        try {
            return userStorage.update(user);
        } catch (ObjectNotFoundException e) {
            throw new UserNotFoundException(user.getId());
        }
    }

    @Override
    public User getUser(int userId) throws UserNotFoundException {
        try {
            return userStorage.get(userId);
        } catch (ObjectNotFoundException e) {
            throw new UserNotFoundException(userId);
        }
    }

    @Override
    public void deleteUser(int userId) throws UserNotFoundException {
        try {
            userStorage.delete(userId);
        } catch (ObjectNotFoundException e) {
            throw new UserNotFoundException(userId);
        }
    }

    @Override
    public List<User> getAllUsers() {
        return userStorage.getAll();
    }

    @Override
    public void addFriend(int userId, int friendId) throws UserNotFoundException {
        final User user = getUser(userId);
        final User friend = getUser(friendId);

        friendStorage.addFriend(user.getId(), friend.getId());
    }

    @Override
    public void deleteFriend(int userId, int friendId) throws UserNotFoundException {
        final User user = getUser(userId);
        final User friend = getUser(friendId);

        friendStorage.deleteFriend(user.getId(), friend.getId());
    }

    @Override
    public List<User> getFriends(int userId) throws UserNotFoundException {
        return friendStorage.getAllFriends(userId);
    }
}