package com.example.backend.application.company;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository {
    Optional<CompanyApplicationAdapter> findById(Long id);
}
