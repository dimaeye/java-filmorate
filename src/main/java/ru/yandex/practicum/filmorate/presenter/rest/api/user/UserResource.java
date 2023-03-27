package ru.yandex.practicum.filmorate.presenter.rest.api.user;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@Validated
public interface UserResource {
    @PostMapping
    ResponseEntity<User> createUser(@Valid @RequestBody User user);

    @PutMapping
    ResponseEntity<User> updateUser(@Valid @RequestBody User user);

    @GetMapping
    ResponseEntity<List<User>> getAllUsers();

}
