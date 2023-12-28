package com.example.backend.domain.employee.deposit;

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
