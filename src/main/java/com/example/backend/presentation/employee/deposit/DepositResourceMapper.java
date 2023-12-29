package com.example.backend.presentation.employee.deposit;

import com.example.backend.deposit.domain.Deposit;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface DepositResourceMapper {
    DepositResource toResource(Deposit deposit);
}
