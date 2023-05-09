package ru.yandex.practicum.filmorate.storage.film.db;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.MPA;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class DbMPAStorageTest {
    private final DbMPAStorage dbMPAStorage;

    @Test
    void getMPA() {
        MPA expectedMPA = new MPA(1);
        expectedMPA.setTitle("G");

        MPA actualMPA = dbMPAStorage.getMPA(expectedMPA.getId());

        assertEquals(expectedMPA, actualMPA);
    }

    @Test
    void getAllMPA() {
        List<MPA> allMPA = dbMPAStorage.getAllMPA();
        List<String> allTitlesMPA = dbMPAStorage.getAllMPA().stream().
                map(MPA::getTitle).collect(Collectors.toList());

        assertAll(
                () -> assertEquals(5, allMPA.size()),
                () -> assertTrue(allTitlesMPA.contains("G")),
                () -> assertTrue(allTitlesMPA.contains("PG")),
                () -> assertTrue(allTitlesMPA.contains("PG-13")),
                () -> assertTrue(allTitlesMPA.contains("R")),
                () -> assertTrue(allTitlesMPA.contains("NC-17"))
        );
    }
}