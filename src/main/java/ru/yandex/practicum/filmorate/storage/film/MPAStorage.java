package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.storage.exception.ObjectNotFoundException;

import java.util.List;

public interface MPAStorage {
    MPA getMPA(int mpaId) throws ObjectNotFoundException;

    List<MPA> getAllMPA();
}
