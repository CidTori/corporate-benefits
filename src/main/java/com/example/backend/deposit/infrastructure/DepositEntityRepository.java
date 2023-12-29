package com.example.backend.deposit.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepositEntityRepository extends JpaRepository<DepositEntity, Long> {
    List<DepositEntity> findByEmployeeId(Long employeeId);
}
