package com.example.backend.infrastructure.employee;

import com.example.backend.domain.deposit.Deposit;
import com.example.backend.domain.employee.Employee;
import com.example.backend.infrastructure.deposit.DepositEntityRepository;
import com.example.backend.infrastructure.deposit.DepositMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor(staticName = "of")
public class EmployeeAdapter extends Employee {
    private final EmployeeEntity employee;
    private final DepositMapper depositMapper;
    private final DepositEntityRepository depositRepository;

    @Override
    public void addDeposit(Deposit deposit) {
        depositRepository.save(depositMapper.toEntity(deposit, employee));
    }

    @Override
    protected List<Deposit> getDeposits() {
        return employee.getDeposits().stream()
                .map(depositMapper::toDomain)
                .toList();
    }
}
