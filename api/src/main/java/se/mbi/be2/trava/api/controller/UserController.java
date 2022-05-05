package se.mbi.be2.trava.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import se.mbi.be2.trava.api.model.UserEntity;
import se.mbi.be2.trava.api.repository.UserRepository;
import se.mbi.be2.trava.api.security.JwtTokenService;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UserRepository userRepository;

//    final SignupService signupService, final PasswordEncoder passwordEncoder

//    @PostMapping("/login")
    @GetMapping("/login")
    public String login() {
//        return "login ok";
        UserDetails userDetails = new User("test", "test", new ArrayList<>()); // FIXME
        return jwtTokenService.generateToken(userDetails);
    }

//    @PostMapping("/user/signup")
//    public void signUp(@RequestBody SignUpRequestDto signUpRequest) {
//        LOG.info("Creating user {}", signUpRequest.getUsername());
//        signupService.signUp(signUpRequest.getUsername(), passwordEncoder.encode(signUpRequest.getPassword()), signUpRequest.getRoles());
//    }

    // eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0IiwiaWF0IjoxNjUxNjI3Njk0LCJleHAiOjE2NTE2NDU2OTR9.yhE6lJiz3EWp3WMxLa_QU7K2N37LEWC096mVTHyuSVR2kmic_IncgvYIigea-fPiE_B6Mkn-2n1XBNrQuEdPyQ

    @GetMapping("/admintest")
    public String admintest(@AuthenticationPrincipal UserEntity user) {
//        return "hello, " + user.getUsername();
        return "hello, " + user.getName();
//        return "Admin";
    }

    @GetMapping("/users")
    public List<UserEntity> getUsers() {
        // FIXME - Do properly
        List<UserEntity> usersList = new ArrayList<>();
        userRepository.findAll().forEach(usersList::add);
        return usersList;
    }

}
