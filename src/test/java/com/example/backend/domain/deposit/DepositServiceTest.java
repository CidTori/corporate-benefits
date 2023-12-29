package com.example.backend.domain.deposit;

import com.example.backend.deposit.domain.Deposit;
import com.example.backend.deposit.domain.DepositService;
import com.example.backend.deposit.domain.company.Company;
import com.example.backend.deposit.domain.company.InsufficientCompanyBalanceException;
import com.example.backend.employee.domain.Employee;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.util.function.Supplier;

import static com.example.backend.deposit.domain.DepositType.GIFT;
import static com.example.backend.deposit.domain.DepositType.MEAL;
import static java.math.BigDecimal.valueOf;
import static java.time.Clock.fixed;
import static java.time.Month.JANUARY;
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
        final LocalDate giftDate = LocalDate.of(2023, JANUARY, 15);
        final LocalDate mealDate = giftDate.plusMonths(1);
        final Company tesla = new Company(1234567890L, valueOf(1000));
        final Employee john = new Employee(1L);

        setDateTo(giftDate);
        Deposit gift = depositService.sendGift(tesla, john.getId(), valueOf(100));
        setDateTo(mealDate);
        Deposit meal = depositService.sendMeal(tesla, john.getId(), valueOf(50));

        assertEquals(valueOf(850), tesla.getBalance());

        assertEquals(giftDate, gift.getReceptionDate());
        assertEquals(GIFT, gift.getType());
        assertEquals(mealDate, meal.getReceptionDate());
        assertEquals(MEAL, meal.getType());
    }

    @Test
    void sendMealDeposit_ko() {
        final Company apple = new Company(1234567890L, valueOf(0));
        final Employee jessica = new Employee(1L);

        assertThrows(
                InsufficientCompanyBalanceException.class,
                () -> depositService.sendMeal(apple, jessica.getId(), valueOf(50))
        );
    }

    private void setDateTo(LocalDate receptionDate) {
        final Instant noon = receptionDate.atStartOfDay(systemDefault()).plusHours(12).toInstant();
        when(clockSupplier.get()).thenReturn(fixed(noon, systemDefault()));
    }
}