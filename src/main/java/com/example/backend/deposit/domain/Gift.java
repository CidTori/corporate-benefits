package com.example.backend.deposit.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Gift extends Deposit {
    public Gift(BigDecimal amount, LocalDate receptionDate, Long employeeId) {
        super(amount, receptionDate, employeeId);
    }
}
