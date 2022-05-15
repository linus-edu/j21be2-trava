package se.mbi.be2.trava.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class ApiSecurityConfiguration {

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Order(1)
    @Bean
    public SecurityFilterChain filterChainApi(HttpSecurity http) throws Exception {
        http
                .antMatcher("/api/**")
                .csrf().disable()
                .cors().disable()
                .formLogin().disable()
                .authorizeRequests(authz -> authz
                        .antMatchers(HttpMethod.POST, "/api/users").permitAll()
                        .antMatchers(HttpMethod.POST, "/api/accesstoken").permitAll()
                        .antMatchers("/api/demo/**").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

//    @Order(2)
//    @Bean
//    public SecurityFilterChain filterChainWeb(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests().antMatchers("/", "/login").permitAll()
//                .and()
//                .authorizeRequests().anyRequest().authenticated()
//                .and()
//                .formLogin();
//        return http.build();
//    }

}
