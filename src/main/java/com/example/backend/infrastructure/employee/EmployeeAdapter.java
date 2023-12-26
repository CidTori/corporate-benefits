package com.example.backend.infrastructure.employee;

import com.example.backend.domain.deposit.Deposit;
import com.example.backend.domain.employee.Employee;
import com.example.backend.infrastructure.deposit.DepositAdapter;
import com.example.backend.infrastructure.deposit.DepositEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor(staticName = "of")
public class EmployeeAdapter extends Employee {
    @Getter private final EmployeeEntity entity;

    @Override
    public void addDeposit(Deposit deposit) {
        DepositEntity depositEntity = ((DepositAdapter) deposit).getEntity();
        depositEntity.setEmployee(entity);
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
