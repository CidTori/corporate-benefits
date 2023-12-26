package com.example.backend.infrastructure.deposit;

import com.example.backend.domain.deposit.DepositType;
import com.example.backend.infrastructure.employee.EmployeeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity(name = "deposit")
@Data
public class DepositEntity {
    @Id @GeneratedValue(strategy = IDENTITY) private Long id;
    @ManyToOne private EmployeeEntity employee;
    private DepositType type;
    private BigDecimal amount;
    private LocalDate receptionDate;
}
