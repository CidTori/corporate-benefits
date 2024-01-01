package com.example.backend.presentation.deposit;

import com.example.backend.domain.deposit.DepositType;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DepositResource(
        DepositType type,
        BigDecimal amount,
        LocalDate receptionDate
) {}
