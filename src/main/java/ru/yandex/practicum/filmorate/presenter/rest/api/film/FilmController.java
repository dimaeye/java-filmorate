package ru.yandex.practicum.filmorate.presenter.rest.api.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.film.FilmService;

import java.util.List;

@Component
@Slf4j
public class FilmController implements FilmResource {
    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @Override
    public ResponseEntity<Film> addFilm(Film film) {
        log.info("Добавление нового фильма {}", film);
        Film newFilm = filmService.addFilm(film);
        log.info("Фильм id={} добавлен успешно", newFilm.getId());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(newFilm);
    }

    @Override
    public ResponseEntity<Film> updateFilm(Film film) {
        log.info("Обновление данных фильма {}", film);
        Film updatedFilm = filmService.updateFilm(film);
        log.info("Фильм id={} обновлен успешно", updatedFilm.getId());

        return ResponseEntity
                .ok()
                .body(updatedFilm);
    }

    @Override
    public ResponseEntity<List<Film>> getAllFilms() {
        return ResponseEntity
                .ok(filmService.getAllFilms());
    }

    @Override
    public ResponseEntity<Film> getFilm(int id) {
        return ResponseEntity
                .ok(filmService.getFilm(id));
    }

    @Override
    public ResponseEntity<Void> addLike(int id, int userId) {
        log.info("Пользователь {} ставит лайк фильму {}", userId, id);
        filmService.addLike(id, userId);
        log.info("Пользователь {} поставил лайк фильму {} успешно", userId, id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> deleteLike(int id, int userId) {
        log.info("Пользователь {} удаляет лайк фильма {}", userId, id);
        filmService.deleteLike(id, userId);
        log.info("Пользователь {} удалил лайк фильма {} успешно", userId, id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<Film>> getTopFilms(int count) {
        return ResponseEntity
                .ok(filmService.getTopFilms(count));
    }
}
