package com.example.backend.presentation.employee.deposit;

import lombok.Value;
import lombok.experimental.NonFinal;

import java.math.BigDecimal;
import java.time.LocalDate;

@Value @NonFinal
//@RequiredArgsConstructor(onConstructor_ = {@JsonCreator})
public abstract class DepositResource {
    BigDecimal amount;
    LocalDate receptionDate;
    Long employeeId;
}
