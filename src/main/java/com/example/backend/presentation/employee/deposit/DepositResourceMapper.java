package com.example.backend.presentation.employee.deposit;

import com.example.backend.deposit.domain.Deposit;
import com.example.backend.deposit.domain.Gift;
import com.example.backend.deposit.domain.Meal;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface DepositResourceMapper {
    GiftResource toGiftResource(Gift gift);

    MealResource toMealResource(Meal meal);
}
