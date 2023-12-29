package com.example.backend.employee.domain;

import com.example.backend.deposit.domain.Deposit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BalanceService {
    public BigDecimal getBalance(List<Deposit> deposits, LocalDate date) {
        return deposits.stream()
                .filter(d -> d.isNotExpired(date))
                .map(Deposit::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}