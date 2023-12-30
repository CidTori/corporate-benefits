package com.example.backend.deposit.application;

import com.example.backend.deposit.application.company.CompanyNotFoundException;
import com.example.backend.deposit.application.company.CompanyRepository;
import com.example.backend.deposit.application.employee.DepositEmployeeNotFoundException;
import com.example.backend.deposit.application.employee.DepositEmployeeRepository;
import com.example.backend.deposit.domain.Deposit;
import com.example.backend.deposit.domain.DepositService;
import com.example.backend.deposit.domain.company.Company;
import com.example.backend.deposit.domain.company.InsufficientCompanyBalanceException;
import com.example.backend.utils.ThrowingTriFunction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class DepositApplicationService {
    private final DepositService depositService;
    private final CompanyRepository companyRepository;
    private final DepositEmployeeRepository depositEmployeeRepository;
    private final DepositRepository depositRepository;

    @Transactional
    public Deposit sendGift(Long companyId, Long employeeId, BigDecimal amount)
            throws InsufficientCompanyBalanceException, CompanyNotFoundException, DepositEmployeeNotFoundException {
        return sendDeposit(depositService::sendGift, companyId, employeeId, amount);
    }

    @Transactional
    public Deposit sendMeal(Long companyId, Long employeeId, BigDecimal amount)
            throws InsufficientCompanyBalanceException, CompanyNotFoundException, DepositEmployeeNotFoundException {
        return sendDeposit(depositService::sendMeal, companyId, employeeId, amount);
    }

    private Deposit sendDeposit(
            ThrowingTriFunction<
                    InsufficientCompanyBalanceException,
                    Deposit,
                    Company, Long, BigDecimal
            > sendingDeposit,
            Long companyId,
            Long employeeId,
            BigDecimal amount
    ) throws
            CompanyNotFoundException,
            DepositEmployeeNotFoundException,
            InsufficientCompanyBalanceException
    {
        final Company company = companyRepository.findById(companyId).orElseThrow(() -> new CompanyNotFoundException("Company with id " + companyId + " not found"));
        if(!depositEmployeeRepository.existsById(employeeId)) throw new DepositEmployeeNotFoundException("Employee with id " + employeeId + " not found");

        Deposit deposit = sendingDeposit.apply(company, employeeId, amount);

        companyRepository.save(company);
        return depositRepository.save(deposit);
    }
}
