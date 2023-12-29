package com.example.backend.employee.infrastructure;

import com.example.backend.employee.application.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class EmployeeDatabaseRepository implements EmployeeRepository {
    private final EmployeeEntityRepository employeeRepository;

    @Override
    public boolean existsById(Long employeeId) {
        return employeeRepository.existsById(employeeId);
    }
}
