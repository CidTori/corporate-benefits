package com.example.backend.domain.deposit;

import com.example.backend.domain.company.Company;
import com.example.backend.infrastructure.company.CompanyEntity;
import com.example.backend.infrastructure.employee.EmployeeEntityRepository;
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
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static com.nimbusds.jose.JWSAlgorithm.HS256;
import static com.nimbusds.jwt.JWTClaimsSet.Builder;
import static java.math.BigDecimal.valueOf;
import static java.time.Clock.fixed;
import static java.time.Month.JANUARY;
import static java.time.Month.MARCH;
import static java.time.ZoneId.systemDefault;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TransactionIT {

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @MockBean
    Supplier<Clock> clockSupplier;

    @MockBean
    EmployeeEntityRepository employeeEntityRepository;

    @Test
    void rollback() {
        LocalDate giftDate = LocalDate.of(2023, JANUARY, 15);
        String teslaId = "1234567890";
        String johnId = "1";

        when(employeeEntityRepository.save(any())).thenThrow(IllegalArgumentException.class);

        setDateTo(giftDate);
        ResponseEntity<String> giftResponse = testRestTemplate.postForEntity(
                "/employees/" + johnId + "/gifts",
                new HttpEntity<>(
                        Map.of("amount", valueOf(100)),
                        getDepositHeaders(createJWT(teslaId, giftDate))
                ),
                String.class
        );

        CompanyEntity company = jdbcTemplate.query("SELECT * FROM company WHERE id = " + teslaId, new BeanPropertyRowMapper<>(CompanyEntity.class)).getFirst();
        assertEquals(valueOf(1000), company.getBalance());
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