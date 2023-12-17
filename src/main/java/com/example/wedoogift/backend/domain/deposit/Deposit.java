package com.example.wedoogift.backend.domain.deposit;

import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;

@Value(staticConstructor = "of")
public class Deposit {
    DepositType type;
    BigDecimal amount;
    LocalDate receptionDate;

    public boolean isNotExpired(LocalDate date) {
        return type.isNotExpired(date, receptionDate);
    }
}
