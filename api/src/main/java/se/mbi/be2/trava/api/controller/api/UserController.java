package se.mbi.be2.trava.api.controller.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import se.mbi.be2.trava.api.model.UserEntity;
import se.mbi.be2.trava.api.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    record CreateUserDto(String username, String password) {}

    @PostMapping("")
    public void createUser(@RequestBody CreateUserDto createUserDto) {
        LOG.info("Creating user {}", createUserDto.username);
        userService.createUser(createUserDto.username, createUserDto.password);
    }

    @GetMapping("")
    public Iterable<UserEntity> getUsers(@AuthenticationPrincipal UserDetails user) {
        LOG.info("Sending list of all users to user='{}'", user.getUsername());
        return userService.getAllUsers();
    }

}
