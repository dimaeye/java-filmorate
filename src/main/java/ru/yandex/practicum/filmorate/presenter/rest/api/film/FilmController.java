package ru.yandex.practicum.filmorate.presenter.rest.api.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
public class FilmController implements FilmResource {
    private final List<Film> films = new ArrayList<>();
    private final AtomicInteger uniqueFilmId = new AtomicInteger(0);

    @Override
    public ResponseEntity<Film> addFilm(Film film) {
        int id = uniqueFilmId.incrementAndGet();
        film.setId(id);

        log.info("Добавление нового фильма {}", film);
        films.add(film);
        log.info("Фильм id={} добавлен успешно", film.getId());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(film);
    }

    @Override
    public ResponseEntity<Film> updateFilm(Film film) {
        log.info("Обновление данных фильма {}", film);

        Optional<Film> optionalFilm = films.stream().filter(f -> f.getId() == film.getId()).findFirst();

        if (optionalFilm.isPresent()) {
            Film filmForUpdate = optionalFilm.get();

            filmForUpdate.setName(film.getName());
            filmForUpdate.setDescription(film.getDescription());
            filmForUpdate.setDuration(film.getDuration());
            filmForUpdate.setReleaseDate(film.getReleaseDate());
            log.info("Фильм id={} обновлен успешно", filmForUpdate.getId());

            return ResponseEntity
                    .ok()
                    .body(filmForUpdate);
        } else {
            log.warn("Фильм id={} не найден для обновления", film.getId());
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Фильм не найден"
            );
        }
    }

    @Override
    public ResponseEntity<List<Film>> getAllFilms() {
        return ResponseEntity
                .ok(films);
    }
}
