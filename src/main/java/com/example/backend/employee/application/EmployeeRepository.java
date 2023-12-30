package com.example.backend.employee.application;

import com.example.backend.employee.domain.Employee;

import java.util.Optional;

public interface EmployeeRepository {
    Optional<Employee> findById(Long employeeId);
}
