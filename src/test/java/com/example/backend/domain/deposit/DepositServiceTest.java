package com.example.backend.domain.deposit;

import com.example.backend.application.DepositFactory;
import com.example.backend.domain.company.Company;
import com.example.backend.domain.company.InsufficientCompanyBalanceException;
import com.example.backend.domain.employee.Employee;
import lombok.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
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
import static org.mockito.Answers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DepositServiceTest {

    @Mock
    Supplier<Clock> clockSupplier;

    @Mock(answer = CALLS_REAL_METHODS)
    DepositFactoryImpl depositFactory;

    @InjectMocks
    DepositService depositService;

    @Test
    void sendGiftDeposit_ok() throws InsufficientCompanyBalanceException {
        final LocalDate giftDate = LocalDate.of(2023, JANUARY, 15);
        final LocalDate giftEndDate = giftDate.plusYears(1);
        final LocalDate mealDate = giftDate.plusMonths(1);
        final LocalDate mealEndDate = LocalDate.of(mealDate.plusYears(1).getYear(), MARCH, 1);
        final Company tesla = new CompanyImpl(valueOf(1000));
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
        final Company apple = new CompanyImpl(valueOf(0));
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

    @AllArgsConstructor
    @Getter @Setter
    static class DepositImpl extends Deposit {
        private DepositType type;
        private BigDecimal amount;
        private LocalDate receptionDate;
    }

    @Getter @Setter
    static class EmployeeImpl extends Employee {
        List<Deposit> deposits = new ArrayList<>();

        @Override
        public void addDeposit(Deposit deposit) {
            deposits.add(deposit);
        }
    }

    @AllArgsConstructor
    @Getter @Setter
    static class CompanyImpl extends Company {
        private BigDecimal balance;
    }

    private class DepositFactoryImpl implements DepositFactory {
        @Override
        public Deposit create(DepositType type, BigDecimal amount, LocalDate receptionDate) {
            return new DepositImpl(type, amount, receptionDate);
        }
    }
}