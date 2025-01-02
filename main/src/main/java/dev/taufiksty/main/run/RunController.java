package dev.taufiksty.main.run;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/runs")
public class RunController {

    private final RunRepository runRepository;

    public RunController(RunRepository runRepository) {
        this.runRepository = runRepository;
    }

    @GetMapping("")
    List<Run> findAllRuns() {
        return runRepository.findAll();
    }

    @GetMapping("/{id}")
    Run findRunById(@PathVariable Integer id) {
        Optional<Run> run = runRepository.findById(id);
        if (run.isEmpty()) {
            throw new RunException.RunNotFoundException("Run with id: " + id + " not found");
        }
        return run.get();
    }

    @GetMapping("/location/{location}")
    List<Run> findRunByLocation(@PathVariable String location) {
        return runRepository.findAllByLocation(Location.valueOf(location));
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    void createRun(@Valid @RequestBody Run run) {
        runRepository.save(run);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateRun(@Valid @PathVariable Integer id, @RequestBody Run run) {
        runRepository.save(run);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteRun(@PathVariable Integer id) {
        runRepository.delete(runRepository.findById(id).get());
    }
}
