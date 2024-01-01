package com.example.backend.infrastructure.employee;

import com.example.backend.domain.deposit.DepositType;
import com.example.backend.domain.employee.Employee;
import com.example.backend.infrastructure.deposit.DepositTypeEntity;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface EmployeeEntityMapper {
    Employee toDomain(EmployeeEntity employee);
    EmployeeEntity toEntity(Employee employee);

    default DepositTypeEntity toEntity(DepositType depositType) {
        return new DepositTypeEntity(depositType.name());
    }
    default DepositType toDomain(DepositTypeEntity depositTypeEntity) {
        return DepositType.valueOf(depositTypeEntity.getName());
    }
}
