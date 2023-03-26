package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import ru.yandex.practicum.filmorate.annotations.MinDateConstraint;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Data
public class Film {
    private int id;
    @NotBlank
    private String name;
    @NotBlank
    @Length(max = 200)
    private String description;
    @NotNull
    @MinDateConstraint(day = 28, month = 12, year = 1895, message = "Минимальная дата релиза 28 декабря 1895")
    private LocalDate releaseDate;
    @NotNull
    @Positive
    private int duration;
}
