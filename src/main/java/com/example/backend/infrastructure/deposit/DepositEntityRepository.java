package com.example.backend.infrastructure.deposit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepositEntityRepository extends JpaRepository<DepositEntity, Long> {}
