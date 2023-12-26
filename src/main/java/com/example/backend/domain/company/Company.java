package com.example.backend.domain.company;

import java.math.BigDecimal;

public abstract class Company {
    public abstract BigDecimal getBalance();
    public abstract void setBalance(BigDecimal balance);
}
