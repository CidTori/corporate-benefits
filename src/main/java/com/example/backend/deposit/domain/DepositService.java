package com.example.backend.deposit.domain;

import com.example.backend.deposit.domain.company.Company;
import com.example.backend.deposit.domain.company.InsufficientCompanyBalanceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Clock;
import java.util.function.Supplier;

import static com.example.backend.deposit.domain.DepositType.GIFT;
import static com.example.backend.deposit.domain.DepositType.MEAL;
import static java.time.LocalDate.now;

@Service
@RequiredArgsConstructor
public class DepositService {
    private final Supplier<Clock> clockSupplier;

    public Deposit sendGift(Company company, Long employeeId, BigDecimal amount) throws InsufficientCompanyBalanceException {
        return sendDeposit(GIFT, company, employeeId, amount);
    }

    public Deposit sendMeal(Company company, Long employeeId, BigDecimal amount) throws InsufficientCompanyBalanceException {
        return sendDeposit(MEAL, company, employeeId, amount);
    }

    private Deposit sendDeposit(
            DepositType type,
            Company company,
            Long employeeId,
            BigDecimal amount
    ) throws InsufficientCompanyBalanceException {
        if (company.getBalance().compareTo(amount) < 0)
            throw new InsufficientCompanyBalanceException("The balance is insufficient for a deposit of " + amount);

        company.setBalance(company.getBalance().subtract(amount));

        return new Deposit(type, amount, now(clockSupplier.get()), employeeId);
    }
}
