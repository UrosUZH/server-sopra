package ch.uzh.ifi.seal.soprafs19.service;
import ch.uzh.ifi.seal.soprafs19.Application;
import ch.uzh.ifi.seal.soprafs19.controller.UserController;
import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for the UserResource REST resource.
 *
 * @see UserService
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes= Application.class)
@AutoConfigureMockMvc
public class UserControllerTest {



    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserController userController;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

/// I had some issues here that i couldn't resolve, somehow the tests were dependent on each other
    // i.e. when i edited one test other tests would succeed somehow or fail, while not being altered
    // i cant seem to find the reason why, i would have made more tests but those manipulated the previous
    // test results. ( would have tested the opposite)

    @Test
    public void createUser() throws Exception {

        this.mvc.perform(post("/user") // register/creating new user
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"testUserr\", \"password\": \"testPasswordd\"}"))
                .andExpect(status().is(201)).andDo(print());


        userRepository.delete(userRepository.findByUsername("testUser"));

    }

    @Test
    public void getProfile() throws Exception {
        User testUser = new User();
        testUser.setUsername("testUser");
        testUser.setPassword("testPassword");
        userService.createUser2(testUser);

        this.mvc.perform(get("/user/{id}","2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("testUser"))
                .andExpect(jsonPath("$.password").value("very secret password"))
                .andExpect(jsonPath("$.token", notNullValue()))
                .andExpect(jsonPath("$.status").value("ONLINE"))
                .andExpect(jsonPath("$.birthday").value("New"));}


        @Test
        public void loginUser() throws Exception {
        User testUser = new User();
        testUser.setUsername("testUser");
        testUser.setPassword("testPassword");
        userService.createUser2(testUser);


        this.mvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"testUser\", \"password\": \"testPassword\"}"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("testUser"))
                .andExpect(jsonPath("$.password").value("SecretPassword"))
                .andExpect(jsonPath("$.token", notNullValue()))
                .andExpect(jsonPath("$.status").value("ONLINE"))
                .andExpect(jsonPath("$.birthday").value("New"));

        userRepository.delete(userRepository.findByUsername("testUser"));

    }

    @Test
    public void updateUser() throws Exception {
        User testUser = new User();

        testUser.setUsername("testUser");
        testUser.setPassword("testPassword");
        userService.createUser2(testUser);

        this.mvc.perform(put("/user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"testUserUpdated\",\"id\": \"1\", \"birthday\": \"testBirthdayUpdated\"}"))
                .andExpect(status().is(204));
        userRepository.delete(userRepository.findByUsername("testUserUpdated"));

    }

}
