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
        User updatedUser = userService.updateUser(user);
        log.info("Пользователь id={} обновлен успешно", updatedUser.getId());

        return ResponseEntity
                .ok()
                .body(updatedUser);

           /* log.warn("Пользователь id={} не найден для обновления", user.getId());
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Пользователь не найден"
            );*/

    }

    @Override
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity
                .ok(userService.getAllUsers());
    }
}
