package com.example.wedoogift.backend.domain.deposit;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;

import static java.time.LocalDate.now;

@ToString
@EqualsAndHashCode(callSuper = true)
public class GiftDeposit extends Deposit {
    GiftDeposit(BigDecimal amount) {
        super(amount);
    }

    public static GiftDeposit of(BigDecimal amount) {
        return new GiftDeposit(amount);
    }

    public boolean isNotExpired() {
        return now().isBefore(getReceptionDate().plusYears(1));
    }
}
