package com.example.wedoogift.backend.domain;

import com.example.wedoogift.backend.domain.company.Company;
import com.example.wedoogift.backend.domain.company.InsufficientBalanceException;
import com.example.wedoogift.backend.domain.deposit.GiftDeposit;
import com.example.wedoogift.backend.domain.deposit.MealDeposit;
import com.example.wedoogift.backend.domain.user.User;
import org.junit.jupiter.api.Test;

import static java.math.BigDecimal.*;
import static java.time.LocalDate.now;
import static org.junit.jupiter.api.Assertions.*;

class WedoogiftTest {

    @Test
    void sendGiftDeposit_ok() throws InsufficientBalanceException {
        final Company tesla = new Company(valueOf(1000));
        User john = new User();

        tesla.sendDeposit(john, GiftDeposit.of(valueOf(100)));

        assertEquals(john.getBalance(), valueOf(100));
    }

    @Test
    void sendMealDeposit_ko() {
        final Company apple = new Company(valueOf(0));
        User jessica = new User();

        assertThrows(
                InsufficientBalanceException.class,
                () -> apple.sendDeposit(jessica, MealDeposit.of(valueOf(100)))
        );
    }

}