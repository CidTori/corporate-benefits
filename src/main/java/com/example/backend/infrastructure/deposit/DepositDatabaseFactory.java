package com.example.backend.infrastructure.deposit;

import com.example.backend.application.DepositFactory;
import com.example.backend.domain.deposit.Deposit;
import com.example.backend.domain.deposit.DepositType;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class DepositDatabaseFactory implements DepositFactory {
    @Override
    public Deposit create(DepositType type, BigDecimal amount, LocalDate receptionDate) {
        return DepositAdapter.of(new DepositEntity(null, null, type, amount, receptionDate));
    }
}
