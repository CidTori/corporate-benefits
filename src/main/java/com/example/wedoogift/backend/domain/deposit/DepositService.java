package com.example.wedoogift.backend.domain.deposit;

import com.example.wedoogift.backend.domain.company.Company;
import com.example.wedoogift.backend.domain.company.InsufficientBalanceException;
import com.example.wedoogift.backend.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Clock;
import java.util.function.Supplier;

import static com.example.wedoogift.backend.domain.deposit.DepositType.*;
import static java.time.LocalDate.now;

@Service
@RequiredArgsConstructor
public class DepositService {
    private final Supplier<Clock> clockSupplier;

    public void sendGiftDeposit(Company company, User user, BigDecimal amount) throws InsufficientBalanceException {
        sendDeposit(company, user, amount, GIFT);
    }

    public void sendMealDeposit(Company company, User user, BigDecimal amount) throws InsufficientBalanceException {
        sendDeposit(company, user, amount, MEAL);
    }

    private void sendDeposit(Company company, User user, BigDecimal amount, DepositType meal) throws InsufficientBalanceException {
        if (company.balance().compareTo(amount) < 0)
            throw new InsufficientBalanceException("The balance is insufficient for a deposit of " + amount);

        user.addDeposit(Deposit.of(meal, amount, now(clockSupplier.get())));
    }
}
