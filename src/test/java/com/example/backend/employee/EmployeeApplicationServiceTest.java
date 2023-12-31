package com.example.backend.employee;

import com.example.backend.employee.application.EmployeeApplicationService;
import com.example.backend.employee.application.EmployeeNotFoundException;
import com.example.backend.employee.application.EmployeeRepository;
import com.example.backend.employee.domain.Employee;
import com.example.backend.employee.domain.deposit.EmployeeDeposit;
import com.example.backend.employee.domain.deposit.EmployeeGift;
import com.example.backend.employee.domain.deposit.EmployeeMeal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

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

    EmployeeApplicationService employeeApplicationService;

    @BeforeEach
    void setup() {
        employeeApplicationService = new EmployeeApplicationService(
                employeeRepository,
                clockSupplier
        );
    }

    @Test
    void sendGiftDeposit_ok() throws EmployeeNotFoundException {
        LocalDate giftDate = LocalDate.of(2023, JANUARY, 15);
        LocalDate giftEndDate = giftDate.plusYears(1);
        LocalDate mealDate = giftDate.plusMonths(1);
        LocalDate mealEndDate = LocalDate.of(mealDate.plusYears(1).getYear(), MARCH, 1);
        Long johnId = 1L;
        Employee john = new Employee();
        List<EmployeeDeposit> deposits = List.of(
                new EmployeeGift(valueOf(100), giftDate, johnId),
                new EmployeeMeal(valueOf(50), giftDate, johnId)
        );
        john.getDeposits().addAll(deposits);
        when(employeeRepository.findById(johnId)).thenReturn(Optional.of(john));

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