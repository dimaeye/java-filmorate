package ru.yandex.practicum.filmorate.storage.film.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

@Component
@Qualifier("DbFilmStorage")
public class DbFilmStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DbFilmStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int add(Film film) throws ObjectNotFoundException {
        String addFilmSql = "insert into films(name, description, release_date, duration, mpa_id)" +
                "values (?, ?, ?, ?, ?, ?)";
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
        return keyHolder.getKey().intValue();
    }

    @Override
    public Film get(int filmId) throws ObjectNotFoundException {
        String sql = "select * from films where id = ?";

        Film film = jdbcTemplate.query(sql, (rs, rowNum) -> makeFilm(rs), filmId)
                .stream()
                .findFirst()
                .orElseThrow();

        return film;
    }

    @Override
    public List<Film> getAll() {
        return null;
    }

    @Override
    public Film update(Film film) throws ObjectNotFoundException {
        return null;
    }

    @Override
    public void delete(int filmId) throws ObjectNotFoundException {

    }

    private Film makeFilm(ResultSet rs) throws SQLException {
       /* return new Film(
                rs.getString("name"),
                rs.getString("description"),
                rs.getDate("release_date").toLocalDate(),
                rs.getInt("duration"),
                List.of(Genre.ACTION),
                MPA.G
        );*/
        throw new  RuntimeException();
    }
}
