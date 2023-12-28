package com.example.backend.infrastructure.employee;

import com.example.backend.domain.employee.Employee;
import com.example.backend.domain.employee.deposit.Deposit;
import com.example.backend.domain.employee.deposit.DepositType;
import com.example.backend.infrastructure.deposit.DepositAdapter;
import com.example.backend.infrastructure.deposit.DepositEntity;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor(staticName = "of")
public class EmployeeAdapter extends Employee {
    private final EmployeeEntity entity;

    @Override
    public void addDeposit(DepositType type, BigDecimal amount, LocalDate receptionDate) {
        DepositEntity depositEntity = new DepositEntity(null, entity, type, amount, receptionDate);
        entity.getDeposits().add(depositEntity);
    }

    @Override
    protected List<Deposit> getDeposits() {
        return entity.getDeposits().stream()
                .map(DepositAdapter::of)
                .map(Deposit.class::cast)
                .toList();
    }
}
