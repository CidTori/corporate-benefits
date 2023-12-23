package com.example.backend.presentation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;
import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;
import static org.springframework.security.config.Customizer.withDefaults;

//@Profile("dev")
@Configuration
public class DevSecurityConfiguration {
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        //http.authorizeHttpRequests((requests) -> requests.anyRequest().authenticated());
        http.oauth2ResourceServer((resourceServer) -> resourceServer.jwt(withDefaults()));

        return http.build();
    }

    @Bean
    @Order(HIGHEST_PRECEDENCE)
    SecurityFilterChain swaggerUiSecurityFilterChain(HttpSecurity http) throws Exception {
        http.securityMatcher("/v3/**", "/swagger-ui/**");
        http.authorizeHttpRequests((requests) -> requests.anyRequest().permitAll());

        return http.build();
    }

    @Bean
    @Order(HIGHEST_PRECEDENCE)
    SecurityFilterChain h2ConsoleSecurityFilterChain(HttpSecurity http) throws Exception {
        http.securityMatcher(toH2Console());
        http.csrf(AbstractHttpConfigurer::disable);
        http.headers((headers) -> headers.frameOptions(FrameOptionsConfig::sameOrigin));

        return http.build();
    }
}


