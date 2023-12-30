package com.example.backend.deposit.infrastructure;

import com.example.backend.deposit.domain.Deposit;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface DepositEntityMapper {
    DepositEntity toEntity(Deposit deposit);
}
