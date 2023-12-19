package com.example.backend.domain.user;

import com.example.backend.domain.deposit.Deposit;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static java.math.BigDecimal.ZERO;

public abstract class User {
    public abstract void addDeposit(Deposit deposit);
    protected abstract List<Deposit> getDeposits();

    public final BigDecimal getBalance(LocalDate date) {
        return getDeposits().stream()
                .filter(d -> d.isNotExpired(date))
                .map(Deposit::getAmount)
                .reduce(ZERO, BigDecimal::add);
    }
}
