package com.example.backend.employee.infrastructure;

import com.example.backend.employee.domain.Employee;
import com.example.backend.employee.domain.deposit.EmployeeDepositType;
import com.example.backend.employee.infrastructure.deposit.EmployeeDepositTypeEntity;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface EmployeeEntityMapper {
    Employee toDomain(EmployeeEntity employeeEntity);
    default EmployeeDepositType toDomain(EmployeeDepositTypeEntity depositTypeEntity) {
        return EmployeeDepositType.valueOf(depositTypeEntity.getName());
    }
}
