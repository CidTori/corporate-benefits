package com.example.backend.infrastructure.company;

import com.example.backend.application.company.CompanyRepository;
import com.example.backend.domain.company.Company;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CompanyDatabaseRepository implements CompanyRepository {
    private final CompanyEntityRepository companyEntityRepository;

    @Override
    public Optional<Company> findById(Long id) {
        return companyEntityRepository.findById(id)
                .map(CompanyAdapter::of);
    }

}
