package com.example.backend.infrastructure.employee;

import com.example.backend.domain.employee.Employee;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface EmployeeEntityMapper {
    Employee toDomain(EmployeeEntity employee);

    EmployeeEntity toEntity(Employee employee);
}
