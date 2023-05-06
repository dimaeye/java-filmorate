package ru.yandex.practicum.filmorate.storage.film.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.FilmGenre;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.storage.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

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
        return 0;
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
        return new Film(
                rs.getString("name"),
                rs.getString("description"),
                rs.getDate("release_date").toLocalDate(),
                rs.getInt("duration"),
                List.of(FilmGenre.ACTION),
                MPA.G
        );
    }
}
