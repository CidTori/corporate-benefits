package com.example.backend.infrastructure.company;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;

@Entity(name = "company")
@Data
public class CompanyEntity {
    @Id private Long id;
    private BigDecimal balance;
}
