package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import ru.yandex.practicum.filmorate.annotations.MinDateConstraint;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private transient Set<Integer> likes = new HashSet<>();

    public void addLike(int userId) {
        likes.add(userId);
    }

    public void deleteLike(int userId) {
        likes.remove(userId);
    }

    @JsonIgnore
    public List<Integer> getAllLikes() {
        return new ArrayList<>(likes);
    }

    public int getLikesCount() {
        return likes.size();
    }
}
