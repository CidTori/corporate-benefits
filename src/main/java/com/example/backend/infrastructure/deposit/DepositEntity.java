package com.example.backend.infrastructure.deposit;

import com.example.backend.domain.deposit.DepositType;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepositEntity {
    private DepositType type;
    private BigDecimal amount;
    private LocalDate receptionDate;
}
