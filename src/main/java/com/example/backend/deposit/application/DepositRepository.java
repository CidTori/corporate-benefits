package com.example.backend.deposit.application;

import com.example.backend.deposit.domain.Deposit;

public interface DepositRepository {
    void save(Deposit deposit);
}
