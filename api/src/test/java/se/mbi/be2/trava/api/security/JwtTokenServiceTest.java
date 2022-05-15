package se.mbi.be2.trava.api.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;


class JwtTokenServiceTest {

    @Test
    // Example of JUnit
    void canGenerateToken() {
        // Given a valid key and a user
        String secretKey = "nj44F1EmJLNBW2BJZt+FzF0+B8TiM88gr3jjd/r/4OArAKIRAweOc+g8F9dXI5JAFwGdT0lzJ/PyI5zKU0xPpQ==";
        User user = new User("user", "pass", Collections.emptyList());

        // When we request a token
        JwtTokenService jwtTokenService = new JwtTokenService(secretKey);
        String token = jwtTokenService.generateToken(user);

        // Then the token should have 3 parts separated by "."
        assertEquals(3, token.split("\\.").length);
    }
}