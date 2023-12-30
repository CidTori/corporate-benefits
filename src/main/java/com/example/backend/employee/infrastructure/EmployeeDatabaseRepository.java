package com.example.backend.employee.infrastructure;

import com.example.backend.employee.application.EmployeeRepository;
import com.example.backend.employee.domain.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class EmployeeDatabaseRepository implements EmployeeRepository {
    private final EmployeeEntityRepository employeeRepository;
    private final EmployeeEntityMapper employeeMapper;

    @Override
    public Optional<Employee> findById(Long employeeId) {
        return employeeRepository.findById(employeeId)
                .map(employeeMapper::toDomain);
    }
}
