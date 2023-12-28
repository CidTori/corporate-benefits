package com.example.backend.infrastructure.company;

import com.example.backend.application.company.CompanyApplicationAdapter;
import com.example.backend.application.company.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CompanyDatabaseRepository implements CompanyRepository {
    private final CompanyEntityRepository companyEntityRepository;

    @Override
    public Optional<CompanyApplicationAdapter> findById(Long id) {
        return companyEntityRepository.findById(id)
                .map(e -> CompanyAdapter.of(e, companyEntityRepository));
    }

}
