package com.example.backend.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.util.function.Supplier;

@Configuration
public class ClockConfiguration {
    @Bean
    public Supplier<Clock> clockSupplier() {
        return Clock::systemDefaultZone;
    }
}
