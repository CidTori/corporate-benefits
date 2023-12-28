package com.example.backend.infrastructure.company;

import com.example.backend.domain.company.Company;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor(staticName = "of")
public class CompanyAdapter extends Company {
    private final CompanyEntity entity;

    @Override
    public BigDecimal getBalance() {
        return entity.getBalance();
    }

    @Override
    public void setBalance(BigDecimal balance) {
        entity.setBalance(balance);
    }
}
