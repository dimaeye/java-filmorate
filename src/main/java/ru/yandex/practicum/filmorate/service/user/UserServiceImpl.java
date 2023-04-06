package ru.yandex.practicum.filmorate.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.user.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.exception.ObjectNotFoundException;
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

        user.addFriend(friend.getId());
        userStorage.update(user);

        friend.addFriend(user.getId());
        userStorage.update(user);
    }

    @Override
    public void deleteFriend(int userId, int friendId) throws UserNotFoundException {
        final User user = getUser(userId);
        final User friend = getUser(friendId);

        user.deleteFriend(friend.getId());
        userStorage.update(user);

        friend.deleteFriend(user.getId());
        userStorage.update(friend);
    }

    @Override
    public List<User> getFriends(int userId) throws UserNotFoundException {
        return getUser(userId)
                .getAllFriends().stream()
                .map(userStorage::get)
                .collect(Collectors.toList());
    }
}