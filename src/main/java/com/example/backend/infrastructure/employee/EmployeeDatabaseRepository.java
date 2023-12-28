package com.example.backend.infrastructure.employee;

import com.example.backend.application.employee.EmployeeApplicationAdapter;
import com.example.backend.application.employee.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.Clock;
import java.util.Optional;
import java.util.function.Supplier;

@Repository
@RequiredArgsConstructor
public class EmployeeDatabaseRepository implements EmployeeRepository {
    private final EmployeeEntityRepository employeeRepository;
    private final Supplier<Clock> clockSupplier;

    @Override
    public Optional<EmployeeApplicationAdapter> findById(Long id) {
        return employeeRepository.findById(id)
                .map(e -> EmployeeAdapter.of(e, employeeRepository));
    }
}
