package com.example.backend.deposit.application;

import com.example.backend.deposit.domain.Deposit;

import java.util.List;

public interface DepositRepository {
    List<Deposit> findByEmployeeId(Long employeeId);
    void save(Deposit deposit);
}
