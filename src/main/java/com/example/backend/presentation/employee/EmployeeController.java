package com.example.backend.presentation.employee;

import com.example.backend.application.company.CompanyNotFoundException;
import com.example.backend.application.deposit.DepositApplicationService;
import com.example.backend.application.employee.EmployeeNotFoundException;
import com.example.backend.domain.company.InsufficientCompanyBalanceException;
import com.example.backend.domain.deposit.Deposit;
import com.example.backend.domain.deposit.DepositType;
import com.example.backend.presentation.deposit.DepositRequest;
import com.example.backend.presentation.deposit.DepositResource;
import com.example.backend.presentation.deposit.DepositResourceMapper;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import static com.example.backend.domain.deposit.DepositType.GIFT;
import static com.example.backend.domain.deposit.DepositType.MEAL;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class EmployeeController {
    private final DepositApplicationService depositApplicationService;
    private final DepositResourceMapper depositResourceMapper;

    @GetMapping("/employees/{employeeId}/balance")
    @PreAuthorize("permitAll()")
    //@PermitAll
    @SecurityRequirements
    public EmployeeBalanceResource getBalance(@PathVariable Long employeeId) {
        try {
            return new EmployeeBalanceResource(depositApplicationService.getBalance(employeeId));
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
        return addDeposit(GIFT, authentication, employeeId, deposit);
    }

    @PostMapping("/employees/{employeeId}/meals")
    public DepositResource addMeal(
            Authentication authentication,
            @PathVariable Long employeeId,
            @RequestBody DepositRequest deposit
    ) {
        return addDeposit(MEAL, authentication, employeeId, deposit);
    }

    private DepositResource addDeposit(
            DepositType type,
            Authentication authentication,
            Long employeeId,
            DepositRequest request
    ) {
        try {
            Long companyId = Long.valueOf(authentication.getName());
            Deposit deposit = depositApplicationService.sendDeposit(type, companyId, employeeId, request.amount());
            return depositResourceMapper.toResource(deposit);
        } catch (InsufficientCompanyBalanceException ex) {
            throw new ResponseStatusException(BAD_REQUEST, "Company balance insufficient", ex);
        } catch (CompanyNotFoundException ex) {
            throw new ResponseStatusException(NOT_FOUND, "Company not found", ex);
        } catch (EmployeeNotFoundException ex) {
            throw new ResponseStatusException(NOT_FOUND, "Employee not found", ex);
        }
    }
}
