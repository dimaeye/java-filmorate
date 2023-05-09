package ru.yandex.practicum.filmorate.service.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.film.MPANotFoundException;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.storage.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.storage.film.MPAStorage;

import java.util.List;

@Service
public class MPAServiceImpl implements MPAService {
    private final MPAStorage mpaStorage;

    @Autowired
    public MPAServiceImpl(MPAStorage mpaStorage) {
        this.mpaStorage = mpaStorage;
    }


    @Override
    public List<MPA> getAllMPA() {
        return mpaStorage.getAllMPA();
    }

    @Override
    public MPA getMPA(int mpaId) {
        try {
            return mpaStorage.getMPA(mpaId);
        } catch (ObjectNotFoundException e) {
            throw new MPANotFoundException(mpaId);
        }

    }
}
