package com.example.backend.application.employee;

public class EmployeeNotFoundException extends Exception {
    public EmployeeNotFoundException(Long employeeId) {
        super("Employee with id " + employeeId + " not found");
    }
}
