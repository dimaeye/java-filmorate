package ru.yandex.practicum.filmorate.presenter.rest.api.film;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FilmController.class)
class FilmControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void addFilm() throws Exception {
        Film film = new Film(
                "nisi eiusmod", "adipisicing",
                LocalDate.of(1967, 3, 25), 100
        );

        mockMvc.perform(post("/films")
                        .content(objectMapper.writeValueAsBytes(film))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(film.getName()))
                .andExpect(jsonPath("$.description").value(film.getDescription()))
                .andExpect(jsonPath("$.releaseDate").value(film.getReleaseDate().toString()))
                .andExpect(jsonPath("$.duration").value(film.getDuration()));
    }

    @Test
    void shouldReturnBadRequestWhenCreateFilmWithFailName() throws Exception {
        Film film = new Film(
                "   ", "adipisicing",
                LocalDate.of(1967, 3, 25), 100
        );

        mockMvc.perform(post("/films")
                        .content(objectMapper.writeValueAsBytes(film))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenCreateFilmWithLargeDescription() throws Exception {
        Film film = new Film(
                "Film name", "Пятеро друзей ( комик-группа «Шарло»), приезжают в город Бризуль. " +
                "Здесь они хотят разыскать господина Огюста Куглова, который задолжал им деньги, " +
                "а именно 20 миллионов. о Куглов, который за время «своего отсутствия», стал кандидатом Коломбани.",
                LocalDate.of(1967, 3, 25), 100
        );

        mockMvc.perform(post("/films")
                        .content(objectMapper.writeValueAsBytes(film))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenCreateFilmWithBadReleaseDate() throws Exception {
        Film film = new Film(
                "Film", "adipisicing",
                LocalDate.of(1895, 12, 27), 100
        );

        mockMvc.perform(post("/films")
                        .content(objectMapper.writeValueAsBytes(film))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnIsCreatedWhenCreateFilmWithReleaseDateEqualDateOfBirthOfCinema() throws Exception {
        Film film = new Film(
                "Film", "adipisicing",
                LocalDate.of(1895, 12, 28), 100
        );

        mockMvc.perform(post("/films")
                        .content(objectMapper.writeValueAsBytes(film))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void shouldReturnBadRequestWhenCreateFilmWithNegativeDuration() throws Exception {
        Film film = new Film(
                "Film", "adipisicing",
                LocalDate.of(1895, 12, 29), -100
        );

        mockMvc.perform(post("/films")
                        .content(objectMapper.writeValueAsBytes(film))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

}