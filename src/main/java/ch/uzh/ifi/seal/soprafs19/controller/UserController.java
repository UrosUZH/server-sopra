package ch.uzh.ifi.seal.soprafs19.controller;

import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserController {

    private final UserService service;

    UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/users")
    Iterable<User> all() {
        return service.getUsers();
    }

    @GetMapping("/login")
    Iterable<User> some() {
        return service.getUsers();
    }

    @GetMapping("/user/{id}")
    ResponseEntity<Optional<User>> getUser(@PathVariable Long id) { return ResponseEntity.status(HttpStatus.OK).body(this.service.getUser(id));
    }

    @PostMapping("/users")
    User createUser(@RequestBody User newUser) {
        return this.service.createUser(newUser);
    }

    @PostMapping("/login")
    User checkUser(@RequestBody User newUser) {
        return this.service.checkUser(newUser);
    }
}
