package com.example.backend.domain.deposit;

import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;

public abstract class Deposit {
    protected abstract DepositType getType();
    public abstract BigDecimal getAmount();
    protected abstract LocalDate getReceptionDate();

    public final boolean isNotExpired(LocalDate date) {
        return getType().isNotExpired(date, getReceptionDate());
    }
}
