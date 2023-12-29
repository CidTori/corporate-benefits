package com.example.backend.presentation.employee;

import com.example.backend.deposit.application.DepositApplicationService;
import com.example.backend.deposit.application.company.CompanyNotFoundException;
import com.example.backend.deposit.application.employee.DepositEmployeeNotFoundException;
import com.example.backend.deposit.domain.Deposit;
import com.example.backend.deposit.domain.company.InsufficientCompanyBalanceException;
import com.example.backend.employee.application.EmployeeApplicationService;
import com.example.backend.employee.application.EmployeeNotFoundException;
import com.example.backend.presentation.employee.deposit.DepositRequest;
import com.example.backend.presentation.employee.deposit.DepositResource;
import com.example.backend.presentation.employee.deposit.DepositResourceMapper;
import com.example.backend.utils.TriThrowingTriFunction;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class EmployeeController {
    private final DepositApplicationService depositService;
    private final EmployeeApplicationService employeeApplicationService;
    private final DepositResourceMapper depositResourceMapper;

    @GetMapping("/employees/{employeeId}/balance")
    @PreAuthorize("permitAll()")
    //@PermitAll
    @SecurityRequirements
    public EmployeeBalanceResource getBalance(@PathVariable Long employeeId) {
        try {
            return new EmployeeBalanceResource(employeeApplicationService.getBalance(employeeId));
        } catch (EmployeeNotFoundException ex) {
            throw new ResponseStatusException(BAD_REQUEST, "Employee not found", ex);
        }
    }

    @PostMapping("/employees/{employeeId}/gifts")
    public DepositResource addGift(
            Authentication authentication,
            @PathVariable Long employeeId,
            @RequestBody DepositRequest deposit
    ) {
        return addDeposit(depositService::sendGift, authentication, employeeId, deposit);
    }

    @PostMapping("/employees/{employeeId}/meals")
    public DepositResource addMeal(
            Authentication authentication,
            @PathVariable Long employeeId,
            @RequestBody DepositRequest deposit
    ) {
        return addDeposit(depositService::sendMeal, authentication, employeeId, deposit);
    }

    private DepositResource addDeposit(
            TriThrowingTriFunction<
                    InsufficientCompanyBalanceException, CompanyNotFoundException, DepositEmployeeNotFoundException,
                    Deposit,
                    Long, Long, BigDecimal
            > consumer,
            Authentication authentication,
            Long employeeId,
            DepositRequest request
    ) {
        try {
            Long companyId = Long.valueOf(authentication.getName());
            Deposit deposit = consumer.apply(companyId, employeeId, request.amount());
            return depositResourceMapper.toResource(deposit);
        } catch (InsufficientCompanyBalanceException ex) {
            throw new ResponseStatusException(BAD_REQUEST, "Company balance insufficient", ex);
        } catch (CompanyNotFoundException ex) {
            throw new ResponseStatusException(NOT_FOUND, "Company not found", ex);
        } catch (DepositEmployeeNotFoundException ex) {
            throw new ResponseStatusException(NOT_FOUND, "Employee not found", ex);
        }
    }
}
