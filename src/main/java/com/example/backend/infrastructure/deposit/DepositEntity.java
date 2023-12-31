package com.example.backend.infrastructure.deposit;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    @ManyToOne @JoinColumn(name = "type")
    private DepositTypeEntity type;
    private BigDecimal amount;
    private LocalDate receptionDate;
}
