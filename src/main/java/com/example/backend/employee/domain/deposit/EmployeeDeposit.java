package com.example.backend.employee.domain.deposit;

import lombok.Value;
import lombok.experimental.NonFinal;

import java.math.BigDecimal;
import java.time.LocalDate;

@Value @NonFinal
public abstract class EmployeeDeposit {
    BigDecimal amount;
    LocalDate receptionDate;
    Long employeeId;

    public abstract boolean isNotExpired(LocalDate date);
}
