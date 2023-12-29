package com.example.backend.presentation.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;
import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

@Profile("dev")
@Configuration
public class DevSecurityConfiguration {
    @Bean
    @Order(HIGHEST_PRECEDENCE)
    SecurityFilterChain swaggerUiSecurityFilterChain(HttpSecurity http) throws Exception {
        http.securityMatcher("/v3/**", "/swagger-ui/**");
        http.authorizeHttpRequests(requests -> requests.anyRequest().permitAll());

        return http.build();
    }

    @Bean
    @Order(HIGHEST_PRECEDENCE)
    SecurityFilterChain h2ConsoleSecurityFilterChain(HttpSecurity http) throws Exception {
        http.securityMatcher(toH2Console());
        http.csrf(AbstractHttpConfigurer::disable);
        http.headers(headers -> headers.frameOptions(FrameOptionsConfig::sameOrigin));

        return http.build();
    }
}


