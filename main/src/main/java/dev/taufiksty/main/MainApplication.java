package dev.taufiksty.main;

import dev.taufiksty.main.user.User;
import dev.taufiksty.main.user.UserHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@SpringBootApplication
public class MainApplication {

    public static final Logger log = LoggerFactory.getLogger(MainApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    @Bean
    UserHttpClient userHttpClient(@Value("${user.service.url}") String baseUrl) {
        RestClient restClient = RestClient.create(baseUrl);
        HttpServiceProxyFactory  factory = HttpServiceProxyFactory
                .builderFor(RestClientAdapter.create(restClient))
                .build();
        return factory.createClient(UserHttpClient.class);
    }

    @Bean
    CommandLineRunner runner(UserHttpClient userHttpClient) {
        return args -> {
            User user = userHttpClient.findById(1);
            log.info("User id: {}, name: {}", user.id(), user.name());
        };
    }
}
