package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;
import ru.yandex.practicum.filmorate.annotations.MinDateConstraint;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Data
public class Film {
    private int id;
    @NonNull
    @NotBlank
    private String name;
    @NonNull
    @NotBlank
    @Length(max = 200)
    private String description;
    @NonNull
    @MinDateConstraint(day = 28, month = 12, year = 1895, message = "Минимальная дата релиза 28 декабря 1895")
    private LocalDate releaseDate;
    @NonNull
    @Positive
    private int duration;
}
