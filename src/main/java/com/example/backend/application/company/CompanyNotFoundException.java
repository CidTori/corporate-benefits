package com.example.backend.application.company;

public class CompanyNotFoundException extends Exception {
    public CompanyNotFoundException(Long companyId) {
        super("Company with id " + companyId + " not found");
    }
}
