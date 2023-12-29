package com.example.backend.domain.employee;

import com.example.backend.deposit.domain.Deposit;
import com.example.backend.employee.domain.BalanceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static com.example.backend.deposit.domain.DepositType.GIFT;
import static com.example.backend.deposit.domain.DepositType.MEAL;
import static java.math.BigDecimal.valueOf;
import static java.time.Month.JANUARY;
import static java.time.Month.MARCH;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class BalanceServiceTest {

    @InjectMocks
    BalanceService balanceService;

    @Test
    void sendGiftDeposit_ok() {
        final LocalDate giftDate = LocalDate.of(2023, JANUARY, 15);
        final LocalDate giftEndDate = giftDate.plusYears(1);
        final LocalDate mealDate = giftDate.plusMonths(1);
        final LocalDate mealEndDate = LocalDate.of(mealDate.plusYears(1).getYear(), MARCH, 1);
        final Long johnId = 1L;

        List<Deposit> deposits = List.of(
                new Deposit(GIFT, valueOf(100), giftDate, johnId),
                new Deposit(MEAL, valueOf(50), mealDate, johnId)
        );

        assertEquals(valueOf(150), balanceService.getBalance(deposits, giftEndDate.minusDays(1)));
        assertEquals(valueOf(50), balanceService.getBalance(deposits, giftEndDate));
        assertEquals(valueOf(50), balanceService.getBalance(deposits, mealEndDate.minusDays(1)));
        assertEquals(valueOf(0), balanceService.getBalance(deposits, mealEndDate));
    }
}