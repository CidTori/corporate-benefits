package com.example.backend.application;

import com.example.backend.domain.company.Company;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository {
    Optional<Company> findById(Long id);

    void save(Company company);
}
