package com.example.backend.deposit.domain;

import lombok.Value;
import lombok.experimental.NonFinal;

import java.math.BigDecimal;
import java.time.LocalDate;

@Value
@NonFinal
public abstract class Deposit {
    BigDecimal amount;
    LocalDate receptionDate;
    Long employeeId;
}
