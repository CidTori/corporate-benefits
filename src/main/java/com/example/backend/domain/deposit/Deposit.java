package com.example.backend.domain.deposit;

import java.math.BigDecimal;
import java.time.LocalDate;

public record Deposit(
        DepositType type,
        BigDecimal amount,
        LocalDate receptionDate
) {
    public boolean isNotExpired(LocalDate date) {
        return type.isNotExpired(date, receptionDate);
    }
}
