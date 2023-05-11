package ru.yandex.practicum.filmorate.service.film;

import ru.yandex.practicum.filmorate.exception.film.MPANotFoundException;
import ru.yandex.practicum.filmorate.model.MPA;

import java.util.List;

public interface MPAService {
    List<MPA> getAllMPA();

    MPA getMPA(int mpaId) throws MPANotFoundException;
}
