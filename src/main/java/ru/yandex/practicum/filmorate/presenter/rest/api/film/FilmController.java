package ru.yandex.practicum.filmorate.presenter.rest.api.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

@Component
@Slf4j
public class FilmController implements FilmResource {
    @Override
    public ResponseEntity<Void> addFilm(Film film) {
        return null;
    }

    @Override
    public ResponseEntity<Void> updateFilm(Film film) {
        return null;
    }

    @Override
    public ResponseEntity<List<Film>> getAllFilms() {
        return null;
    }
}
