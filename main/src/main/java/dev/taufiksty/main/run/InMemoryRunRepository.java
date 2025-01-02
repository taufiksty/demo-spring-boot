package dev.taufiksty.main.run;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class InMemoryRunRepository {

    private static final Logger log = LoggerFactory.getLogger(InMemoryRunRepository.class);
    private final List<Run> runs = new ArrayList<>();

    public List<Run> findAll() {
        return runs;
    }

    public Optional<Run> findById(Integer id) {
        return Optional.of(
                runs.stream()
                        .filter(run -> run.id().equals(id))
                        .findFirst()
                        .orElseThrow(() -> new RunException.RunNotFoundException("Run Not Found"))
                );
    }

    public void create(Run run) {
        Run newRun = new Run(run.id(), run.title(), run.startedOn(), run.completedOn(), run.miles(), run.location(), run.version());
        runs.add(run);
    }

    public void update(Run newRun, Integer id) {
        Optional<Run> existingRun = findById(id);
        if (existingRun.isPresent()) {
            Run run = existingRun.get();
            runs.set(runs.indexOf(run), newRun);
        } else {
            log.error("Run with id {} not found", id);
        }
    }

    public void delete(Integer id) {
        runs.removeIf(run -> run.id().equals(id));
    }

    public void saveAll(List<Run> runs) {
        runs.forEach(this::create);
    }

    public Integer count() {
        return runs.size();
    }

    public List<Run> findByLocation(String location) {
        return runs.stream().filter(run -> Objects.equals(run.location(), location)).toList();
    }

    @PostConstruct
    private void init() {
        runs.add(new Run(1, "Monday Morning Run", LocalDateTime.now(), LocalDateTime.now().plusMinutes(30), 3, Location.INDOOR, 1));
        runs.add(new Run(2, "Tuesday Morning Run", LocalDateTime.now(), LocalDateTime.now().plusMinutes(30), 4, Location.OUTDOOR, 1));
        runs.add(new Run(3, "Wednesday Morning Run", LocalDateTime.now(), LocalDateTime.now().plusMinutes(30), 5, Location.OUTDOOR, 1));
    }
}
