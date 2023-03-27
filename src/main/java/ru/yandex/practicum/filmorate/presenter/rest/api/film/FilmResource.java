package ru.yandex.practicum.filmorate.presenter.rest.api.film;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/films")
@Validated
public interface FilmResource {

    @PostMapping
    ResponseEntity<Film> addFilm(@Valid @RequestBody Film film);

    @PutMapping
    ResponseEntity<Film> updateFilm(@Valid @RequestBody Film film);

    @GetMapping
    ResponseEntity<List<Film>> getAllFilms();

}
