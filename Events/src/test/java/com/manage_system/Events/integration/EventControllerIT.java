package com.manage_system.Events.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.manage_system.Events.Infrastucture.controller.dto.EventCreateDto;
import com.manage_system.Events.util.JwtTestUtil;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Sql(scripts = "/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EventControllerIT {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    private String adminToken;


    @BeforeEach
    void setUp() {
        adminToken = "Bearer " + JwtTestUtil.generateAdminToken();
    }

    // ------------------------------------
    // 1. CREATE
    // ------------------------------------
    @Test
    @Order(1)
    void shouldCreateEvent() throws Exception {

        EventCreateDto dto = new EventCreateDto(
                "Evento Integración",
                "Prueba",
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(1).plusHours(1),
                1
        );

        mvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", adminToken)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Evento Integración"));
    }

    // ------------------------------------
    // 2. GET ALL
    // ------------------------------------
    @Test
    @Order(2)
    void shouldGetAllEvents() throws Exception {
        mvc.perform(get("/api/events")
                        .header("Authorization", adminToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    // ------------------------------------
    // 3. GET BY ID
    // ------------------------------------
    @Test
    @Order(3)
    void shouldGetEventById() throws Exception {
        mvc.perform(get("/api/events/1")
                        .header("Authorization", adminToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    // ------------------------------------
    // 4. UPDATE
    // ------------------------------------
    @Test
    @Order(4)
    void shouldUpdateEvent() throws Exception {

        EventCreateDto dto = new EventCreateDto(
                "Evento Actualizado",
                "Actualización",
                LocalDateTime.now().plusDays(2),
                LocalDateTime.now().plusDays(2).plusHours(1),
                1
        );

        mvc.perform(put("/api/events/1")
                        .header("Authorization", adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Evento Actualizado"));
    }

    // ------------------------------------
    // 5. FILTER
    // ------------------------------------
    @Test
    @Order(5)
    void shouldFilterEvents() throws Exception {
        mvc.perform(get("/api/events/filter?venueId=1")
                        .header("Authorization", adminToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    // ------------------------------------
    // 6. DELETE
    // ------------------------------------
    @Test
    @Order(6)
    void shouldDeleteEvent() throws Exception {
        mvc.perform(delete("/api/events/1")
                        .header("Authorization", adminToken))
                .andExpect(status().isNoContent());
    }

    // ------------------------------------
    // 7. 404 AFTER DELETE
    // ------------------------------------
    @Test
    @Order(7)
    void shouldReturn404AfterDelete() throws Exception {
        mvc.perform(get("/api/events/1")
                        .header("Authorization", adminToken))
                .andExpect(status().isNotFound());
    }
}
