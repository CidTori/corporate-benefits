package com.example.wedoogift.backend.domain.deposit;

import com.example.wedoogift.backend.domain.company.Company;
import com.example.wedoogift.backend.domain.company.InsufficientBalanceException;
import com.example.wedoogift.backend.domain.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.LocalDate;
import java.time.Month;
import java.util.function.Supplier;

import static java.math.BigDecimal.valueOf;
import static java.time.Clock.fixed;
import static java.time.ZoneId.systemDefault;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DepositServiceTest {

    @Mock
    Supplier<Clock> clockSupplier;

    @InjectMocks
    DepositService depositService;

    @Test
    void sendGiftDeposit_ok() throws InsufficientBalanceException {
        final LocalDate receptionDate = LocalDate.of(2023, Month.DECEMBER, 17);
        final Company tesla = new Company(valueOf(1000));
        User john = new User();
        when(clockSupplier.get()).thenReturn(fixed(receptionDate.atStartOfDay(systemDefault()).toInstant(), systemDefault()));

        depositService.sendGiftDeposit(tesla, john, valueOf(100));

        assertEquals(valueOf(100), john.getBalance(receptionDate));
        assertEquals(valueOf(0), john.getBalance(receptionDate.plusYears(1)));
    }

    @Test
    void sendMealDeposit_ko() {
        final Company apple = new Company(valueOf(0));
        User jessica = new User();

        assertThrows(
                InsufficientBalanceException.class,
                () -> depositService.sendMealDeposit(apple, jessica, valueOf(50))
        );
    }

}