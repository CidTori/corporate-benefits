package com.example.backend.infrastructure.deposit;

import com.example.backend.domain.deposit.Deposit;
import com.example.backend.domain.deposit.DepositType;
import com.example.backend.domain.employee.Employee;
import com.example.backend.infrastructure.employee.EmployeeEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor(staticName = "of")
public class DepositAdapter extends Deposit {
    @Getter private final DepositEntity entity;

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
