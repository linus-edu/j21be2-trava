package se.mbi.be2.trava.api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenService {

    private static final Logger LOG = LoggerFactory.getLogger(JwtTokenService.class);

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    @Value("${jwt.secret:}")
    private String secret;

    private Key jwtKey;

    @PostConstruct
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

    // nj44F1EmJLNBW2BJZt+FzF0+B8TiM88gr3jjd/r/4OArAKIRAweOc+g8F9dXI5JAFwGdT0lzJ/PyI5zKU0xPpQ==
    // Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0IiwiaWF0IjoxNjUxNjI5MDM5LCJleHAiOjE2NTE2NDcwMzl9.hicIjtYGbqgKrvhndT4Q9FK4Ct7Y1s5Q-aXXLXf1_8z-FHbV3ITuf4GiwLerbUaInmPm80QeceuzCazJ63enxQ

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

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}
