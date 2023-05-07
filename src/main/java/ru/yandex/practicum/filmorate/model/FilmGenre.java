package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;

@Data
public class FilmGenre {
    @NonNull
    private int film_id;
    @NonNull
    private int genre_id;
}
