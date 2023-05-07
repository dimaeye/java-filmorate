package ru.yandex.practicum.filmorate.storage.film.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.storage.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

@Component
@Qualifier("DbFilmStorage")
public class DbFilmStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DbFilmStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int add(Film film) {
        String addFilmSql = "insert into films(name, description, release_date, duration, mpa_id)" +
                "values (?, ?, ?, ?, ?)";
        String addGenresSql = "INSERT INTO public.film_genre(film_id, genre_id)" +
                "values (?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(addFilmSql, new String[]{"id"});
            stmt.setString(1, film.getName());
            stmt.setString(2, film.getDescription());
            stmt.setDate(3, Date.valueOf(film.getReleaseDate()));
            stmt.setInt(4, film.getDuration());
            stmt.setInt(5, film.getMpa().getId());
            return stmt;
        }, keyHolder);

        int addedFilmId = keyHolder.getKey().intValue();

        jdbcTemplate.batchUpdate(addGenresSql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, addedFilmId);
                ps.setInt(2, film.getGenres().get(i).getId());
            }

            @Override
            public int getBatchSize() {
                return film.getGenres().size();
            }
        });
        return addedFilmId;
    }

    @Override
    public Film get(int filmId) throws ObjectNotFoundException {
        String sql = "select * from films where id = ?";
        try {
            return jdbcTemplate.query(sql, (rs, rowNum) -> mapRowToFilm(rs), filmId)
                    .stream()
                    .findFirst()
                    .orElseThrow();
        } catch (NoSuchElementException e) {
            throw new ObjectNotFoundException(e.getMessage());
        }
    }

    @Override
    public List<Film> getAll() {
        return null;
    }

    @Override
    public Film update(Film film) throws ObjectNotFoundException {
        String sql
        return null;
    }

    @Override
    public void delete(int filmId) throws ObjectNotFoundException {
        String sql = "delete from films where id = ?";
        jdbcTemplate.update(sql, filmId);
    }

    private Film mapRowToFilm(ResultSet rs) throws SQLException {
        int filmId = rs.getInt("id");
        Film film = new Film(
                rs.getString("name"),
                rs.getString("description"),
                rs.getDate("release_date").toLocalDate(),
                rs.getInt("duration"),
                getAllGenres(filmId),
                getMPA(rs.getInt("mpa_id"))
        );
        film.setId(filmId);
        return film;
    }

    private List<Genre> getAllGenres(int filmId) {
        String sql = "select * from genre " +
                "where id in (" +
                "   select genre_id from film_genre" +
                "   where film_id = ?" +
                ")";
        return jdbcTemplate.query(sql, this::mapRowToGenre, filmId);
    }

    private Genre mapRowToGenre(ResultSet rs, int rowNum) throws SQLException {
        Genre genre = new Genre(rs.getInt("id"));
        genre.setTitle(rs.getString("title"));
        return genre;
    }

    private MPA getMPA(int id) {
        String sql = "select * from mpa where id = ?";
        return jdbcTemplate.query(sql, this::mapRowToMPA, id)
                .stream()
                .findFirst()
                .orElseThrow();
    }

    private MPA mapRowToMPA(ResultSet rs, int rowNum) throws SQLException {
        MPA mpa = new MPA(rs.getInt("id"));
        mpa.setTitle(rs.getString("title"));
        return mpa;
    }
}
