package com.example.backend.deposit.infrastructure.employee;

import com.example.backend.deposit.application.employee.DepositEmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DepositEmployeeDatabaseRepository implements DepositEmployeeRepository {
    private final DepositEmployeeEntityRepository employeeRepository;

    @Override
    public boolean existsById(Long employeeId) {
        return employeeRepository.existsById(employeeId);
    }
}
