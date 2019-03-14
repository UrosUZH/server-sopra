package ch.uzh.ifi.seal.soprafs19.controller;

import ch.uzh.ifi.seal.soprafs19.constant.UserStatus;
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

    @GetMapping("/users") // get all users
    Iterable<User> all() {
        Iterable<User> everyBody = service.getUsers();
        for (User user : everyBody)
            user.setPassword("Very secret password");
        return everyBody;
    }


    @GetMapping("/user/{id}") // get profile page
    ResponseEntity getUser(@PathVariable Long id) {
        if (this.service.existID(id)){
            User test = service.getUser(id);
            test.setPassword("SecretPassword");
            test.setToken("SecretToken");
        return ResponseEntity.status(HttpStatus.OK).body(test);}
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found");
        }
    }


    @PostMapping("/user") //register Post: check if username is taken
    ResponseEntity createUser2(@RequestBody User newUser) {
        if (this.service.existUsername(newUser)){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Username already exists");
        }
        else{
            this.service.createUser2(newUser);
            return ResponseEntity.status(HttpStatus.CREATED).body("Location: /login");
        }

    }

    //@PostMapping("/users") //OLD
    //User createUser(@RequestBody User newUser) {
      //  return this.service.createUser(newUser);
    //}

    @PostMapping("/login") // for login check, also old
    User checkUser(@RequestBody User newUser) {
        User test = service.checkUser(newUser);
        test.setPassword("SecretPassword");
        return test;

    }

    @PutMapping("/user/{id}") // edit user Profile
    ResponseEntity updateUser(@RequestBody User oldUser, @PathVariable Long id){
        if (this.service.existID(id) & this.service.updateCheck(oldUser)){
            this.service.updateUser(oldUser);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Good: User id found and updated");
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with that id is not found" +
                    " or Username already taken");
            }
    }
}
