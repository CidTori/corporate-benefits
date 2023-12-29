package com.example.backend.employee.application;

public interface EmployeeRepository {
    boolean existsById(Long employeeId);
}
