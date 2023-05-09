package ru.yandex.practicum.filmorate.presenter.rest.api.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.service.film.MPAService;

import java.util.List;

@Component
public class MPAController implements MPAResource {
    private final MPAService mpaService;

    @Autowired
    public MPAController(MPAService mpaService) {
        this.mpaService = mpaService;
    }

    @Override
    public ResponseEntity<List<MPA>> getAllMPA() {
        return ResponseEntity.ok(mpaService.getAllMPA());
    }

    @Override
    public ResponseEntity<MPA> getMPA(int id) {
        return ResponseEntity.ok(mpaService.getMPA(id));
    }
}
