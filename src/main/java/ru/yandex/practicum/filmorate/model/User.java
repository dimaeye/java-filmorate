package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;
import ru.yandex.practicum.filmorate.annotations.NotFutureDateConstraint;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

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
}
