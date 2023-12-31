package com.example.backend;

import com.example.backend.application.company.CompanyNotFoundException;
import com.example.backend.application.company.CompanyRepository;
import com.example.backend.application.deposit.DepositApplicationService;
import com.example.backend.application.employee.EmployeeNotFoundException;
import com.example.backend.application.employee.EmployeeRepository;
import com.example.backend.domain.company.Company;
import com.example.backend.domain.company.InsufficientCompanyBalanceException;
import com.example.backend.domain.deposit.Deposit;
import com.example.backend.domain.deposit.DepositService;
import com.example.backend.domain.deposit.DepositType;
import com.example.backend.domain.employee.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;
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
class DepositApplicationServiceTest {
    @Mock
    Supplier<Clock> clockSupplier;
    @Mock
    CompanyRepository companyRepository;
    @Mock
    EmployeeRepository employeeRepository;

    @InjectMocks
    DepositService depositService;

    DepositApplicationService depositApplicationService;

    @BeforeEach
    void setup() {
        depositApplicationService = new DepositApplicationService(
                depositService,
                companyRepository,
                employeeRepository,
                clockSupplier
        );
    }

    @Test
    void sendGiftDeposit_ok() throws InsufficientCompanyBalanceException, CompanyNotFoundException, EmployeeNotFoundException {
        LocalDate giftDate = LocalDate.of(2023, JANUARY, 15);
        LocalDate giftEndDate = giftDate.plusYears(1);
        LocalDate mealDate = giftDate.plusMonths(1);
        LocalDate mealEndDate = LocalDate.of(mealDate.plusYears(1).getYear(), MARCH, 1);
        Long teslaId = 1234567890L;
        Long johnId = 1L;
        Company tesla = new Company(teslaId, valueOf(1000));
        Employee john = new Employee(johnId);
        when(companyRepository.findById(teslaId)).thenReturn(Optional.of(tesla));
        when(employeeRepository.findById(johnId)).thenReturn(Optional.of(john));

        setDateTo(giftDate);
        Deposit gift = depositApplicationService.sendGift(teslaId, johnId, valueOf(100));
        setDateTo(mealDate);
        Deposit meal = depositApplicationService.sendMeal(teslaId, johnId, valueOf(50));

        assertEquals(valueOf(850), tesla.getBalance());

        assertEquals(giftDate, gift.getReceptionDate());
        Assertions.assertEquals(DepositType.GIFT, gift.getType());
        assertEquals(mealDate, meal.getReceptionDate());
        Assertions.assertEquals(DepositType.MEAL, meal.getType());

        setDateTo(giftEndDate.minusDays(1));
        assertEquals(valueOf(150), depositApplicationService.getBalance(johnId));
        setDateTo(giftEndDate);
        assertEquals(valueOf(50), depositApplicationService.getBalance(johnId));
        setDateTo(mealEndDate.minusDays(1));
        assertEquals(valueOf(50), depositApplicationService.getBalance(johnId));
        setDateTo(mealEndDate);
        assertEquals(valueOf(0), depositApplicationService.getBalance(johnId));
    }

    @Test
    void sendMealDeposit_ko() {
        Long appleId = 1234567890L;
        Long jessicaId = 1L;
        Company apple = new Company(appleId, valueOf(0));
        Employee jessica = new Employee(jessicaId);
        when(companyRepository.findById(appleId)).thenReturn(Optional.of(apple));
        when(employeeRepository.findById(jessicaId)).thenReturn(Optional.of(jessica));

        assertThrows(
                InsufficientCompanyBalanceException.class,
                () -> depositApplicationService.sendMeal(appleId, jessicaId, valueOf(50))
        );
    }

    private void setDateTo(LocalDate receptionDate) {
        final Instant noon = receptionDate.atStartOfDay(systemDefault()).plusHours(12).toInstant();
        when(clockSupplier.get()).thenReturn(fixed(noon, systemDefault()));
    }
}