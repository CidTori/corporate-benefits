package com.example.backend.application.deposit;

import com.example.backend.application.company.CompanyNotFoundException;
import com.example.backend.application.company.CompanyRepository;
import com.example.backend.application.employee.EmployeeNotFoundException;
import com.example.backend.application.employee.EmployeeRepository;
import com.example.backend.domain.company.Company;
import com.example.backend.domain.company.InsufficientCompanyBalanceException;
import com.example.backend.domain.deposit.Deposit;
import com.example.backend.domain.deposit.DepositService;
import com.example.backend.domain.deposit.DepositType;
import com.example.backend.domain.employee.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public BigDecimal getBalance(Long employeeId) throws EmployeeNotFoundException {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException("Employee with id " + employeeId + " not found"));

        return employee.getBalance(now(clockSupplier.get()));
    }

    @Transactional
    public Deposit sendDeposit(DepositType type, Long companyId, Long employeeId, BigDecimal amount) throws InsufficientCompanyBalanceException, CompanyNotFoundException, EmployeeNotFoundException {
        final Company company = companyRepository.findById(companyId).orElseThrow(() -> new CompanyNotFoundException("Company with id " + companyId + " not found"));
        final Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException("Employee with id " + employeeId + " not found"));

        Deposit deposit = depositService.sendDeposit(type, company, employee, amount);

        companyRepository.save(company);
        employeeRepository.save(employee);

        return deposit;
    }
}
