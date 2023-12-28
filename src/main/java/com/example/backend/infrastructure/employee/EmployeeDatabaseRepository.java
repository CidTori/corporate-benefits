package com.example.backend.infrastructure.employee;

import com.example.backend.application.employee.EmployeeRepository;
import com.example.backend.domain.employee.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class EmployeeDatabaseRepository implements EmployeeRepository {
    private final EmployeeEntityRepository employeeRepository;

    @Override
    public Optional<Employee> findById(Long id) {
        return employeeRepository.findById(id)
                .map(EmployeeAdapter::of);
    }
}
