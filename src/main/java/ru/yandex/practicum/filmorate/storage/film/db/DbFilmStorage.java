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
import java.util.stream.Collectors;

@Component
@Qualifier("DbFilmStorage")
public class DbFilmStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;
    private final DbGenreStorage dbGenreStorage;

    private final DbLikesStorage dbLikesStorage;

    @Autowired
    public DbFilmStorage(
            JdbcTemplate jdbcTemplate,
            DbGenreStorage dbGenreStorage,
            @Qualifier("DbLikesStorage")
            DbLikesStorage dbLikesStorage) {
        this.jdbcTemplate = jdbcTemplate;
        this.dbGenreStorage = dbGenreStorage;
        this.dbLikesStorage = dbLikesStorage;
    }

    @Override
    public int add(Film film) {
        String sql = "insert into films(name, description, release_date, duration, mpa_id)"
                + "values (?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql, new String[]{"id"});
            stmt.setString(1, film.getName());
            stmt.setString(2, film.getDescription());
            stmt.setDate(3, Date.valueOf(film.getReleaseDate()));
            stmt.setInt(4, film.getDuration());
            stmt.setInt(5, film.getMpa().getId());
            return stmt;
        }, keyHolder);

        int addedFilmId = keyHolder.getKey().intValue();

        dbGenreStorage.addFilmGenres(
                addedFilmId,
                film.getGenres().stream().map(Genre::getId).collect(Collectors.toSet())
        );

        return addedFilmId;
    }

    @Override
    public Film get(int filmId) throws ObjectNotFoundException {
        String sql = "select f.id, f.name, f.description, f.release_date, f.duration, f.mpa_id, m.title as mpa_title "
                + "from films as f "
                + "left outer join mpa as m on f.mpa_id = m.id "
                + "where f.id = ? "
                + "group by f.id, m.id";

        return jdbcTemplate.query(sql, (rs, rowNum) -> makeFilm(rs), filmId)
                .stream()
                .findFirst()
                .orElseThrow(() -> new ObjectNotFoundException("Фильм с id = " + filmId + " не найден"));
    }

    @Override
    public List<Film> getAll() {
        String sql = "select f.id, f.name, f.description, f.release_date, f.duration, f.mpa_id, m.title as mpa_title "
                + "from films as f "
                + "left outer join mpa as m on f.mpa_id = m.id "
                + "left outer join likes as l on f.id = l.film_id "
                + "group by f.id, m.id";

        return jdbcTemplate.query(sql, (rs, rowNum) -> makeFilm(rs));
    }

    @Override
    public List<Film> getTop(int max) {
        String sql = "select f.id, f.name, f.description, f.release_date, f.duration, f.mpa_id, m.title as mpa_title "
                + "from films as f "
                + "left outer join mpa as m on f.mpa_id = m.id "
                + "left outer join likes as l on f.id = l.film_id "
                + "group by f.id, m.id "
                + "order by count(l.user_id) desc "
                + "limit ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> makeFilm(rs), max);
    }

    @Override
    public Film update(Film film) throws ObjectNotFoundException {
        String sql = "update films set name = ?, description = ?, release_date = ?, duration = ?, mpa_id = ?"
                + "where id = ?";

        int res = jdbcTemplate.update(sql,
                film.getName(),
                film.getDescription(),
                Date.valueOf(film.getReleaseDate()),
                film.getDuration(),
                film.getMpa().getId(),
                film.getId()
        );

        if (res == 0)
            throw new ObjectNotFoundException("Фильм с id = " + film.getId() + " не найден для обновления!");

        List<Genre> currentGenres = dbGenreStorage.getFilmGenres(film.getId());
        if (!currentGenres.equals(film.getGenres())) {
            dbGenreStorage.deleteFilmGenres(film.getId());
            dbGenreStorage.addFilmGenres(
                    film.getId(),
                    film.getGenres().stream().map(Genre::getId).collect(Collectors.toSet())
            );
        }

        return get(film.getId());
    }

    @Override
    public void delete(int filmId) throws ObjectNotFoundException {
        String sql = "delete from films where id = ?";
        int res = jdbcTemplate.update(sql, filmId);
        if (res == 0)
            throw new ObjectNotFoundException("Фильм с id = " + filmId + " не удален!");
    }

    private Film makeFilm(ResultSet rs) throws SQLException {
        int filmId = rs.getInt("id");

        MPA mpa = new MPA(rs.getInt("mpa_id"));
        mpa.setTitle(rs.getString("mpa_title"));

        Film film = new Film(
                rs.getString("name"),
                rs.getString("description"),
                rs.getDate("release_date").toLocalDate(),
                rs.getInt("duration"),
                mpa
        );
        film.setId(filmId);

        film.setGenres(dbGenreStorage.getFilmGenres(filmId));
        List<Integer> likes = dbLikesStorage.getAllFilmLikes(filmId);
        likes.forEach(film::addLike);

        return film;
    }
}
