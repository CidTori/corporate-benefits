package com.example.backend.domain.deposit;

import com.example.backend.domain.company.Company;
import com.example.backend.domain.company.InsufficientCompanyBalanceException;
import com.example.backend.domain.employee.Employee;
import lombok.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
        final LocalDate giftDate = LocalDate.of(2023, JANUARY, 15);
        final LocalDate giftEndDate = giftDate.plusYears(1);
        final LocalDate mealDate = giftDate.plusMonths(1);
        final LocalDate mealEndDate = LocalDate.of(mealDate.plusYears(1).getYear(), MARCH, 1);
        final Company tesla = new Company(1234567890L, valueOf(1000));
        final Employee john = new EmployeeImpl();

        setDateTo(giftDate);
        depositService.sendGift(tesla, john, valueOf(100));
        setDateTo(mealDate);
        depositService.sendMeal(tesla, john, valueOf(50));

        assertEquals(valueOf(850), tesla.getBalance());

        assertEquals(valueOf(150), john.getBalance(giftEndDate.minusDays(1)));
        assertEquals(valueOf(50), john.getBalance(giftEndDate));
        assertEquals(valueOf(50), john.getBalance(mealEndDate.minusDays(1)));
        assertEquals(valueOf(0), john.getBalance(mealEndDate));
    }

    @Test
    void sendMealDeposit_ko() {
        final Company apple = new Company(1234567890L, valueOf(0));
        final Employee jessica = new EmployeeImpl();

        assertThrows(
                InsufficientCompanyBalanceException.class,
                () -> depositService.sendMeal(apple, jessica, valueOf(50))
        );
    }

    private void setDateTo(LocalDate receptionDate) {
        final Instant noon = receptionDate.atStartOfDay(systemDefault()).plusHours(12).toInstant();
        when(clockSupplier.get()).thenReturn(fixed(noon, systemDefault()));
    }

    @Getter @Setter
    static class EmployeeImpl extends Employee {
        List<Deposit> deposits = new ArrayList<>();

        @Override
        public void addDeposit(Deposit deposit) {
            deposits.add(deposit);
        }
    }

}