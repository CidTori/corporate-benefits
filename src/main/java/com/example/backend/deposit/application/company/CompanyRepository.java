package com.example.backend.deposit.application.company;

import com.example.backend.deposit.domain.company.Company;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository {
    Optional<Company> findById(Long id);
    void save(Company company);
}
