package com.example.backend.employee.infrastructure;

import com.example.backend.employee.domain.Employee;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface EmployeeEntityMapper {
    Employee toDomain(EmployeeEntity employeeEntity);
}
