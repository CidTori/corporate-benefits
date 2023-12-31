package com.example.backend.employee.domain.deposit;

import java.math.BigDecimal;
import java.time.LocalDate;

import static java.time.Month.MARCH;

public class EmployeeMeal extends EmployeeDeposit {
    public EmployeeMeal(BigDecimal amount, LocalDate receptionDate, Long employeeId) {
        super(amount, receptionDate, employeeId);
    }

    @Override
    public boolean isNotExpired(LocalDate date) {
        return date.isBefore(LocalDate.of(getReceptionDate().plusYears(1).getYear(), MARCH, 1));
    }
}
