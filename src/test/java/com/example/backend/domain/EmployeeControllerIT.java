package com.example.backend.domain;

import com.example.backend.presentation.employee.deposit.DepositResource;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.Map;
import java.util.function.Supplier;

import static com.nimbusds.jose.JWSAlgorithm.HS256;
import static com.nimbusds.jwt.JWTClaimsSet.Builder;
import static java.math.BigDecimal.valueOf;
import static java.time.Clock.fixed;
import static java.time.LocalDate.of;
import static java.time.Month.JANUARY;
import static java.time.Month.MARCH;
import static java.time.ZoneId.systemDefault;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@DirtiesContext
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class EmployeeControllerIT {

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @MockBean
    Supplier<Clock> clockSupplier;

    @Test
    void sendGiftDeposit_ok() {
        LocalDate giftDate = of(2023, JANUARY, 15);
        LocalDate giftEndDate = giftDate.plusYears(1);
        LocalDate mealDate = giftDate.plusMonths(1);
        LocalDate mealEndDate = of(mealDate.plusYears(1).getYear(), MARCH, 1);
        String teslaId = "1234567890";
        String johnId = "1";

        setDateTo(giftDate);
        ResponseEntity<DepositResource> giftResponse = testRestTemplate.postForEntity(
                "/employees/" + johnId + "/gifts",
                new HttpEntity<>(
                        Map.of("amount", valueOf(100)),
                        getDepositHeaders(createJWT(teslaId, giftDate))
                ),
                DepositResource.class
        );
        BigDecimal balanceAfterGift = jdbcTemplate.queryForObject("SELECT balance FROM company WHERE id = ?", BigDecimal.class, teslaId);
        setDateTo(mealDate);
        ResponseEntity<DepositResource> mealResponse = testRestTemplate.postForEntity(
                "/employees/" + johnId + "/meals",
                new HttpEntity<>(
                        Map.of("amount", valueOf(50)),
                        getDepositHeaders(createJWT(teslaId, giftDate))
                ),
                DepositResource.class
        );
        BigDecimal balanceAfterMeal = jdbcTemplate.queryForObject("SELECT balance FROM company WHERE id = ?", BigDecimal.class, teslaId);

        setDateTo(giftEndDate.minusDays(1));
        ResponseEntity<Map> balanceBeforeGiftEndDate = testRestTemplate.getForEntity("/employees/" + johnId + "/balance", Map.class);
        setDateTo(giftEndDate);
        ResponseEntity<Map> balanceGiftEndDate = testRestTemplate.getForEntity("/employees/" + johnId + "/balance", Map.class);
        setDateTo(mealEndDate.minusDays(1));
        ResponseEntity<Map> balanceBeforeMealEndDate = testRestTemplate.getForEntity("/employees/" + johnId + "/balance", Map.class);
        setDateTo(mealEndDate);
        ResponseEntity<Map> balanceMealEndDate = testRestTemplate.getForEntity("/employees/" + johnId + "/balance", Map.class);


        assertEquals(OK, giftResponse.getStatusCode());
        assertEquals(OK, mealResponse.getStatusCode());
        assertEquals(OK, balanceBeforeGiftEndDate.getStatusCode());
        assertEquals(OK, balanceGiftEndDate.getStatusCode());
        assertEquals(OK, balanceBeforeMealEndDate.getStatusCode());
        assertEquals(OK, balanceMealEndDate.getStatusCode());

        assertEquals(valueOf(900), balanceAfterGift);
        assertEquals(valueOf(850), balanceAfterMeal);

        assertEquals(150, balanceBeforeGiftEndDate.getBody().get("balance"));
        assertEquals(50, balanceGiftEndDate.getBody().get("balance"));
        assertEquals(50, balanceBeforeMealEndDate.getBody().get("balance"));
        assertEquals(0, balanceMealEndDate.getBody().get("balance"));
    }

    @Test
    void sendMealDeposit_ko() {
        LocalDate mealDate = of(2023, JANUARY, 15);
        String appleId = "1234567890";
        String jessicaId = "1";

        ResponseEntity<DepositResource> mealResponse = testRestTemplate.postForEntity(
                "/employees/" + jessicaId + "/meals",
                new HttpEntity<>(
                        Map.of("amount", valueOf(1050)),
                        getDepositHeaders(createJWT(appleId, mealDate))
                ),
                DepositResource.class
        );

        assertEquals(BAD_REQUEST, mealResponse.getStatusCode());
    }

    private static HttpHeaders getDepositHeaders(String jwt) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + jwt);
        return headers;
    }

    private void setDateTo(LocalDate receptionDate) {
        Instant noon = receptionDate.atStartOfDay(systemDefault()).plusHours(12).toInstant();
        when(clockSupplier.get()).thenReturn(fixed(noon, systemDefault()));
    }

    private static String createJWT(String subject, LocalDate issueTime) {
        String secret = "your-256-bit-secretyour-256-bit-secret";

        JWTClaimsSet claimsSet = new Builder()
                .subject(subject)
                .issueTime(Date.from(issueTime.atStartOfDay(systemDefault()).toInstant()))
                .build();

        try {
            SignedJWT signedJWT = new SignedJWT(new JWSHeader(HS256), claimsSet);
            signedJWT.sign(new MACSigner(secret.getBytes()));
            return signedJWT.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }
}