package com.example.backend;

import com.example.backend.domain.company.Company;
import com.example.backend.domain.company.InsufficientCompanyBalanceException;
import com.example.backend.domain.deposit.Deposit;
import com.example.backend.domain.deposit.DepositService;
import com.example.backend.domain.deposit.DepositType;
import com.example.backend.domain.employee.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.util.function.Supplier;

import static java.math.BigDecimal.valueOf;
import static java.time.Clock.fixed;
import static java.time.Month.JANUARY;
import static java.time.Month.MARCH;
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
    void sendGiftDeposit_ok() throws InsufficientCompanyBalanceException {
        LocalDate giftDate = LocalDate.of(2023, JANUARY, 15);
        LocalDate giftEndDate = giftDate.plusYears(1);
        LocalDate mealDate = giftDate.plusMonths(1);
        LocalDate mealEndDate = LocalDate.of(mealDate.plusYears(1).getYear(), MARCH, 1);
        Company tesla = new Company(1234567890L, valueOf(1000));
        Employee john = new Employee(1L);

        setDateTo(giftDate);
        Deposit gift = depositService.sendGift(tesla, john, valueOf(100));
        setDateTo(mealDate);
        Deposit meal = depositService.sendMeal(tesla, john, valueOf(50));

        assertEquals(valueOf(850), tesla.getBalance());

        assertEquals(giftDate, gift.getReceptionDate());
        Assertions.assertEquals(DepositType.GIFT, gift.getType());
        assertEquals(mealDate, meal.getReceptionDate());
        Assertions.assertEquals(DepositType.MEAL, meal.getType());

        assertEquals(valueOf(150), john.getBalance(giftEndDate.minusDays(1)));
        assertEquals(valueOf(50), john.getBalance(giftEndDate));
        assertEquals(valueOf(50), john.getBalance(mealEndDate.minusDays(1)));
        assertEquals(valueOf(0), john.getBalance(mealEndDate));
    }

    @Test
    void sendMealDeposit_ko() {
        final Company apple = new Company(1234567890L, valueOf(0));
        final Employee jessica = new Employee(1L);

        assertThrows(
                InsufficientCompanyBalanceException.class,
                () -> depositService.sendMeal(apple, jessica, valueOf(50))
        );
    }

    private void setDateTo(LocalDate receptionDate) {
        final Instant noon = receptionDate.atStartOfDay(systemDefault()).plusHours(12).toInstant();
        when(clockSupplier.get()).thenReturn(fixed(noon, systemDefault()));
    }
}