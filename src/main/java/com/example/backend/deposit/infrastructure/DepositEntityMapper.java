package com.example.backend.deposit.infrastructure;

import com.example.backend.deposit.domain.Deposit;
import com.example.backend.deposit.domain.DepositType;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface DepositEntityMapper {
    DepositEntity toEntity(Deposit deposit);
    default DepositTypeEntity toEntity(DepositType depositType) {
        return new DepositTypeEntity(depositType.name());
    }

    Deposit toDomain(DepositEntity depositEntity);
    default DepositType toDomain(DepositTypeEntity depositTypeEntity) {
        return DepositType.valueOf(depositTypeEntity.getName());
    }
}
