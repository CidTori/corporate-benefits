package com.example.backend.presentation.employee.deposit;

import java.math.BigDecimal;
import java.time.LocalDate;

//@RequiredArgsConstructor(onConstructor_ = {@JsonCreator})
public final class MealResource extends DepositResource {
    public MealResource(BigDecimal amount, LocalDate receptionDate, Long employeeId) {
        super(amount, receptionDate, employeeId);
    }
}
