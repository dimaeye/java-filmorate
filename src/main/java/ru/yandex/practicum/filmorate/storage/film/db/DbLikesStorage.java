package ru.yandex.practicum.filmorate.storage.film.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.storage.film.LikesStorage;

import java.util.ArrayList;
import java.util.List;

@Component
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
}
