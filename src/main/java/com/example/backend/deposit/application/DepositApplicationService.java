package com.example.backend.deposit.application;

import com.example.backend.deposit.application.company.CompanyNotFoundException;
import com.example.backend.deposit.application.company.CompanyRepository;
import com.example.backend.deposit.application.employee.DepositEmployeeNotFoundException;
import com.example.backend.deposit.application.employee.DepositEmployeeRepository;
import com.example.backend.deposit.domain.Deposit;
import com.example.backend.deposit.domain.DepositService;
import com.example.backend.deposit.domain.Gift;
import com.example.backend.deposit.domain.Meal;
import com.example.backend.deposit.domain.company.Company;
import com.example.backend.deposit.domain.company.InsufficientCompanyBalanceException;
import com.example.backend.utils.ThrowingTriFunction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.function.UnaryOperator;

@Service
@RequiredArgsConstructor
public class DepositApplicationService {
    private final DepositService depositService;
    private final CompanyRepository companyRepository;
    private final DepositEmployeeRepository depositEmployeeRepository;
    private final DepositRepository depositRepository;

    @Transactional
    public Gift sendGift(Long companyId, Long employeeId, BigDecimal amount)
            throws InsufficientCompanyBalanceException, CompanyNotFoundException, DepositEmployeeNotFoundException {
        return sendDeposit(depositService::sendGift, companyId, employeeId, amount, depositRepository::save);
    }

    @Transactional
    public Meal sendMeal(Long companyId, Long employeeId, BigDecimal amount)
            throws InsufficientCompanyBalanceException, CompanyNotFoundException, DepositEmployeeNotFoundException {
        return sendDeposit(depositService::sendMeal, companyId, employeeId, amount, depositRepository::save);
    }

    private <T extends Deposit> T sendDeposit(
            ThrowingTriFunction<
                    InsufficientCompanyBalanceException,
                    T,
                    Company, Long, BigDecimal
            > sendingDeposit,
            Long companyId,
            Long employeeId,
            BigDecimal amount,
            UnaryOperator<T> saving
    ) throws
            CompanyNotFoundException,
            DepositEmployeeNotFoundException,
            InsufficientCompanyBalanceException
    {
        final Company company = companyRepository.findById(companyId).orElseThrow(() -> new CompanyNotFoundException("Company with id " + companyId + " not found"));
        if(!depositEmployeeRepository.existsById(employeeId)) throw new DepositEmployeeNotFoundException("Employee with id " + employeeId + " not found");

        T deposit = sendingDeposit.apply(company, employeeId, amount);

        companyRepository.save(company);
        return saving.apply(deposit);
    }
}
