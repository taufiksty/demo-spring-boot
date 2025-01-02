package dev.taufiksty.main.run;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

import java.time.LocalDateTime;

public record Run(
        @Id
        Integer id,

        @NotEmpty
        String title,

        @JsonProperty("started_on")
        LocalDateTime startedOn,

        @JsonProperty("completed_on")
        LocalDateTime completedOn,

        @Positive
        Integer miles,

        Location location,

        @Version
        Integer version
) {

    public Run {
        if (!completedOn.isAfter(startedOn)) {
            throw new IllegalArgumentException("completedOn date-time cannot be before startedOn date-time");
        }
    }
}
