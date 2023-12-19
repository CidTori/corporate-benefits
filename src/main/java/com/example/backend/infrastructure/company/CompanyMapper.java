package com.example.backend.infrastructure.company;

import com.example.backend.domain.company.Company;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyMapper {
    Company toDomain(CompanyEntity companyEntity);
}
