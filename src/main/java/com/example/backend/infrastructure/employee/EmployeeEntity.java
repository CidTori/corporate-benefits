package com.example.backend.infrastructure.employee;

import com.example.backend.infrastructure.deposit.DepositEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

import static jakarta.persistence.CascadeType.ALL;

@Entity(name = "employee")
@Data
public class EmployeeEntity {
    @Id private Long id;
    @OneToMany(mappedBy = "employee", cascade = ALL) private List<DepositEntity> deposits;
}
