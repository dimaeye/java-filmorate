package ru.yandex.practicum.filmorate.storage.film.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.storage.film.LikesStorage;

import java.util.ArrayList;
import java.util.List;

@Component
@Qualifier("DbLikesStorage")
public class DbLikesStorage implements LikesStorage {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DbLikesStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Integer> getAllFilmLikes(int filmId) {
        String sql = "select user_id from likes where film_id = ?";

        SqlRowSet likes = jdbcTemplate.queryForRowSet(sql, filmId);

        List<Integer> userLikes = new ArrayList<>();
        while (likes.next()) {
            userLikes.add(likes.getInt("user_id"));
        }

        return userLikes;
    }

    @Override
    public void addLike(int filmId, int userId) {
        String sql = "insert into likes(film_id, user_id)"
                + "values(?,?)";

        jdbcTemplate.update(sql, filmId, userId);
    }

    @Override
    public void deleteLike(int filmId, int userId) {
        String sql = "delete from likes where film_id = ? and user_id = ?";

        jdbcTemplate.update(sql, filmId, userId);
    }
}
