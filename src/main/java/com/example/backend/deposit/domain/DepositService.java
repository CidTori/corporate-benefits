package com.example.backend.deposit.domain;

import com.example.backend.deposit.domain.company.Company;
import com.example.backend.deposit.domain.company.InsufficientCompanyBalanceException;
import com.example.backend.utils.TriFunction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDate;
import java.util.function.Supplier;

import static java.time.LocalDate.now;

@Service
@RequiredArgsConstructor
public class DepositService {
    private final Supplier<Clock> clockSupplier;

    public Gift sendGift(Company company, Long employeeId, BigDecimal amount) throws InsufficientCompanyBalanceException {
        return sendDeposit(Gift::new, company, employeeId, amount);
    }

    public Meal sendMeal(Company company, Long employeeId, BigDecimal amount) throws InsufficientCompanyBalanceException {
        return sendDeposit(Meal::new, company, employeeId, amount);
    }

    private <T extends Deposit> T sendDeposit(
            TriFunction<T, BigDecimal, LocalDate, Long> constructor,
            Company company,
            Long employeeId,
            BigDecimal amount
    ) throws InsufficientCompanyBalanceException {
        if (company.getBalance().compareTo(amount) < 0)
            throw new InsufficientCompanyBalanceException("The balance is insufficient for a deposit of " + amount);

        company.setBalance(company.getBalance().subtract(amount));

        return constructor.apply(amount, now(clockSupplier.get()), employeeId);
    }
}
