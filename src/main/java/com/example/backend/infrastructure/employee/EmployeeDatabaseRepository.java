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
    private final EmployeeEntityMapper employeeEntityMapper;

    @Override
    public Optional<Employee> findById(Long employeeId) {
        return employeeRepository.findById(employeeId)
                .map(employeeEntityMapper::toDomain);
    }

    @Override
    public void save(Employee employee) {
        employeeRepository.save(employeeEntityMapper.toEntity(employee));
    }
}
