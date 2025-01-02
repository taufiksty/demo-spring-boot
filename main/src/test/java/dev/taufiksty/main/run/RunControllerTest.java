package dev.taufiksty.main.run;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RunController.class)
class RunControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    RunRepository repository;

    private final List<Run> runs = new ArrayList<>();

    @BeforeEach
    void setUp() {
        runs.add(new Run(1, "Monday", LocalDateTime.now(), LocalDateTime.now().plusMinutes(30), 3, Location.OUTDOOR, null));
        runs.add(new Run(2, "Tuesday", LocalDateTime.now(), LocalDateTime.now().plusMinutes(30), 5, Location.INDOOR, null));
    }

    @Test
    void shouldFindAllRuns() throws Exception {
        when(repository.findAll()).thenReturn(runs);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/runs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void shouldFindOneRun() throws Exception {
        Run run = runs.get(0);
        when(repository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(run));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/runs/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Monday"));
    }

    @Test
    void shouldReturnNotFoundWithInvalid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/runs/3"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateNewRun() throws Exception {
        Run run = new Run(3, "Wednesday", LocalDateTime.now(), LocalDateTime.now().plusMinutes(30), 3, Location.OUTDOOR, null);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/runs")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(run)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldUpdateRun() throws Exception {
        Run run = new Run(1, "Monday Updated", LocalDateTime.now(), LocalDateTime.now().plusMinutes(30), 3, Location.OUTDOOR, null);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/runs/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(run)))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldDeleteRun() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/runs/1"))
                .andExpect(status().isNoContent());
    }

}