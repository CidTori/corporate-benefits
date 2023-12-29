package com.example.backend.deposit.domain.company;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Company {
    private final Long id;
    private BigDecimal balance;
}
