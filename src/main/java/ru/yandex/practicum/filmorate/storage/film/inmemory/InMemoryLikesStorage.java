package ru.yandex.practicum.filmorate.storage.film.inmemory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.LikesStorage;

import java.util.List;

@Component
@Qualifier("InMemoryLikesStorage")
public class InMemoryLikesStorage implements LikesStorage {
    private final FilmStorage filmStorage;

    @Autowired
    public InMemoryLikesStorage(@Qualifier("InMemoryFilmStorage") FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }


    @Override
    public List<Integer> getAllFilmLikes(int filmId) {
        return filmStorage.get(filmId).getAllLikes();
    }

    @Override
    public void addLike(int filmId, int userId) {
        filmStorage.get(filmId).addLike(userId);
    }

    @Override
    public void deleteLike(int filmId, int userId) {
        filmStorage.get(filmId).deleteLike(userId);
    }
}
