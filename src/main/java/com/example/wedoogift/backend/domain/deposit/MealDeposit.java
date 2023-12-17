package com.example.wedoogift.backend.domain.deposit;

import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

import static java.time.LocalDate.now;
import static java.time.Month.MARCH;

@EqualsAndHashCode(callSuper = true)
public class MealDeposit extends Deposit {
    MealDeposit(BigDecimal amount) {
        super(amount);
    }

    public static MealDeposit of(BigDecimal amount) {
        return new MealDeposit(amount);
    }

    public boolean isNotExpired() {
        return now().isBefore(LocalDate.of(getReceptionDate().plusYears(1).getYear(), MARCH, 1));
    }
}
