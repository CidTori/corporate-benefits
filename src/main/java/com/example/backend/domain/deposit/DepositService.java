package com.example.backend.domain.deposit;

import com.example.backend.domain.company.Company;
import com.example.backend.domain.company.InsufficientCompanyBalanceException;
import com.example.backend.domain.employee.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Clock;
import java.util.function.Supplier;

import static com.example.backend.domain.deposit.DepositType.GIFT;
import static com.example.backend.domain.deposit.DepositType.MEAL;
import static java.time.LocalDate.now;

@Service
@RequiredArgsConstructor
public class DepositService {
    private final Supplier<Clock> clockSupplier;

    public void sendGift(Company company, Employee employee, BigDecimal amount) throws InsufficientCompanyBalanceException {
        sendDeposit(GIFT, company, employee, amount);
    }

    public void sendMeal(Company company, Employee employee, BigDecimal amount) throws InsufficientCompanyBalanceException {
        sendDeposit(MEAL, company, employee, amount);
    }

    private void sendDeposit(
            DepositType meal,
            Company company,
            Employee employee,
            BigDecimal amount
    ) throws InsufficientCompanyBalanceException {
        if (company.getBalance().compareTo(amount) < 0)
            throw new InsufficientCompanyBalanceException("The balance is insufficient for a deposit of " + amount);

        company.setBalance(company.getBalance().subtract(amount));

        employee.addDeposit(new Deposit(meal, amount, now(clockSupplier.get())));
    }
}
