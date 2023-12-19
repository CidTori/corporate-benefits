package com.example.backend.infrastructure.company;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity(name = "Company")
@Data
public class CompanyEntity {
    @Id private Long id;
}
