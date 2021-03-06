package se.mbi.be2.trava.api.security;

import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// @Profile("!test")O
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final Logger LOG = LoggerFactory.getLogger(JwtRequestFilter.class);

    @Autowired
    private JwtTokenService jwtTokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        boolean notAlreadyAuthenticated = SecurityContextHolder.getContext().getAuthentication() == null;
        final String bearerToken = getBearerTokenIfAvailable(request.getHeader(HttpHeaders.AUTHORIZATION));

        if (notAlreadyAuthenticated && bearerToken != null) {
            try {
                UserDetails userDetails = jwtTokenService.validateToken(bearerToken);
                if (userDetails != null) {
                    var usernamePasswordAuthToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthToken);
                }
            } catch (SignatureException e) {
                LOG.info("Got invalid JWT token");
            } catch (MalformedJwtException e) {
                LOG.info("Got malformed JWT token");
            }
        }

        chain.doFilter(request, response);
    }

    private String getBearerTokenIfAvailable(String authHeaderValue) {
        if (authHeaderValue != null && authHeaderValue.startsWith("Bearer ")) {
            return authHeaderValue.substring(7);
        }
        return null;
    }
}