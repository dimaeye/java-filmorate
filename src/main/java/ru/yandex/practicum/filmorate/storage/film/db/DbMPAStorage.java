package ru.yandex.practicum.filmorate.storage.film.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.storage.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.storage.film.MPAStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class DbMPAStorage implements MPAStorage {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DbMPAStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public MPA getMPA(int mpaId) throws ObjectNotFoundException {
        String sql = "select id, title from mpa where id = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> makeMPA(rs), mpaId)
                .stream()
                .findFirst()
                .orElseThrow(() -> new ObjectNotFoundException("MPA с id = " + mpaId + " не найден"));
    }

    @Override
    public List<MPA> getAllMPA() {
        String sql = "select id, title from mpa order by id";

        return jdbcTemplate.query(sql, (rs, rowNum) -> makeMPA(rs));
    }

    private MPA makeMPA(ResultSet rs) throws SQLException {
        MPA mpa = new MPA(rs.getInt("id"));
        mpa.setTitle(rs.getString("title"));
        return mpa;
    }
}
