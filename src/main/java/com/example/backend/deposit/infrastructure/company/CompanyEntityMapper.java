package com.example.backend.deposit.infrastructure.company;

import com.example.backend.deposit.domain.company.Company;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface CompanyEntityMapper {
    Company toDomain(CompanyEntity companyEntity);

    CompanyEntity toEntity(Company company);
}
