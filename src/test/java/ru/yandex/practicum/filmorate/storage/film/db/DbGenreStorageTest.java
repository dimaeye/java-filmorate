package ru.yandex.practicum.filmorate.storage.film.db;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class DbGenreStorageTest {
    private final DbGenreStorage dbGenreStorage;
    private static int filmId = 0;

    @BeforeAll
    public static void setUp(@Autowired DbFilmStorage dbFilmStorage) {
        Film film = new Film(
                "nisi eiusmod", "adipisicing",
                LocalDate.of(1967, 3, 25), 100, new MPA(1)
        );

        filmId = dbFilmStorage.add(film);
    }

    @Test
    void getGenre() {
        Genre expectedGenre = new Genre(1);
        expectedGenre.setTitle("Комедия");

        Genre actualGenre = dbGenreStorage.getGenre(1);

        assertEquals(expectedGenre, actualGenre);
    }

    @Test
    void getAllGenres() {
        List<Genre> genres = dbGenreStorage.getAllGenres();

        List<String> allTitlesGenre = genres.stream().
                map(Genre::getTitle).collect(Collectors.toList());

        assertAll(
                () -> assertEquals(6, allTitlesGenre.size()),
                () -> assertTrue(allTitlesGenre.contains("Комедия")),
                () -> assertTrue(allTitlesGenre.contains("Драма")),
                () -> assertTrue(allTitlesGenre.contains("Мультфильм")),
                () -> assertTrue(allTitlesGenre.contains("Триллер")),
                () -> assertTrue(allTitlesGenre.contains("Документальный")),
                () -> assertTrue(allTitlesGenre.contains("Боевик"))
        );
    }

    @Test
    void getFilmGenres() {
        int genreId = 1;
        dbGenreStorage.deleteFilmGenres(filmId);
        dbGenreStorage.addFilmGenres(filmId, Set.of(genreId));

        List<Genre> filmGenres = dbGenreStorage.getFilmGenres(filmId);

        assertEquals(genreId, filmGenres.get(0).getId());
    }

    @Test
    void addFilmGenres() {
        assertDoesNotThrow(
                () -> dbGenreStorage.addFilmGenres(filmId, Set.of(1, 2))
        );
    }

    @Test
    void deleteFilmGenres() {
        assertDoesNotThrow(
                () -> dbGenreStorage.deleteFilmGenres(filmId)
        );
    }
}