package com.example.backend.deposit.domain.company;

public class InsufficientCompanyBalanceException extends Exception {
    public InsufficientCompanyBalanceException(String message) {
        super(message);
    }
}
