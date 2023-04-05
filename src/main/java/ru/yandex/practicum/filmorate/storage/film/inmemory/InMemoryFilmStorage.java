package ru.yandex.practicum.filmorate.storage.film.inmemory;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Set<Film> films = new TreeSet<Film>(Comparator.comparingInt(Film::getLikesCount));
    private final AtomicInteger uniqueFilmId = new AtomicInteger(0);

    @Override
    public int add(Film film) {
        int id = uniqueFilmId.incrementAndGet();
        film.setId(id);

        films.add(film);

        return id;
    }

    @Override
    public Film get(int filmId) {
        Optional<Film> optionalFilm = films.stream().filter(f -> f.getId() == filmId).findFirst();
        if (optionalFilm.isPresent())
            return optionalFilm.get();
        else
            throw new RuntimeException("Фильм с идентификатором " + filmId + " не найден!");
    }

    @Override
    public List<Film> getAll() {
        return new ArrayList<>(films);
    }

    @Override
    public Film update(Film film) {
        Optional<Film> optionalFilm = films.stream().filter(f -> f.getId() == film.getId()).findFirst();
        if (optionalFilm.isPresent()) {
            Film filmForUpdate = optionalFilm.get();

            filmForUpdate.setName(film.getName());
            filmForUpdate.setDescription(film.getDescription());
            filmForUpdate.setDuration(film.getDuration());
            filmForUpdate.setReleaseDate(film.getReleaseDate());

            return filmForUpdate;
        } else
            throw new RuntimeException("Фильм с идентификатором " + film.getId() + " не найден!");
    }

    @Override
    public void delete(int filmId) {
        Optional<Film> optionalFilm = films.stream().filter(f -> f.getId() == filmId).findFirst();
        if (optionalFilm.isPresent())
            films.remove(optionalFilm.get());
        else
            throw new RuntimeException("Фильм с идентификатором " + filmId + " не найден!");
    }
}
