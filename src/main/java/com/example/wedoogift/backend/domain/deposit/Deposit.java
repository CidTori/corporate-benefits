package com.example.wedoogift.backend.domain.deposit;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;

import static java.time.LocalDate.now;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Getter
@FieldDefaults(makeFinal = true, level = PRIVATE)
@RequiredArgsConstructor(access = PROTECTED)
@ToString
@EqualsAndHashCode
public abstract class Deposit {
    BigDecimal amount;
    LocalDate receptionDate = now();

    public abstract boolean isNotExpired();
}
