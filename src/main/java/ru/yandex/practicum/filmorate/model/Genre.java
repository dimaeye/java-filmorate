package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;

@Data
public class Genre {
    @NonNull
    private int id;
    private String title;
}
