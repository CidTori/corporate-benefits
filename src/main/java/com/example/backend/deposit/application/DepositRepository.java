package com.example.backend.deposit.application;

import com.example.backend.deposit.domain.Gift;
import com.example.backend.deposit.domain.Meal;

public interface DepositRepository {
    Gift save(Gift deposit);
    Meal save(Meal deposit);
}
