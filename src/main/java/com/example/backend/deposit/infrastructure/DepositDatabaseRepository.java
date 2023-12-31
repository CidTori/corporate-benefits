package com.example.backend.deposit.infrastructure;

import com.example.backend.deposit.application.DepositRepository;
import com.example.backend.deposit.domain.Gift;
import com.example.backend.deposit.domain.Meal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DepositDatabaseRepository implements DepositRepository {
    private final DepositEntityRepository depositEntityRepository;
    private final DepositEntityMapper depositEntityMapper;

    @Override
    public Gift save(Gift gift) {
        return depositEntityMapper.toGift(depositEntityRepository.save(depositEntityMapper.toEntity(gift)));
    }

    @Override
    public Meal save(Meal meal) {
        return depositEntityMapper.toMeal(depositEntityRepository.save(depositEntityMapper.toEntity(meal)));
    }

}
