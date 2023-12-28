package com.example.backend.infrastructure.deposit;

import com.example.backend.domain.employee.deposit.Deposit;
import com.example.backend.domain.employee.deposit.DepositType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@RequiredArgsConstructor(staticName = "of")
public class DepositAdapter extends Deposit {
    private final DepositEntity entity;

    @Override
    protected DepositType getType() {
        return entity.getType();
    }

    @Override
    public BigDecimal getAmount() {
        return entity.getAmount();
    }

    @Override
    protected LocalDate getReceptionDate() {
        return entity.getReceptionDate();
    }
}
