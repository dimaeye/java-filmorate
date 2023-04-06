package ru.yandex.practicum.filmorate.service.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.film.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.user.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.exception.ObjectNotFoundException;
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
    public Film updateFilm(Film film) throws FilmNotFoundException {
        try {
            return filmStorage.update(film);
        } catch (ObjectNotFoundException e) {
            throw new FilmNotFoundException(film.getId());
        }
    }

    @Override
    public Film getFilm(int filmId) throws FilmNotFoundException {
        try {
            return filmStorage.get(filmId);
        } catch (ObjectNotFoundException e) {
            throw new FilmNotFoundException(filmId);
        }
    }

    @Override
    public void deleteFilm(int filmId) throws FilmNotFoundException {
        try {
            filmStorage.delete(filmId);
        } catch (ObjectNotFoundException e) {
            throw new FilmNotFoundException(filmId);
        }
    }

    @Override
    public void addLike(int filmId, int userId) throws FilmNotFoundException, UserNotFoundException {
        Film film = getFilm(filmId);
        User user = getUser(userId);

        film.addLike(user.getId());
        filmStorage.update(film);
    }

    @Override
    public void deleteLike(int filmId, int userId) throws FilmNotFoundException, UserNotFoundException {
        Film film = getFilm(filmId);
        User user = getUser(userId);

        film.deleteLike(user.getId());
        filmStorage.update(film);
    }

    @Override
    public List<Film> getTopFilms(int count) {
        return filmStorage.getAll().stream()
                .sorted((film, film1) -> film1.getLikesCount() - film.getLikesCount())
                .limit(count)
                .collect(Collectors.toList());
    }

    @Override
    public List<Film> getAllFilms() {
        return filmStorage.getAll();
    }

    private User getUser(int userId) throws UserNotFoundException {
        try {
            return userStorage.get(userId);
        } catch (ObjectNotFoundException e) {
            throw new UserNotFoundException(userId);
        }
    }
}
