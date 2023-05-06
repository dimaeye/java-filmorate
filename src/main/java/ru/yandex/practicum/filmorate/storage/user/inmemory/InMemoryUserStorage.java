package ru.yandex.practicum.filmorate.storage.user.inmemory;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Qualifier("InMemoryUserStorage")
public class InMemoryUserStorage implements UserStorage {
    private final List<User> users = new ArrayList<>();
    private final AtomicInteger uniqueUserId = new AtomicInteger(0);

    @Override
    public int add(User user) {
        int id = uniqueUserId.incrementAndGet();
        user.setId(id);

        users.add(user);

        return id;
    }

    @Override
    public User get(int userId) throws ObjectNotFoundException {
        Optional<User> optionalUser = users.stream().filter(u -> u.getId() == userId).findFirst();
        if (optionalUser.isPresent())
            return optionalUser.get();
        else
            throw new ObjectNotFoundException(getObjectNotFoundErrorMessage(userId));
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(users);
    }

    @Override
    public User update(User user) throws ObjectNotFoundException {
        Optional<User> optionalUser = users.stream().filter(u -> u.getId() == user.getId()).findFirst();

        if (optionalUser.isPresent()) {
            User userForUpdate = optionalUser.get();

            if (user.getName() == null || user.getName().isBlank())
                user.setName(user.getLogin());

            userForUpdate.setName(user.getName());
            userForUpdate.setEmail(user.getEmail());
            userForUpdate.setLogin(user.getLogin());
            userForUpdate.setBirthday(user.getBirthday());

            return userForUpdate;
        } else
            throw new ObjectNotFoundException(getObjectNotFoundErrorMessage(user.getId()));
    }

    @Override
    public void delete(int userId) throws ObjectNotFoundException {
        Optional<User> optionalUser = users.stream().filter(u -> u.getId() == userId).findFirst();
        if (optionalUser.isPresent())
            users.remove(optionalUser.get());
        else
            throw new ObjectNotFoundException(getObjectNotFoundErrorMessage(userId));
    }

    private String getObjectNotFoundErrorMessage(int userId) {
        return "Пользователь с идентификатором " + userId + " не найден!";
    }
}
