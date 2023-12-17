package com.example.wedoogift.backend.domain.user;

import com.example.wedoogift.backend.domain.deposit.Deposit;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.math.BigDecimal.ZERO;

@Value
public class User {
    List<Deposit> deposits = new ArrayList<>();

    public void addDeposit(Deposit deposit) {
        deposits.add(deposit);
    }

    public BigDecimal getBalance(LocalDate date) {
        return deposits.stream()
                .filter(d -> d.isNotExpired(date))
                .map(Deposit::getAmount)
                .reduce(ZERO, BigDecimal::add);
    }
}
