package com.example.backend.infrastructure.company;

import com.example.backend.application.company.CompanyRepository;
import com.example.backend.domain.company.Company;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CompanyDatabaseRepository implements CompanyRepository {
    private final CompanyEntityRepository companyEntityRepository;
    private final CompanyEntityMapper companyEntityMapper;

    @Override
    public Optional<Company> findById(Long id) {
        return companyEntityRepository.findById(id)
                .map(companyEntityMapper::toDomain);
    }

    @Override
    public void save(Company company) {
        companyEntityRepository.save(companyEntityMapper.toEntity(company));
    }

}
