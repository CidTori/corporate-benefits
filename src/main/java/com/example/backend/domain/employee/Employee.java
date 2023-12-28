package com.example.backend.domain.employee;

import com.example.backend.domain.employee.deposit.Deposit;
import com.example.backend.domain.employee.deposit.DepositType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static java.math.BigDecimal.ZERO;

public abstract class Employee {
    public abstract void addDeposit(DepositType type, BigDecimal amount, LocalDate receptionDate);
    protected abstract List<Deposit> getDeposits();

    public final BigDecimal getBalance(LocalDate date) {
        return getDeposits().stream()
                .filter(d -> d.isNotExpired(date))
                .map(Deposit::getAmount)
                .reduce(ZERO, BigDecimal::add);
    }
}
