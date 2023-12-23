package com.example.backend.presentation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import javax.crypto.spec.SecretKeySpec;

import static java.nio.charset.StandardCharsets.UTF_8;

@Configuration
//@EnableMethodSecurity(jsr250Enabled = true)
public class SecurityConfiguration {
    @Bean
    public JwtDecoder jwtDecoder() {
        String secret = "your-256-bit-secretyour-256-bit-secret";
        SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(UTF_8), "HMACSHA256");
        return NimbusJwtDecoder.withSecretKey(secretKey).build();
    }
}
