package ru.yandex.practicum.filmorate.storage.user.db;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class DbUserStorageTest {
    private final DbUserStorage dbUserStorage;
    private static User user;

    @BeforeAll
    public static void setUp() {
        user = new User(
                "mail@mail.ru", "dolore", LocalDate.now()
        );
        user.setName("testName");
    }

    @Test
    void add() {
        user.setLogin(UUID.randomUUID().toString());
        assertDoesNotThrow(
                () -> dbUserStorage.add(user)
        );
    }

    @Test
    void get() {
        user.setLogin(UUID.randomUUID().toString());
        int userId = dbUserStorage.add(user);

        assertDoesNotThrow(
                () -> dbUserStorage.get(userId)
        );
    }

    @Test
    void getAll() {
        user.setLogin(UUID.randomUUID().toString());
        int userId = dbUserStorage.add(user);
        List<User> allUsers = dbUserStorage.getAll();

        assertTrue(allUsers.stream().anyMatch(u -> u.getId() == userId));
    }

    @Test
    void update() {
        user.setLogin(UUID.randomUUID().toString());
        int userId = dbUserStorage.add(user);

        User addedUser = dbUserStorage.get(userId);
        addedUser.setEmail("new@Email.ru");
        addedUser.setName("new Name");

        User updatedUser = dbUserStorage.update(addedUser);

        assertAll(
                () -> assertEquals(addedUser.getEmail(), updatedUser.getEmail()),
                () -> assertEquals(addedUser.getName(), updatedUser.getName())
        );
    }

    @Test
    void delete() {
        int userId = dbUserStorage.add(user);

        assertDoesNotThrow(
                () -> dbUserStorage.delete(userId)
        );
    }
}