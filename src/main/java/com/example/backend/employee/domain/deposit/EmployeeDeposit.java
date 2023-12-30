package com.example.backend.employee.domain.deposit;

import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;

@Value
public class EmployeeDeposit {
    EmployeeDepositType type;
    BigDecimal amount;
    LocalDate receptionDate;
    Long employeeId;

    public boolean isNotExpired(LocalDate date) {
        return type.isNotExpired(date, receptionDate);
    }
}
