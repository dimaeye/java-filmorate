package ru.yandex.practicum.filmorate.storage.user.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.FriendStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@Qualifier("DbFriendStorage")
public class DbFriendStorage implements FriendStorage {
    private static final int FRIENDSHIP_STATUS_ID = 2; //временно, в тз_11 про статус ничего не сказано
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DbFriendStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> getAllFriends(int userId) {
        String sql = "select * from users " +
                "where id in ( " +
                "   select f.friend_id from users as u " +
                "   left outer join friends as f on u.id = f.user_id " +
                "   where u.id = ? " +
                ")";

        return jdbcTemplate.query(sql, (rs, rowNum) -> makeFriend(rs), userId);
    }

    @Override
    public void addFriend(int userId, int friendId) {
        String sql = "insert into friends(user_id, friend_id, status_id) "
                + "values (?, ?, ?)";

        jdbcTemplate.update(sql, userId, friendId, FRIENDSHIP_STATUS_ID);
    }

    @Override
    public void deleteFriend(int userId, int friendId) {
        String sql = "delete from friends where user_id = ? and friend_id = ?";

        jdbcTemplate.update(sql, userId, friendId);
    }

    private User makeFriend(ResultSet rs) throws SQLException {
        User friend = new User(
                rs.getString("email"),
                rs.getString("login"),
                rs.getDate("birthday").toLocalDate()
        );
        friend.setId(rs.getInt("id"));
        friend.setName(rs.getString("name"));

        return friend;
    }
}
