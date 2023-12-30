package com.example.backend.employee.domain.deposit;

import java.math.BigDecimal;
import java.time.LocalDate;

public record EmployeeDeposit(
        EmployeeDepositType type,
        BigDecimal amount,
        LocalDate receptionDate,
        Long employeeId
) {
    public boolean isNotExpired(LocalDate date) {
        return type.isNotExpired(date, receptionDate);
    }
}
