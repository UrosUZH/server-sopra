package ch.uzh.ifi.seal.soprafs19.controller;

import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Optional;


@RestController

public class UserController  {

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

    ResponseEntity getUser(@PathVariable Long id) {
        if (this.service.existID(id)){
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getUser(id));}
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found");
        }
    }


    @PostMapping("/user")
    ResponseEntity createUser2(@RequestBody User newUser) {
        if (this.service.existUsername(newUser)){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Username already exists");
        }
        else{
            this.service.createUser2(newUser);
            return ResponseEntity.status(HttpStatus.CREATED).body("Location: /login");
        }

    }

    @PostMapping("/users")
    User createUser(@RequestBody User newUser) {
        return this.service.createUser(newUser);
    }

    @PostMapping("/login")
    User checkUser(@RequestBody User newUser) {
        return this.service.checkUser(newUser);
    }

    @PutMapping("/user/{id}")

    ResponseEntity updateUser(@RequestBody User oldUser, @PathVariable Long id){
        if (this.service.existID(id) & this.service.updateCheck(oldUser)){
            this.service.updateUser(oldUser);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Test: User id found and updated");
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with that id is not found" +
                    " or Username already taken");
            }
    }
}
