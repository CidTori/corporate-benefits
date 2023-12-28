package com.example.backend.infrastructure.company;

import com.example.backend.application.company.CompanyApplicationAdapter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor(staticName = "of")
public class CompanyAdapter extends CompanyApplicationAdapter {
    private final CompanyEntity entity;
    private final CompanyEntityRepository companyEntityRepository;

    @Override
    public BigDecimal getBalance() {
        return entity.getBalance();
    }

    @Override
    public void setBalance(BigDecimal balance) {
        entity.setBalance(balance);
    }

    @Override
    public void save() {
        companyEntityRepository.save(entity);
    }
}
