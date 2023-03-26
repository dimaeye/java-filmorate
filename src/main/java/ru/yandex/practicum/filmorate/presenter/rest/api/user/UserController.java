package ru.yandex.practicum.filmorate.presenter.rest.api.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
public class UserController implements UserResource {
    private final List<User> users = new ArrayList<>();
    private final AtomicInteger uniqueUserId = new AtomicInteger(0);


    @Override
    public ResponseEntity<Void> createUser(User user) {
        int id = uniqueUserId.incrementAndGet();
        user.setId(id);

        if (user.getName() == null || user.getName().isBlank())
            user.setName(user.getLogin());

        log.info("Добавление нового пользователя {}", user);
        users.add(user);
        log.info("Пользователь id={} добавлен успешно", user.getId());

        return ResponseEntity
                .ok()
                .build();
    }

    @Override
    public ResponseEntity<Void> updateUser(User user) {
        log.info("Обновление данных пользователя {}", user);
        Optional<User> optionalUser = users.stream().filter(u -> u.getId() == user.getId()).findFirst();

        if (optionalUser.isPresent()) {
            User userForUpdate = optionalUser.get();

            if (user.getName() == null || user.getName().isBlank())
                user.setName(user.getLogin());

            userForUpdate.setName(user.getName());
            userForUpdate.setEmail(user.getEmail());
            userForUpdate.setLogin(user.getLogin());
            userForUpdate.setBirthday(user.getBirthday());
            log.info("Пользователь id={} обновлен успешно", user.getId());

            return ResponseEntity
                    .ok()
                    .build();
        } else {
            log.warn("Пользователь id={} не найден для обновления", user.getId());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }

    @Override
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity
                .ok(users);
    }
}
