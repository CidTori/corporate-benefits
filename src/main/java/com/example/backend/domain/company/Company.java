package com.example.backend.domain.company;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public final class Company {
    private Long id;
    private BigDecimal balance;
}
