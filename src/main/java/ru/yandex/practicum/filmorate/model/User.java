package ru.yandex.practicum.filmorate.model;

import lombok.*;
import ru.yandex.practicum.filmorate.annotations.NotFutureDateConstraint;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class User {
    private int id;
    @NonNull
    @Email
    private String email;
    @NonNull
    @NotEmpty(message = "логин не может быть пустым")
    @Pattern(regexp = "^((?!\\s).)*$", message = "логин не может содержать пробелы")
    private String login;
    private String name;
    @NonNull
    @NotFutureDateConstraint(message = "дата рождения не может быть в будущем")
    private LocalDate birthday;
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private transient Set<Integer> friends = new HashSet<>();

    public void addFriend(int friendId) {
        friends.add(friendId);
    }

    public void deleteFriend(int friendId) {
        friends.remove(friendId);
    }

    public List<Integer> getAllFriends() {
        return new ArrayList<>(friends);
    }
}
