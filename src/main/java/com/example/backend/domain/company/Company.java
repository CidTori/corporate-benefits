package com.example.backend.domain.company;

import lombok.Data;

import java.math.BigDecimal;

import static java.math.BigDecimal.valueOf;

@Data
public class Company {
    private final Long id;
    private BigDecimal balance = valueOf(0);
}
