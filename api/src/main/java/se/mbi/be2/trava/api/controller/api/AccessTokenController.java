package se.mbi.be2.trava.api.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import se.mbi.be2.trava.api.security.JwtTokenService;

@RestController
@RequestMapping("/api/accesstoken")
public class AccessTokenController {

    @Autowired
    private JwtTokenService jwtTokenService;

    record CreateAccessTokenDto(String username, String password) {
    }

    @PostMapping("")
    public String createAccessToken(@RequestBody CreateAccessTokenDto createAccessTokenDto) {
        return jwtTokenService.validateAndGetAccessToken(createAccessTokenDto.username, createAccessTokenDto.password)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect username or password"));
    }

}
