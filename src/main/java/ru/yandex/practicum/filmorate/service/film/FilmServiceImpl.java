package ru.yandex.practicum.filmorate.service.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.film.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.user.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.LikesStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;

@Service
public class FilmServiceImpl implements FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final LikesStorage likesStorage;

    @Autowired
    public FilmServiceImpl(
            @Qualifier("DbFilmStorage")
            FilmStorage filmStorage,
            @Qualifier("DbUserStorage")
            UserStorage userStorage,
            @Qualifier("DbLikesStorage")
            LikesStorage likesStorage
    ) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
        this.likesStorage = likesStorage;
    }

    @Override
    public Film addFilm(Film film) {
        int filmId = filmStorage.add(film);
        return getFilm(filmId);
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

        likesStorage.addLike(film.getId(), user.getId());
    }

    @Override
    public void deleteLike(int filmId, int userId) throws FilmNotFoundException, UserNotFoundException {
        Film film = getFilm(filmId);
        User user = getUser(userId);

        likesStorage.deleteLike(film.getId(), user.getId());
    }

    @Override
    public List<Film> getTopFilms(int count) {
        return filmStorage.getTop(count);
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
