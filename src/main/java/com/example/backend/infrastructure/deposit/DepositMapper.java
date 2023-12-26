package com.example.backend.infrastructure.deposit;

import com.example.backend.domain.deposit.Deposit;
import com.example.backend.infrastructure.employee.EmployeeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface DepositMapper {
    @Mapping(target = "id", ignore = true)
    DepositEntity toEntity(Deposit deposit, EmployeeEntity employee);
    Deposit toDomain(DepositEntity d);
}
