package com.example.backend.infrastructure.employee;

import com.example.backend.domain.deposit.Deposit;
import com.example.backend.domain.employee.Employee;
import com.example.backend.infrastructure.deposit.DepositMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor(staticName = "of")
public class EmployeeAdapter extends Employee {
    @Getter private final EmployeeEntity entity;
    private final DepositMapper depositMapper;

    @Override
    public void addDeposit(Deposit deposit) {
        entity.getDeposits().add(depositMapper.toEntity(deposit, entity));
    }

    @Override
    protected List<Deposit> getDeposits() {
        return entity.getDeposits().stream()
                .map(depositMapper::toDomain)
                .toList();
    }
}
