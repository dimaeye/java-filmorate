package ru.yandex.practicum.filmorate.presenter.rest.api.film;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

@RestController
@RequestMapping("/genres")
public interface GenreResource {
    @GetMapping
    ResponseEntity<List<Genre>> getAllGenres();

    @GetMapping("/{id}")
    ResponseEntity<Genre> getGenre(@PathVariable int id);
}
