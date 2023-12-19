package com.example.backend.application;

import com.example.backend.domain.company.InsufficientBalanceException;
import com.example.backend.domain.company.Company;
import com.example.backend.domain.deposit.DepositService;
import com.example.backend.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class DepositApplicationService {
    private final DepositService depositService;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    public void sendGiftDeposit(Long companyId, Long userId, BigDecimal amount) throws InsufficientBalanceException {
        final Company company = companyRepository.findById(companyId).orElseThrow();
        final User user = userRepository.findById(userId).orElseThrow();
        depositService.sendGiftDeposit(company, user, amount);
    }

    public void sendMealDeposit(Long companyId, Long userId, BigDecimal amount) throws InsufficientBalanceException {
        final Company company = companyRepository.findById(companyId).orElseThrow();
        final User user = userRepository.findById(userId).orElseThrow();
        depositService.sendMealDeposit(company, user, amount);
    }

}
