package dev.taufiksty.main.user;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;

@HttpExchange("/users")
public interface UserHttpClient {

    @GetExchange("")
    List<User> findAll();

    @GetExchange("/{id}")
    User findById(@PathVariable Integer id);

}
