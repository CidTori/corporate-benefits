package com.example.backend.presentation.deposit;

import com.example.backend.domain.deposit.Deposit;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface DepositResourceMapper {
    DepositResource toResource(Deposit deposit);
}
