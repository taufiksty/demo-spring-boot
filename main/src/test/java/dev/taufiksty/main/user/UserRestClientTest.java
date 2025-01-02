package dev.taufiksty.main.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(UserRestClient.class)
class UserRestClientTest {

    @Autowired
    MockRestServiceServer server;

    @Autowired
    UserRestClient userRestClient;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void shouldFindAllUsers() throws JsonProcessingException {
        User user1 = new User(1, "Leanne Graham", "Bret", "Sincere@april.biz",
                new Address("Kulas Light", "Apt. 556", "Gwenborough", "92998-3874",
                        new Geo("-37.3159", "81.1496")), "1-770-736-8031 x56442", "hildegard.org",
                new Company("Romaguera-Crona", "Multi-layered client-server neural-net", "harness real-time e-markets"));

        List<User> users = List.of(user1);

        this.server.expect(requestTo("https://jsonplaceholder.typicode.com/users"))
                .andRespond(withSuccess(objectMapper.writeValueAsString(users), MediaType.APPLICATION_JSON));

        List<User> response = userRestClient.findAll();
        assertEquals(users, response);
    }

    @Test
    void shouldFindUserById() throws JsonProcessingException {
        User user1 = new User(1, "Leanne Graham", "Bret", "XXXXXXXXXXXXXXXXX",
                new Address("Kulas Light", "Apt. 556", "Gwenborough", "92998-3874",
                        new Geo("-37.3159", "81.1496")), "00000000000000 x56442", "hildegard.org",
                new Company("Romaguera-Crona", "Multi-layered client-server neural-net", "harness real-time e-markets"));

        this.server.expect(requestTo("https://jsonplaceholder.typicode.com/users/1"))
                .andRespond(withSuccess(objectMapper.writeValueAsString(user1), MediaType.APPLICATION_JSON));

        User response = userRestClient.findById(1);
        assertEquals(user1, response);
    }

}