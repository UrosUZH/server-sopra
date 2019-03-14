package ch.uzh.ifi.seal.soprafs19.service;

import ch.uzh.ifi.seal.soprafs19.constant.UserStatus;
import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public User getUser(Long id){
        return userRepository.findById(id).get();
    }
    public boolean existID(Long id){
        return this.userRepository.existsById(id);
    }
    public boolean existUsername(User newUser){
        if (userRepository.findByUsername(newUser.getUsername())==null){
            return false;
        }
        else {
            return true;
        }
    }

    public User checkUser(User newUser){
        User test = userRepository.findByUsername(newUser.getUsername());
        if (test.getPassword().equals(newUser.getPassword())){
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
    public void createUser2(User newUser) {
        newUser.setToken(UUID.randomUUID().toString());
        newUser.setStatus(UserStatus.ONLINE);
        newUser.setDate();
        newUser.setBirthday("New");
        newUser.setError(100);
        userRepository.save(newUser);
        log.debug("Created Information for User: {}", newUser);
    }
    public void updateUser(User oldUser){
        if (oldUser.getUsername()!=null){
         this.userRepository.findById(oldUser.getId()).get().setUsername(oldUser.getUsername());}
        if(!oldUser.getBirthday().equals("")){
         this.userRepository.findById(oldUser.getId()).get().setBirthday(oldUser.getBirthday());}
         this.userRepository.save(this.userRepository.findById(oldUser.getId()).get());
    }
    public boolean updateCheck(User updateUser){
        if (this.userRepository.findByUsername(updateUser.getUsername())==null){
            return true;
        }
        else{
            return false;
        }
    }
}
