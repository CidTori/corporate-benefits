package com.example.backend.presentation.employee;

import com.example.backend.application.company.CompanyNotFoundException;
import com.example.backend.application.employee.EmployeeApplicationService;
import com.example.backend.application.employee.EmployeeNotFoundException;
import com.example.backend.domain.company.InsufficientCompanyBalanceException;
import com.example.backend.utils.TriThrowingTriConsumer;
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
    private final EmployeeApplicationService depositService;

    @GetMapping("/employees/{employeeId}/balance")
    @PreAuthorize("permitAll()")
    //@PermitAll
    @SecurityRequirements
    public EmployeeBalanceResource getBalance(@PathVariable Long employeeId) {
        try {
            return new EmployeeBalanceResource(depositService.getBalance(employeeId));
        } catch (EmployeeNotFoundException ex) {
            throw new ResponseStatusException(BAD_REQUEST, "Employee not found", ex);
        }
    }

    @PostMapping("/employees/{employeeId}/gifts")
    public void addGift(
            Authentication authentication,
            @PathVariable Long employeeId,
            @RequestBody DepositRequest deposit
    ) {
        addDeposit(depositService::sendGift, authentication, employeeId, deposit);
    }

    @PostMapping("/employees/{employeeId}/meals")
    public void addMeal(
            Authentication authentication,
            @PathVariable Long employeeId,
            @RequestBody DepositRequest deposit
    ) {
        addDeposit(depositService::sendMeal, authentication, employeeId, deposit);
    }

    private void addDeposit(
            TriThrowingTriConsumer<
                    InsufficientCompanyBalanceException, CompanyNotFoundException, EmployeeNotFoundException,
                    Long, Long, BigDecimal
            > consumer,
            Authentication authentication,
            Long employeeId,
            DepositRequest deposit
    ) {
        try {
            Long companyId = Long.valueOf(authentication.getName());
            consumer.accept(companyId, employeeId, deposit.amount());
        } catch (InsufficientCompanyBalanceException ex) {
            throw new ResponseStatusException(BAD_REQUEST, "Company balance insufficient", ex);
        } catch (CompanyNotFoundException ex) {
            throw new ResponseStatusException(NOT_FOUND, "Company not found", ex);
        } catch (EmployeeNotFoundException ex) {
            throw new ResponseStatusException(NOT_FOUND, "Employee not found", ex);
        }
    }
}
