package ru.yandex.practicum.filmorate.presenter.rest.api.film;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.MPA;

import java.util.List;

@RestController
@RequestMapping("/mpa")
public interface MPAResource {
    @GetMapping
    ResponseEntity<List<MPA>> getAllMPA();

    @GetMapping("/{id}")
    ResponseEntity<MPA> getMPA(@PathVariable int id);
}
