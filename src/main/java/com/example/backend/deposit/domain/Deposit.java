package com.example.backend.deposit.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

public record Deposit(DepositType type, BigDecimal amount, LocalDate receptionDate, Long employeeId) {}
