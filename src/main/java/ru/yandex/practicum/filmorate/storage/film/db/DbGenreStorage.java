package ru.yandex.practicum.filmorate.storage.film.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.storage.film.GenreStorage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

@Component
public class DbGenreStorage implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DbGenreStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Genre getGenre(int genreId) throws ObjectNotFoundException {
        String sql = "select id, title from genre where id = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> makeGenre(rs), genreId)
                .stream()
                .findFirst()
                .orElseThrow(() -> new ObjectNotFoundException("Жанр с id = " + genreId + " не найден"));
    }

    @Override
    public List<Genre> getAllGenres() {
        String sql = "select id, title from genre order by id";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeGenre(rs));
    }

    @Override
    public List<Genre> getFilmGenres(int filmId) {
        String sql = "select id, title from genre " +
                "where id in (" +
                "   select genre_id from film_genre" +
                "   where film_id = ?" +
                ") order by id";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeGenre(rs), filmId);
    }

    @Override
    public void addFilmGenres(int filmId, Set<Integer> genreIds) {
        String sql = "insert into film_genre(film_id, genre_id)" +
                "values (?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            private final Integer[] genres = genreIds.toArray(new Integer[0]);

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, filmId);
                ps.setInt(2, genres[i]);
            }

            @Override
            public int getBatchSize() {
                return genreIds.size();
            }
        });
    }

    @Override
    public void deleteFilmGenres(int filmId) {
        String sql = "delete from film_genre where film_id = ?";

        jdbcTemplate.update(sql, filmId);
    }

    private Genre makeGenre(ResultSet rs) throws SQLException {
        Genre genre = new Genre(rs.getInt("id"));
        genre.setTitle(rs.getString("title"));
        return genre;
    }
}
