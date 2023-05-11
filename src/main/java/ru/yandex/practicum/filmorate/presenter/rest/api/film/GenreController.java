package ru.yandex.practicum.filmorate.presenter.rest.api.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.film.GenreService;

import java.util.List;

@Component
public class GenreController implements GenreResource {
    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @Override
    public ResponseEntity<List<Genre>> getAllGenres() {
        return ResponseEntity.ok(genreService.getAllGenres());
    }

    @Override
    public ResponseEntity<Genre> getGenre(int id) {
        return ResponseEntity.ok(genreService.getGenre(id));
    }
}
