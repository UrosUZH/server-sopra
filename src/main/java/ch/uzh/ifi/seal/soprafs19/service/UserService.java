package ch.uzh.ifi.seal.soprafs19.service;

import ch.uzh.ifi.seal.soprafs19.constant.UserStatus;
import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;


    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Iterable<User> getUsers() {
        return this.userRepository.findAll();
    }

    public Optional<User> getUser(Long id){
        return this.userRepository.findById(id);
    }

    public User checkUser(User newUser){
        User test = userRepository.findByUsername(newUser.getUsername());
        if (test.getName().equals(newUser.getName())){
            test.setError(5);
            test.setError(0);
        }
        else{
            test.setError(3);
            test.setError(2);
        }
        return test;

    }

    public User createUser(User newUser) {
        newUser.setToken(UUID.randomUUID().toString());
        newUser.setStatus(UserStatus.ONLINE);
        newUser.setDate();
        if (userRepository.findByUsername(newUser.getUsername())==null) {
            newUser.setError(0);
            userRepository.save(newUser);
            log.debug("Created Information for User: {}", newUser);
        }
        else {
            newUser.setError(1);
        }
        return newUser;
    }
}
