package com.example.backend.application;

import com.example.backend.domain.company.Company;
import com.example.backend.domain.company.InsufficientBalanceException;
import com.example.backend.domain.deposit.DepositService;
import com.example.backend.domain.employee.Employee;
import com.example.backend.utils.ThrowingTriConsumer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Clock;
import java.util.function.Supplier;

import static java.time.LocalDate.now;

@Service
@RequiredArgsConstructor
public class DepositApplicationService {
    private final DepositService depositService;
    private final CompanyRepository companyRepository;
    private final EmployeeRepository employeeRepository;
    private final Supplier<Clock> clockSupplier;

    public BigDecimal getBalance(Long employeeId) {
        return employeeRepository.findById(employeeId)
                .map(u -> u.getBalance(now(clockSupplier.get())))
                .orElseThrow();
    }

    public void sendGift(Long companyId, Long employeeId, BigDecimal amount) throws InsufficientBalanceException {
        sendDeposit(depositService::sendGift, companyId, employeeId, amount);
    }

    public void sendMeal(Long companyId, Long employeeId, BigDecimal amount) throws InsufficientBalanceException {
        sendDeposit(depositService::sendMeal, companyId, employeeId, amount);
    }

    private void sendDeposit(
            ThrowingTriConsumer<InsufficientBalanceException, Company, Employee, BigDecimal> consumer, Long companyId,
            Long employeeId,
            BigDecimal amount
    ) throws InsufficientBalanceException {
        final Company company = companyRepository.findById(companyId).orElseThrow();
        final Employee employee = employeeRepository.findById(employeeId).orElseThrow();
        consumer.accept(company, employee, amount);
    }
}
