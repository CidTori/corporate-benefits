package com.example.backend.domain.employee;

import com.example.backend.domain.deposit.Deposit;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Value
public class Employee {
    Long id;
    List<Deposit> deposits = new ArrayList<>();

    public BigDecimal getBalance(LocalDate date) {
        return deposits.stream()
                .filter(d -> d.isNotExpired(date))
                .map(Deposit::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
