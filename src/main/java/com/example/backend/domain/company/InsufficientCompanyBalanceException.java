package com.example.backend.domain.company;

public class InsufficientCompanyBalanceException extends Exception {
    public InsufficientCompanyBalanceException(String message) {
        super(message);
    }
}
