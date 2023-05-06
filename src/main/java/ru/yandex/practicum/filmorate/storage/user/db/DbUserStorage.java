package ru.yandex.practicum.filmorate.storage.user.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;

@Component
@Qualifier("DbUserStorage")
public class DbUserStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DbUserStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int add(User user) throws ObjectNotFoundException {
        return 0;
    }

    @Override
    public User get(int userId) throws ObjectNotFoundException {
        return null;
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public User update(User user) throws ObjectNotFoundException {
        return null;
    }

    @Override
    public void delete(int userId) throws ObjectNotFoundException {

    }
}
