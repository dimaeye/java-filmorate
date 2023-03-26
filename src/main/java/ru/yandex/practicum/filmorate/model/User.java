package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import ru.yandex.practicum.filmorate.annotations.NotFutureDateConstraint;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
public class User {
    private int id;
    @Email
    private String email;
    @NotEmpty(message = "логин не может быть пустым")
    @Pattern(regexp = "^((?!\\s).)*$", message = "логин не может содержать пробелы")
    private String login;
    private String name;
    @NotNull
    @NotFutureDateConstraint(message = "дата рождения не может быть в будущем")
    private LocalDate birthday;
}
