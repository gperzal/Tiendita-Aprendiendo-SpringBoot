// src/test/java/com/bootcamp/security/integration/AuthIntegrationTest.java
package com.bootcamp.security.integration;

import com.bootcamp.security.dto.LoginRequest;
import com.bootcamp.security.dto.RegisterRequest;
import com.bootcamp.security.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc        // aquí sí usamos filtros reales; /auth/** debe estar permitAll
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class AuthIntegrationTest {

    @Autowired MockMvc mvc;
    @Autowired ObjectMapper om;
    @Autowired UsuarioRepository usuarioRepository;

    @Test
    void registroLuegoLogin_persistenciaYTokenOk() throws Exception {
        // 1) REGISTER
        var reg = new RegisterRequest("Ana", "ana@mail.com", "secreta");
        mvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(reg)))
                .andExpect(status().isCreated());

        assertThat(usuarioRepository.findByEmail("ana@mail.com")).isPresent();

        // 2) LOGIN
        var login = new LoginRequest("ana@mail.com", "secreta");
        mvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(login)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("ana@mail.com"))
                .andExpect(jsonPath("$.token").isNotEmpty())
                // chequeo básico de forma JWT: 3 segmentos separados por puntos
                .andExpect(jsonPath("$.token").value(org.hamcrest.Matchers.matchesPattern("^[^.]+\\.[^.]+\\.[^.]+$")));
    }
}
