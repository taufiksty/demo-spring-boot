package dev.taufiksty.main.run;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RunControllerIntegrationTest {

    @LocalServerPort
    private int randomServerPort;

    RestClient restClient;

    @BeforeEach
    void setUp() {
        restClient = RestClient.create("http://localhost:" + randomServerPort);
    }

    @Test
    void shouldReturnAllRuns() {
        List<Run> allRuns = restClient.get()
                .uri("/api/v1/runs")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});

        assert allRuns != null;
        assertEquals(2, allRuns.size());
    }

}