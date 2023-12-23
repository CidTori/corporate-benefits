package com.example.backend.domain.company;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public final class Company {
    private BigDecimal balance;
}
