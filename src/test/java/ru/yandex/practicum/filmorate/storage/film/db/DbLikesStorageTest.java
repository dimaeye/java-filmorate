package ru.yandex.practicum.filmorate.storage.film.db;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class DbLikesStorageTest {

    private final DbLikesStorage dbLikesStorage;
    private static int filmId = 0;
    private static int userId = 0;


    @BeforeAll
    public static void setUp(
            @Autowired
            DbFilmStorage dbFilmStorage,
            @Autowired
            DbUserStorage dbUserStorage
    ) {
        Film film = new Film(
                "nisi eiusmod", "adipisicing",
                LocalDate.of(1967, 3, 25), 100, new MPA(1)
        );
        User user = new User(
                "mail@mail.ru", "dolore", LocalDate.now()
        );
        user.setName("test");

        filmId = dbFilmStorage.add(film);
        userId = dbUserStorage.add(user);
    }

    @Test
    void getAllFilmLikes() {
        dbLikesStorage.deleteLike(filmId, userId);
        dbLikesStorage.addLike(filmId, userId);

        List<Integer> likes = dbLikesStorage.getAllFilmLikes(filmId);

        assertEquals(1, likes.size());
    }

    @Test
    void addLike() {
        assertDoesNotThrow(
                () -> {
                    dbLikesStorage.deleteLike(filmId, userId);
                    dbLikesStorage.addLike(filmId, userId);
                }
        );
    }

    @Test
    void deleteLike() {
        assertDoesNotThrow(() -> dbLikesStorage.deleteLike(filmId, userId));
    }
}