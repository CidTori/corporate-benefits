package com.example.backend.infrastructure.deposit;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Embeddable
@Data
public class DepositEntity {
    @ManyToOne @JoinColumn(name = "type")
    private DepositTypeEntity type;
    private BigDecimal amount;
    private LocalDate receptionDate;
}
