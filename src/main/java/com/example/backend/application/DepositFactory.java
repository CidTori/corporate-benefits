package com.example.backend.application;

import com.example.backend.domain.deposit.Deposit;
import com.example.backend.domain.deposit.DepositType;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface DepositFactory {
    Deposit create(DepositType type, BigDecimal amount, LocalDate receptionDate);
}
