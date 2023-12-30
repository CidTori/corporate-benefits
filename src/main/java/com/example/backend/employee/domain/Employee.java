package com.example.backend.employee.domain;

import com.example.backend.employee.domain.deposit.EmployeeDeposit;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.math.BigDecimal.ZERO;

@Value
public class Employee {
    Long id;
    List<EmployeeDeposit> deposits = new ArrayList<>();

    public BigDecimal getBalance(LocalDate date) {
        return deposits.stream()
                .filter(d -> d.isNotExpired(date))
                .map(EmployeeDeposit::getAmount)
                .reduce(ZERO, BigDecimal::add);
    }
}
