package dev.taufiksty.main;

import dev.taufiksty.main.run.Location;
import dev.taufiksty.main.run.Run;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class MainApplication {

    public static final Logger log = LoggerFactory.getLogger(MainApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    @Bean
    CommandLineRunner runner() {
        return args -> {
            Run run = new Run(
                    1,
                    "First Run",
                    LocalDateTime.now(),
                    LocalDateTime.now().plusHours(1),
                    5,
                    Location.OUTDOOR
            );
            log.info("Run: {}", run);
        };
    }
}
