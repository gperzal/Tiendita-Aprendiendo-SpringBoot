package com.bootcamp.security.controller;

import com.bootcamp.security.dto.LoginRequest;
import com.bootcamp.security.dto.LoginResponse;
import com.bootcamp.security.dto.RegisterRequest;
import com.bootcamp.security.service.AuthService;
import com.bootcamp.security.jwt.JwtAuthenticationFilter; // <- para excluirlo
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = AuthController.class,
        // evita que el slice intente instanciar el filtro (y con ello JwtUtils)
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = JwtAuthenticationFilter.class
        )
)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired MockMvc mvc;
    @Autowired ObjectMapper om;

    @MockitoBean AuthService authService;

    @Test
    void register_creaUsuario_retorna201() throws Exception {
        RegisterRequest req = new RegisterRequest("Juan", "juan@mail.com", "123456");
        doNothing().when(authService).registrar(anyString(), anyString(), anyString());

        mvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(content().string(""));
    }

    @Test
    void login_credencialesValidas_retornaTokenYDatos() throws Exception {
        LoginRequest req = new LoginRequest("juan@mail.com", "123456");
        LoginResponse res = new LoginResponse("Juan","juan@mail.com","TOKEN_FAKE","USER,ADMIN");
        when(authService.login("juan@mail.com", "123456")).thenReturn(res);

        mvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan"))
                .andExpect(jsonPath("$.email").value("juan@mail.com"))
                .andExpect(jsonPath("$.token").value("TOKEN_FAKE"))
                .andExpect(jsonPath("$.roles").value("USER,ADMIN"));
    }
}
