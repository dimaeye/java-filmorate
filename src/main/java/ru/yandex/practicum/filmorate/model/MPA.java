package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@RequiredArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class MPA {
    @NonNull
    @Getter
    private int id;
    @Getter
    @Setter
    @JsonProperty("name")
    private String title;
}
