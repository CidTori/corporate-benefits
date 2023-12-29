package com.example.backend.deposit.infrastructure.company;

import com.example.backend.deposit.application.company.CompanyRepository;
import com.example.backend.deposit.domain.company.Company;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
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
