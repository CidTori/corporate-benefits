package com.example.backend.infrastructure.user;

import com.example.backend.infrastructure.deposit.DepositEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Entity(name = "User")
@Data
public class UserEntity {
    @Id private Long id;
    @OneToMany(mappedBy = "user") private List<DepositEntity> deposits;
}
