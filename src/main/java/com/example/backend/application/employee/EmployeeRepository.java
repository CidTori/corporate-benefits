package com.example.backend.application.employee;

import java.util.Optional;

public interface EmployeeRepository {
    Optional<EmployeeApplicationAdapter> findById(Long id);
}
