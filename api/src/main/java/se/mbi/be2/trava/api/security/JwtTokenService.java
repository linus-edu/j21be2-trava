package se.mbi.be2.trava.api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import se.mbi.be2.trava.api.service.UserService;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Service
public class JwtTokenService {

    private static final Logger LOG = LoggerFactory.getLogger(JwtTokenService.class);

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    private final String secret;

    private Key jwtKey;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Taking the key in the constructor to be able to inject it from unit test
    @Autowired
    JwtTokenService(@Value("${jwt.secret:}") String secret) {
       this.secret = secret;
        init();
    }

    public void init() {
        if (secret.isEmpty()) {
            jwtKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
            LOG.warn("JWT secret not set, using a new: {}", Encoders.BASE64.encode(jwtKey.getEncoded()));
        } else {
            byte[] keyBytes = Decoders.BASE64.decode(secret);
            jwtKey = Keys.hmacShaKeyFor(keyBytes);
        }
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return (Claims) Jwts.parserBuilder()
                // .requireAudience("string")
                .setSigningKey(jwtKey)
                .build()
                .parse(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(UserDetails userDetails) { // TODO Does not have to take UserDetails
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(jwtKey, SignatureAlgorithm.HS512)
                .compact();
    }

    public UserDetails validateToken(String jwtToken) {
        String username = getUsernameFromToken(jwtToken);
        UserDetails userDetails = userService.loadUserByUsername(username);
        if (userDetails != null && !isTokenExpired(jwtToken)) {
            return userDetails;
        }
        return null;
    }

    public Optional<String> validateAndGetAccessToken(String username, String cleartextPasswordToVerify) {
        UserDetails userDetails = userService.loadUserByUsername(username);
        String encodedPassword = userDetails.getPassword();
        boolean passwordIsCorrect = passwordEncoder.matches(cleartextPasswordToVerify, encodedPassword);
        return Optional.of(generateToken(userDetails));
    }

}
