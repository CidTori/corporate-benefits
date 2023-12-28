package com.example.backend.application.employee;

import com.example.backend.application.company.CompanyApplicationAdapter;
import com.example.backend.application.company.CompanyNotFoundException;
import com.example.backend.application.company.CompanyRepository;
import com.example.backend.domain.DepositService;
import com.example.backend.domain.company.Company;
import com.example.backend.domain.company.InsufficientCompanyBalanceException;
import com.example.backend.domain.employee.Employee;
import com.example.backend.utils.ThrowingTriConsumer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Clock;
import java.util.function.Supplier;

import static java.time.LocalDate.now;

@Service
@RequiredArgsConstructor
public class EmployeeApplicationService {
    private final DepositService depositService;
    private final CompanyRepository companyRepository;
    private final EmployeeRepository employeeRepository;
    private final Supplier<Clock> clockSupplier;

    public BigDecimal getBalance(Long employeeId) throws EmployeeNotFoundException {
        return employeeRepository.findById(employeeId)
                .map(e -> e.getBalance(now(clockSupplier.get())))
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with id " + employeeId + " not found"));
    }

    @Transactional
    public void sendGift(Long companyId, Long employeeId, BigDecimal amount) throws InsufficientCompanyBalanceException, CompanyNotFoundException, EmployeeNotFoundException {
        sendDeposit(depositService::sendGift, companyId, employeeId, amount);
    }

    @Transactional
    public void sendMeal(Long companyId, Long employeeId, BigDecimal amount) throws InsufficientCompanyBalanceException, CompanyNotFoundException, EmployeeNotFoundException {
        sendDeposit(depositService::sendMeal, companyId, employeeId, amount);
    }

    private void sendDeposit(
            ThrowingTriConsumer<InsufficientCompanyBalanceException, Company, Employee, BigDecimal> consumer, Long companyId,
            Long employeeId,
            BigDecimal amount
    ) throws
            CompanyNotFoundException,
            EmployeeNotFoundException,
            InsufficientCompanyBalanceException
    {

        final CompanyApplicationAdapter company = companyRepository.findById(companyId).orElseThrow(() -> new CompanyNotFoundException("Company with id " + companyId + " not found"));
        final EmployeeApplicationAdapter employee = employeeRepository.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException("Employee with id " + employeeId + " not found"));

        consumer.accept(company, employee, amount);

        //company.save();
        //employee.save();
    }
}
