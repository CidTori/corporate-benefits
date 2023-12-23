package com.example.backend.presentation;

import com.example.backend.application.DepositApplicationService;
import com.example.backend.domain.company.InsufficientBalanceException;
import com.example.backend.utils.ThrowingTriConsumer;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class DepositController {
    private final DepositApplicationService depositService;

    @GetMapping("/employees/{employeeId}/balance")
    @PreAuthorize("permitAll()")
    //@PermitAll
    @SecurityRequirements
    public BalanceResource getBalance(@PathVariable Long employeeId) {
        return new BalanceResource(depositService.getBalance(employeeId));
    }

    @PostMapping("/employees/{id}/gifts")
    public void addGift(
            Authentication authentication,
            @PathVariable Long id,
            @RequestBody DepositResource deposit
    ) {
        addDeposit(depositService::sendGift, authentication, id, deposit);
    }

    @PostMapping("/employees/{id}/meals")
    public void addMeal(
            Authentication authentication,
            @PathVariable Long id,
            @RequestBody DepositResource deposit
    ) {
        addDeposit(depositService::sendMeal, authentication, id, deposit);
    }

    private void addDeposit(
            ThrowingTriConsumer<InsufficientBalanceException, Long, Long, BigDecimal> consumer,
            Authentication authentication,
            Long id,
            DepositResource deposit
    ) {
        try {
            consumer.accept(Long.valueOf(authentication.getName()), id, deposit.amount());
        } catch (InsufficientBalanceException ex) {
            throw new RuntimeException(ex);
        }
    }

}
