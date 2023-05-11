package ru.yandex.practicum.filmorate.storage.user.db;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class DbFriendStorageTest {
    private final DbFriendStorage dbFriendStorage;
    private final DbUserStorage dbUserStorage;

    @Test
    void getAllFriends() {
        int firstUserId = dbUserStorage.add(getNewUser());
        int secondUserId = dbUserStorage.add(getNewUser());

        dbFriendStorage.addFriend(firstUserId, secondUserId);

        assertAll(
                () -> assertEquals(secondUserId, dbFriendStorage.getAllFriends(firstUserId).get(0).getId()),
                () -> assertTrue(dbFriendStorage.getAllFriends(secondUserId).isEmpty())
        );
    }

    @Test
    void addFriend() {
        int firstUserId = dbUserStorage.add(getNewUser());
        int secondUserId = dbUserStorage.add(getNewUser());

        dbFriendStorage.addFriend(firstUserId, secondUserId);

        assertEquals(secondUserId, dbFriendStorage.getAllFriends(firstUserId).get(0).getId());
    }

    @Test
    void deleteFriend() {
        int firstUserId = dbUserStorage.add(getNewUser());
        int secondUserId = dbUserStorage.add(getNewUser());

        assertAll(
                () -> {
                    dbFriendStorage.addFriend(firstUserId, secondUserId);
                    assertEquals(secondUserId, dbFriendStorage.getAllFriends(firstUserId).get(0).getId());

                },
                () -> {
                    dbFriendStorage.deleteFriend(firstUserId, secondUserId);
                    assertTrue(dbFriendStorage.getAllFriends(firstUserId).isEmpty());
                }
        );
    }

    private User getNewUser() {
        User user = new User(
                "mail@mail.ru", UUID.randomUUID().toString(), LocalDate.now()
        );
        user.setName("testName");
        return user;
    }
}