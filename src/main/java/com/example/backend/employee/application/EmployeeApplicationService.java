package com.example.backend.employee.application;

import com.example.backend.deposit.application.DepositRepository;
import com.example.backend.employee.domain.BalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Clock;
import java.util.function.Supplier;

import static java.time.LocalDate.now;

@Service
@RequiredArgsConstructor
public class EmployeeApplicationService {
    private final EmployeeRepository employeeRepository;
    private final DepositRepository depositRepository;
    private final BalanceService balanceService;
    private final Supplier<Clock> clockSupplier;

    public BigDecimal getBalance(Long employeeId) throws EmployeeNotFoundException {
        if (!employeeRepository.existsById(employeeId)) throw new EmployeeNotFoundException("Employee with id " + employeeId + " not found");

        return balanceService.getBalance(depositRepository.findByEmployeeId(employeeId), now(clockSupplier.get()));
    }
}