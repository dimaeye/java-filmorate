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

    @GetMapping("/{id}")
    ResponseEntity<User> getUser(@PathVariable int id);

    @PutMapping("/{id}/friends/{friendId}")
    ResponseEntity<Void> addFriend(@PathVariable int id, @PathVariable int friendId);

    @DeleteMapping("/{id}/friends/{friendId}")
    ResponseEntity<Void> deleteFriend(@PathVariable int id, @PathVariable int friendId);

    @GetMapping("{id}/friends")
    ResponseEntity<List<User>> getFriends(@PathVariable int id);

    @GetMapping("{id}/friends/common/{otherId}")
    ResponseEntity<List<User>> getCommonFriends(@PathVariable int id, @PathVariable int otherId);
}
