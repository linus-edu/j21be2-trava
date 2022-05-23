package se.mbi.be2.trava.api.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import se.mbi.be2.trava.api.service.UserService;

@Component
public class CreateDefaultUserOnStart implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(CreateDefaultUserOnStart.class);

    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) {
        // TODO Check if it exists first
        LOG.info("Creating test user");
        userService.createUser("user", "pass");
    }

}
