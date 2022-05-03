package se.mbi.be2.trava.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import se.mbi.be2.trava.api.model.User;
import se.mbi.be2.trava.api.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public List<User> getUsers() {
        // FIXME - Do properly
        List<User> usersList = new ArrayList<>();
        userRepository.findAll().forEach(usersList::add);
        return usersList;
    }

}
