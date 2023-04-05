package ru.yandex.practicum.filmorate.service.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmServiceImpl implements FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    @Autowired
    public FilmServiceImpl(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    @Override
    public Film addFilm(Film film) {
        filmStorage.add(film);
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        return filmStorage.update(film);
    }

    @Override
    public void deleteFilm(int filmId) {
        filmStorage.delete(filmId);
    }

    @Override
    public void addLike(int filmId, int userId) {
        Film film = filmStorage.get(filmId);
        User user = userStorage.get(userId);

        film.addLike(user.getId());

        filmStorage.update(film);
    }

    @Override
    public void deleteLike(int filmId, int userId) {
        Film film = filmStorage.get(filmId);
        User user = userStorage.get(userId);

        film.deleteLike(user.getId());

        filmStorage.update(film);
    }

    @Override
    public List<Film> getTopFilms(int count) {
        return filmStorage.getAll().stream().limit(count).collect(Collectors.toList());
    }
}
