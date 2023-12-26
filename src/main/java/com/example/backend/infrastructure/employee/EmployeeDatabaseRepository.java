package com.example.backend.infrastructure.employee;

import com.example.backend.application.employee.EmployeeRepository;
import com.example.backend.domain.employee.Employee;
import com.example.backend.infrastructure.deposit.DepositMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class EmployeeDatabaseRepository implements EmployeeRepository {
    private final EmployeeEntityRepository employeeRepository;
    private final DepositMapper depositMapper;

    @Override
    public Optional<Employee> findById(Long id) {
        return employeeRepository.findById(id)
                .map(e -> EmployeeAdapter.of(e, depositMapper));
    }

    @Override
    public void save(Employee employee) {
        employeeRepository.save(((EmployeeAdapter) employee).getEntity());
    }
}
