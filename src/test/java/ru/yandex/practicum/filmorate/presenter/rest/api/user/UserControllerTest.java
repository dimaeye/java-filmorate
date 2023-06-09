package ru.yandex.practicum.filmorate.presenter.rest.api.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.UserService;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    void createUser() throws Exception {
        User user = new User(
                "mail@mail.ru", "dolore", LocalDate.now()
        );

        User userResult = new User(
                user.getEmail(), user.getLogin(), user.getBirthday()
        );
        userResult.setName(user.getLogin());
        Mockito.when(userService.addUser(any(User.class))).thenReturn(userResult);

        mockMvc.perform(post("/users")
                        .content(objectMapper.writeValueAsBytes(user))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.login").value(user.getLogin()))
                .andExpect(jsonPath("$.name").value(user.getLogin()))
                .andExpect(jsonPath("$.birthday").value(user.getBirthday().toString()));

    }

    @Test
    void shouldReturnBadRequestWhenCreateUserWithLoginContainsWhitespace() throws Exception {
        User user = new User(
                "mail@mail.ru", "dol ore", LocalDate.now()
        );
        user.setName("Nick Name");

        mockMvc.perform(post("/users")
                        .content(objectMapper.writeValueAsBytes(user))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenCreateUserWithFailEmail() throws Exception {
        User user = new User(
                "mail0mail.ru", "dolore", LocalDate.now()
        );

        mockMvc.perform(post("/users")
                        .content(objectMapper.writeValueAsBytes(user))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenCreateUserWithFutureBirthday() throws Exception {
        User user = new User(
                "mail@mail.ru", "dolore", LocalDate.now().plusDays(1)
        );

        mockMvc.perform(post("/users")
                        .content(objectMapper.writeValueAsBytes(user))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

}