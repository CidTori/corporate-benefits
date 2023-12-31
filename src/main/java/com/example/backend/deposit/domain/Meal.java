package com.example.backend.deposit.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Meal extends Deposit {
    public Meal(BigDecimal amount, LocalDate receptionDate, Long employeeId) {
        super(amount, receptionDate, employeeId);
    }
}
