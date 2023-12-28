package com.example.backend.application.employee;

import com.example.backend.domain.employee.Employee;

import java.util.Optional;

public interface EmployeeRepository {
    Optional<Employee> findById(Long id);
}
