package com.example.backend.domain.deposit;

import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;

@Value
public class Deposit {
    Long id;
    DepositType type;
    BigDecimal amount;
    LocalDate receptionDate;
    Long employeeId;

    public boolean isNotExpired(LocalDate date) {
        return type.isNotExpired(date, receptionDate);
    }
}