package com.example.backend.presentation.employee.deposit;

import com.example.backend.deposit.domain.DepositType;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@RequiredArgsConstructor(onConstructor_ = {@JsonCreator})
public class DepositResource {
    private final DepositType type;
    private final BigDecimal amount;
    private final LocalDate receptionDate;
    private final Long employeeId;
}
