package com.example.backend.deposit.domain.company;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
public class Company {
    private final Long id;
    private BigDecimal balance;
}
