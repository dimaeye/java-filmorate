package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Objects;

@RequiredArgsConstructor
@NoArgsConstructor
public class MPA {
    @NonNull
    @Getter
    private int id;
    @Getter
    @Setter
    @JsonProperty("name")
    private String title;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MPA mpa = (MPA) o;
        return id == mpa.id && Objects.equals(title, mpa.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }
}
