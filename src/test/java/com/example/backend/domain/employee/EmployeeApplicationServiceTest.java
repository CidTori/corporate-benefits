package com.example.backend.domain.employee;

import com.example.backend.deposit.application.DepositRepository;
import com.example.backend.deposit.domain.Deposit;
import com.example.backend.employee.application.EmployeeApplicationService;
import com.example.backend.employee.application.EmployeeNotFoundException;
import com.example.backend.employee.application.EmployeeRepository;
import com.example.backend.employee.domain.BalanceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Supplier;

import static com.example.backend.deposit.domain.DepositType.GIFT;
import static com.example.backend.deposit.domain.DepositType.MEAL;
import static java.math.BigDecimal.valueOf;
import static java.time.Clock.fixed;
import static java.time.Month.JANUARY;
import static java.time.Month.MARCH;
import static java.time.ZoneId.systemDefault;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeApplicationServiceTest {
    @Mock
    Supplier<Clock> clockSupplier;
    @Mock
    EmployeeRepository employeeRepository;

    @Mock
    DepositRepository depositRepository;

    @InjectMocks
    BalanceService balanceService;

    EmployeeApplicationService employeeApplicationService;

    @BeforeEach
    void setup() {
        employeeApplicationService = new EmployeeApplicationService(
                employeeRepository,
                depositRepository,
                balanceService,
                clockSupplier
        );
    }

    @Test
    void sendGiftDeposit_ok() throws EmployeeNotFoundException {
        final LocalDate giftDate = LocalDate.of(2023, JANUARY, 15);
        final LocalDate giftEndDate = giftDate.plusYears(1);
        final LocalDate mealDate = giftDate.plusMonths(1);
        final LocalDate mealEndDate = LocalDate.of(mealDate.plusYears(1).getYear(), MARCH, 1);
        final Long johnId = 1L;
        List<Deposit> deposits = List.of(
                new Deposit(GIFT, valueOf(100), giftDate, johnId),
                new Deposit(MEAL, valueOf(50), giftDate, johnId)
        );
        when(employeeRepository.existsById(johnId)).thenReturn(true);
        when(depositRepository.findByEmployeeId(johnId)).thenReturn(deposits);

        setDateTo(giftEndDate.minusDays(1));
        assertEquals(valueOf(150), employeeApplicationService.getBalance(johnId));
        setDateTo(giftEndDate);
        assertEquals(valueOf(50), employeeApplicationService.getBalance(johnId));
        setDateTo(mealEndDate.minusDays(1));
        assertEquals(valueOf(50), employeeApplicationService.getBalance(johnId));
        setDateTo(mealEndDate);
        assertEquals(valueOf(0), employeeApplicationService.getBalance(johnId));
    }

    private void setDateTo(LocalDate receptionDate) {
        final Instant noon = receptionDate.atStartOfDay(systemDefault()).plusHours(12).toInstant();
        when(clockSupplier.get()).thenReturn(fixed(noon, systemDefault()));
    }
}