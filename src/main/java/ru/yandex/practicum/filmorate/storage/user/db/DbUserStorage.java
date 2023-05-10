package ru.yandex.practicum.filmorate.storage.user.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@Qualifier("DbUserStorage")
public class DbUserStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;
    private final DbFriendStorage dbFriendStorage;

    @Autowired
    public DbUserStorage(
            JdbcTemplate jdbcTemplate,
            @Qualifier("DbFriendStorage")
            DbFriendStorage dbFriendStorage
    ) {
        this.jdbcTemplate = jdbcTemplate;
        this.dbFriendStorage = dbFriendStorage;
    }

    @Override
    public int add(User user) {
        String sql = "insert into users(email, login, name, birthday)"
                + "values (?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql, new String[]{"id"});
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getLogin());
            stmt.setString(3, user.getName());
            stmt.setDate(4, Date.valueOf(user.getBirthday()));
            return stmt;
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }

    @Override
    public User get(int userId) throws ObjectNotFoundException {
        String sql = "select id, email, login, name, birthday from users where id = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> makeUser(rs), userId)
                .stream()
                .findFirst()
                .orElseThrow(() ->
                        new ObjectNotFoundException("Пользователь с идентификатором " + userId + " не найден!")
                );
    }

    @Override
    public List<User> getAll() {
        String sql = "select id, email, login, name, birthday from users";

        return jdbcTemplate.query(sql, (rs, rowNum) -> makeUser(rs));
    }

    @Override
    public User update(User user) throws ObjectNotFoundException {
        String sql = "update users set email = ?, login = ?, name = ?, birthday = ?"
                + "where id = ?";

        jdbcTemplate.update(sql,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                Date.valueOf(user.getBirthday()),
                user.getId()
        );
        return get(user.getId());
    }

    @Override
    public void delete(int userId) throws ObjectNotFoundException {
        String sql = "delete from users where id = ?";

        jdbcTemplate.update(sql, userId);
    }

    private User makeUser(ResultSet rs) throws SQLException {
        User user = new User(
                rs.getString("email"),
                rs.getString("login"),
                rs.getDate("birthday").toLocalDate()
        );
        user.setId(rs.getInt("id"));
        user.setName(rs.getString("name"));

        List<User> friends = dbFriendStorage.getAllFriends(user.getId());
        friends.forEach(f -> user.addFriend(f.getId()));

        return user;
    }
}
