package com.example.backend.domain.deposit;

import com.example.backend.deposit.application.DepositApplicationService;
import com.example.backend.deposit.application.DepositRepository;
import com.example.backend.deposit.application.company.CompanyNotFoundException;
import com.example.backend.deposit.application.company.CompanyRepository;
import com.example.backend.deposit.application.employee.DepositEmployeeNotFoundException;
import com.example.backend.deposit.application.employee.DepositEmployeeRepository;
import com.example.backend.deposit.domain.Deposit;
import com.example.backend.deposit.domain.DepositService;
import com.example.backend.deposit.domain.company.Company;
import com.example.backend.deposit.domain.company.InsufficientCompanyBalanceException;
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
import static java.time.ZoneId.systemDefault;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DepositApplicationServiceTest {
    @Mock
    Supplier<Clock> clockSupplier;
    @Mock
    CompanyRepository companyRepository;
    @Mock
    DepositEmployeeRepository depositEmployeeRepository;

    @Mock
    DepositRepository depositRepository;


    @InjectMocks
    DepositService depositService;

    DepositApplicationService depositApplicationService;

    @BeforeEach
    void setup() {
        depositApplicationService = new DepositApplicationService(
                depositService,
                companyRepository,
                depositEmployeeRepository,
                depositRepository
        );
    }

    @Test
    void sendGiftDeposit_ok() throws InsufficientCompanyBalanceException, CompanyNotFoundException, DepositEmployeeNotFoundException {
        LocalDate giftDate = LocalDate.of(2023, JANUARY, 15);
        LocalDate mealDate = giftDate.plusMonths(1);
        Long teslaId = 1234567890L;
        Long johnId = 1L;
        Company tesla = new Company(teslaId, valueOf(1000));
        when(companyRepository.findById(teslaId)).thenReturn(Optional.of(tesla));
        when(depositEmployeeRepository.existsById(johnId)).thenReturn(true);

        setDateTo(giftDate);
        Deposit gift = depositApplicationService.sendGift(teslaId, johnId, valueOf(100));
        setDateTo(mealDate);
        Deposit meal = depositApplicationService.sendMeal(teslaId, johnId, valueOf(50));

        assertEquals(valueOf(850), tesla.getBalance());

        verify(depositRepository).save(gift);
        verify(depositRepository).save(meal);
    }

    @Test
    void sendMealDeposit_ko() {
        Long appleId = 1234567890L;
        Long jessicaId = 1L;
        Company apple = new Company(appleId, valueOf(0));
        when(companyRepository.findById(appleId)).thenReturn(Optional.of(apple));
        when(depositEmployeeRepository.existsById(jessicaId)).thenReturn(true);

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