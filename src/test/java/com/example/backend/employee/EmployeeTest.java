package com.example.backend.employee;

import com.example.backend.employee.domain.Employee;
import com.example.backend.employee.domain.deposit.EmployeeDeposit;
import com.example.backend.employee.domain.deposit.EmployeeGift;
import com.example.backend.employee.domain.deposit.EmployeeMeal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static java.math.BigDecimal.valueOf;
import static java.time.Month.JANUARY;
import static java.time.Month.MARCH;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class EmployeeTest {

    @Test
    void sendGiftDeposit_ok() {
        LocalDate giftDate = LocalDate.of(2023, JANUARY, 15);
        LocalDate giftEndDate = giftDate.plusYears(1);
        LocalDate mealDate = giftDate.plusMonths(1);
        LocalDate mealEndDate = LocalDate.of(mealDate.plusYears(1).getYear(), MARCH, 1);
        Long johnId = 1L;
        Employee john = new Employee();
        List<EmployeeDeposit> deposits = List.of(
                new EmployeeGift(valueOf(100), giftDate, johnId),
                new EmployeeMeal(valueOf(50), mealDate, johnId)
        );
        john.getDeposits().addAll(deposits);

        assertEquals(valueOf(150), john.getBalance(giftEndDate.minusDays(1)));
        assertEquals(valueOf(50), john.getBalance(giftEndDate));
        assertEquals(valueOf(50), john.getBalance(mealEndDate.minusDays(1)));
        assertEquals(valueOf(0), john.getBalance(mealEndDate));
    }
}