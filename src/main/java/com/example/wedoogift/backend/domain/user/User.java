package com.example.wedoogift.backend.domain.user;

import com.example.wedoogift.backend.domain.deposit.Deposit;
import lombok.Value;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.math.BigDecimal.ZERO;

@Value
public class User {
    List<Deposit> deposits = new ArrayList<>();

    public void addDeposit(Deposit deposit) {
        deposits.add(deposit);
    }

    public BigDecimal getBalance() {
        return deposits.stream()
                .filter(Deposit::isNotExpired)
                .map(Deposit::getAmount)
                .reduce(ZERO, BigDecimal::add);
    }
}
