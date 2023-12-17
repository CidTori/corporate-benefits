package com.example.wedoogift.backend.domain.company;

import com.example.wedoogift.backend.domain.deposit.Deposit;
import com.example.wedoogift.backend.domain.user.User;

import java.math.BigDecimal;

public record Company(BigDecimal balance) {
    public void sendDeposit(User user, Deposit deposit) throws InsufficientBalanceException {
        if (this.balance().compareTo(deposit.getAmount()) < 0)
            throw new InsufficientBalanceException("The balance is insufficient for a deposit of " + deposit.getAmount());

        user.addDeposit(deposit);
    }
}
