package ru.yandex.practicum.filmorate.storage.film;

import java.util.List;

public interface LikesStorage {
    List<Integer> getAllFilmLikes(int filmId);
}
