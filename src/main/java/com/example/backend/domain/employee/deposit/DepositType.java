package com.example.backend.domain.employee.deposit;

import java.time.LocalDate;

import static java.time.Month.MARCH;

public enum DepositType {
    GIFT {
        @Override
        public boolean isNotExpired(LocalDate date, LocalDate receptionDate) {
            return date.isBefore(receptionDate.plusYears(1));
        }
    },
    MEAL {
        @Override
        public boolean isNotExpired(LocalDate date, LocalDate receptionDate) {
            return date.isBefore(LocalDate.of(receptionDate.plusYears(1).getYear(), MARCH, 1));
        }
    };

    public abstract boolean isNotExpired(LocalDate date, LocalDate receptionDate);
}
