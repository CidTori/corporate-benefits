package com.example.backend.infrastructure.deposit;

import com.example.backend.domain.deposit.DepositType;
import com.example.backend.infrastructure.user.UserEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity(name = "Deposit")
public class DepositEntity {
    @Id private Long id;
    @ManyToOne private UserEntity user;
    private DepositType type;
    private BigDecimal amount;
    private LocalDate receptionDate;
}
