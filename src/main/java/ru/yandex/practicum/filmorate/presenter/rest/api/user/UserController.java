package ru.yandex.practicum.filmorate.presenter.rest.api.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class UserController implements UserResource {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<User> createUser(User user) {
        log.info("Добавление нового пользователя {}", user);
        if (user.getName() == null || user.getName().isBlank())
            user.setName(user.getLogin());
        User newUser = userService.addUser(user);
        log.info("Пользователь id={} добавлен успешно", user.getId());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(newUser);
    }

    @Override
    public ResponseEntity<User> updateUser(User user) {
        log.info("Обновление данных пользователя {}", user);
        if (user.getName() == null || user.getName().isBlank())
            user.setName(user.getLogin());
        User updatedUser = userService.updateUser(user);
        log.info("Пользователь id={} обновлен успешно", updatedUser.getId());

        return ResponseEntity
                .ok()
                .body(updatedUser);
    }

    @Override
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity
                .ok(userService.getAllUsers());
    }

    @Override
    public ResponseEntity<User> getUser(int id) {
        return ResponseEntity
                .ok(userService.getUser(id));
    }

    @Override
    public ResponseEntity<Void> addFriend(int id, int friendId) {
        log.info("Добавления в друзья {} у пользователя {}", friendId, id);
        userService.addFriend(id, friendId);
        log.info("Добавления в друзья {} у пользователя {} выполнено успешно", friendId, id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> deleteFriend(int id, int friendId) {
        log.info("Удаление из друзей {} у пользователя {}", friendId, id);
        userService.deleteFriend(id, friendId);
        log.info("Удаление из друзей {} у пользователя {} выполнено успешно", friendId, id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<User>> getFriends(int id) {
        return ResponseEntity
                .ok(userService.getFriends(id));
    }

    @Override
    public ResponseEntity<List<User>> getCommonFriends(int id, int otherId) {
        List<User> userFriends = userService.getFriends(id);
        List<User> otherUserFriends = userService.getFriends(otherId);

        return ResponseEntity.ok(
                userFriends.stream()
                        .filter(otherUserFriends::contains)
                        .collect(Collectors.toList())
        );
    }
}
