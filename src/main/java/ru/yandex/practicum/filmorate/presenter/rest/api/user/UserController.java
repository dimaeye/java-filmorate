package ru.yandex.practicum.filmorate.presenter.rest.api.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

@Component
@Slf4j
public class UserController implements UserResource {
    @Override
    public ResponseEntity<Void> createUser(User user) {
        return null;
    }

    @Override
    public ResponseEntity<Void> updateUser(User user) {
        return null;
    }

    @Override
    public ResponseEntity<List<User>> getAllUsers() {
        return null;
    }
}
