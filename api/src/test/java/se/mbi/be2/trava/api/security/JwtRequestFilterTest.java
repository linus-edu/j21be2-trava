package se.mbi.be2.trava.api.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.*;

@SpringBootTest
class JwtRequestFilterTest {

    // This is a SpringBootTest, so Autowire will work
    @Autowired
    JwtRequestFilter jwtRequestFilter;

    @Test
    // Example of Mockito
    void doFilterInternal() throws Exception {
        // Given a request, response and filter chain
        var request = mock(HttpServletRequest.class);
        var response = mock(HttpServletResponse.class);
        var chain = mock(FilterChain.class);
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Banana");

        // When the filter processes the request
        jwtRequestFilter.doFilterInternal(request, response, chain);

        // It should call the next filter in the chain
        verify(chain).doFilter(any(), same(response));
    }
}