package ru.yandex.practicum.filmorate.storage.film.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.exception.EditObjectException;
import ru.yandex.practicum.filmorate.storage.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.storage.film.GenreStorage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

@Component
public class DbGenreStorage implements GenreStorage {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public DbGenreStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Genre getGenre(int genreId) throws ObjectNotFoundException {
        String sql = "select * from genre where id = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> makeGenre(rs), genreId)
                .stream()
                .findFirst()
                .orElseThrow(() -> new ObjectNotFoundException("Жанр с id = " + genreId + "не найден"));
    }

    @Override
    public List<Genre> getAllGenres() {
        String sql = "select * from genre";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeGenre(rs));
    }

    @Override
    public List<Genre> getFilmGenres(int filmId) throws ObjectNotFoundException {
        String sql = "select * from genre " +
                "where id in (" +
                "   select genre_id from film_genre" +
                "   where film_id = ?" +
                ")";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeGenre(rs), filmId);
    }

    @Override
    public void addFilmGenres(Set<Integer> genreIds, int filmId) throws EditObjectException {
        String sql = "insert into film_genre(film_id, genre_id)" +
                "values (?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            private Integer[] genres = genreIds.toArray(new Integer[0]);

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

    private Genre makeGenre(ResultSet rs) throws SQLException {
        Genre genre = new Genre(rs.getInt("id"));
        genre.setTitle(rs.getString("title"));
        return genre;
    }
}
