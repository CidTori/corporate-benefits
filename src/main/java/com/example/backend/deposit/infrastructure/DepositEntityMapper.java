package com.example.backend.deposit.infrastructure;

import com.example.backend.deposit.domain.Deposit;
import com.example.backend.deposit.domain.Gift;
import com.example.backend.deposit.domain.Meal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface DepositEntityMapper {
    @Mapping(target = "type", expression = "java(new DepositTypeEntity(\"GIFT\"))")
    DepositEntity toEntity(Gift gift);
    @Mapping(target = "type", expression = "java(new DepositTypeEntity(\"MEAL\"))")
    DepositEntity toEntity(Meal meal);

    default Deposit toDomain(DepositEntity depositEntity) {
        return switch (depositEntity.getType().getName()) {
            case "GIFT" -> toGift(depositEntity);
            case "MEAL" -> toMeal(depositEntity);
            default -> throw new IllegalArgumentException("Deposit type not supported");
        };
    }
    //@Mapping(source = "type", expression = "java(checkCondition(source))")
    Gift toGift(DepositEntity depositEntity);
    Meal toMeal(DepositEntity depositEntity);
}
