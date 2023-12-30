package com.example.backend.employee.application;

import com.example.backend.employee.domain.Employee;
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
    private final Supplier<Clock> clockSupplier;

    public BigDecimal getBalance(Long employeeId) throws EmployeeNotFoundException {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException("Employee with id " + employeeId + " not found"));

        return employee.getBalance(now(clockSupplier.get()));
    }
}