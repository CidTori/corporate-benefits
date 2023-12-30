package com.example.backend.presentation.employee.deposit;

import com.example.backend.deposit.domain.DepositType;

import java.math.BigDecimal;
import java.time.LocalDate;

//@RequiredArgsConstructor(onConstructor_ = {@JsonCreator})
public record DepositResource(
        DepositType type,
        BigDecimal amount,
        LocalDate receptionDate,
        Long employeeId
) {}
