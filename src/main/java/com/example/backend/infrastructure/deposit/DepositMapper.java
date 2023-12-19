package com.example.backend.infrastructure.deposit;

import com.example.backend.domain.deposit.Deposit;
import com.example.backend.infrastructure.user.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DepositMapper {
    DepositEntity toEntity(Deposit deposit, UserEntity user);
    Deposit toDomain(DepositEntity deposit);
}
