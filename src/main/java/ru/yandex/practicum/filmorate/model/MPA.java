package ru.yandex.practicum.filmorate.model;

import lombok.*;

@RequiredArgsConstructor
@NoArgsConstructor
public class MPA {
    @NonNull
    @Getter
    private int id;
    @Getter
    @Setter
    private String title;
}
