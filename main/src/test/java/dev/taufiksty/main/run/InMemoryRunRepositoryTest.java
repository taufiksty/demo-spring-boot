package dev.taufiksty.main.run;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryRunRepositoryTest {

    InMemoryRunRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryRunRepository();
        repository.create(new Run(1, "Monday", LocalDateTime.now(), LocalDateTime.now().plusMinutes(30), 3, Location.OUTDOOR, null));
        repository.create(new Run(2, "Tuesday", LocalDateTime.now(), LocalDateTime.now().plusMinutes(30), 5, Location.INDOOR, null));
    }

    @Test
    void shouldFindAllRuns() {
        List<Run> runs = repository.findAll();
        assertEquals(2, runs.size(), "Should have returned 2 runs");
    }

    @Test
    void shouldFindRunById() {
        Optional<Run> run = repository.findById(1);
        assertEquals(1, run.get().id(), "Should have returned run with id 1");
    }

    @Test
    void shouldNotFindRunById() {
        RunException.RunNotFoundException runNotFoundException = assertThrows(
                RunException.RunNotFoundException.class,
                () -> repository.findById(3),
                "Should have thrown RunNotFoundException"
        );
        assertEquals("Run Not Found", runNotFoundException.getMessage(), "Should have thrown RunNotFoundException");
    }

    @Test
    void shouldCreateRun() {
        repository.create(new Run(3, "Wednesday", LocalDateTime.now(), LocalDateTime.now().plusMinutes(30), 3, Location.OUTDOOR, null));
        List<Run> runs = repository.findAll();
        assertEquals(3, runs.size(), "Should have returned 3 runs");
    }

    @Test
    void shouldUpdateRun() {
        Run run = new Run(1, "Monday Updated", LocalDateTime.now(), LocalDateTime.now().plusMinutes(30), 3, Location.OUTDOOR, null);
        repository.update(run, 1);
        Optional<Run> updatedRun = repository.findById(1);
        assertEquals("Monday Updated", updatedRun.get().title(), "Should have returned run with title Monday Updated");
    }

    @Test
    void shouldDeleteRun() {
        repository.delete(1);
        List<Run> runs = repository.findAll();
        assertEquals(1, runs.size(), "Should have returned 1 run");
    }

}