package ru.yandex.practicum.filmorate.storage.film.db;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.db.DbUserStorage;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class DbFilmStorageTest {
    private final DbFilmStorage dbFilmStorage;
    private final DbLikesStorage dbLikesStorage;
    private final DbUserStorage dbUserStorage;
    private final Film film = new Film(
            "nisi eiusmod", "adipisicing",
            LocalDate.of(1967, 3, 25), 100, new MPA(1)
    );

    @Test
    void add() {
        assertDoesNotThrow(() -> dbFilmStorage.add(film));
    }

    @Test
    void get() {
        int filmId = dbFilmStorage.add(film);
        Film addedFilm = dbFilmStorage.get(filmId);

        assertEquals(
                filmId, addedFilm.getId()
        );
    }

    @Test
    void getAll() {
        dbFilmStorage.add(film);
        assertTrue(dbFilmStorage.getAll().size() > 0);
    }

    @Test
    void getTop() {
        int filmId = dbFilmStorage.add(film);

        User user = new User(
                "mail@mail.ru", "dolore", LocalDate.now()
        );
        user.setName("test");
        int userId = dbUserStorage.add(user);

        dbLikesStorage.addLike(filmId, userId);

        List<Film> top = dbFilmStorage.getTop(1);

        assertAll(
                () -> assertEquals(1, top.size()),
                () -> assertEquals(filmId, top.get(0).getId())
        );
    }

    @Test
    void update() {
        int filmId = dbFilmStorage.add(film);

        Film addedFilm = dbFilmStorage.get(filmId);
        addedFilm.setDescription("New Description");
        addedFilm.setName("New Name");

        Film updatedFilm = dbFilmStorage.update(addedFilm);

        assertAll(
                () -> assertEquals(addedFilm.getDescription(), updatedFilm.getDescription()),
                () -> assertEquals(addedFilm.getName(), updatedFilm.getName())
        );
    }

    @Test
    void delete() {
        int filmId = dbFilmStorage.add(film);

        assertDoesNotThrow(
                () -> dbFilmStorage.delete(filmId)
        );
    }
}