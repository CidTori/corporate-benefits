package com.example.backend.employee.domain.deposit;

import java.math.BigDecimal;
import java.time.LocalDate;

public class EmployeeGift extends EmployeeDeposit {
    public EmployeeGift(BigDecimal amount, LocalDate receptionDate, Long employeeId) {
        super(amount, receptionDate, employeeId);
    }

    @Override
    public boolean isNotExpired(LocalDate date) {
        return date.isBefore(getReceptionDate().plusYears(1));
    }
}
