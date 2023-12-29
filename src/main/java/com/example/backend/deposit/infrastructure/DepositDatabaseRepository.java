package com.example.backend.deposit.infrastructure;

import com.example.backend.deposit.application.DepositRepository;
import com.example.backend.deposit.domain.Deposit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class DepositDatabaseRepository implements DepositRepository {
    private final DepositEntityRepository depositEntityRepository;
    private final DepositEntityMapper depositEntityMapper;

    @Override
    public List<Deposit> findByEmployeeId(Long employeeId) {
        return depositEntityRepository.findByEmployeeId(employeeId).stream()
                .map(depositEntityMapper::toDomain)
                .toList();
    }

    @Override
    public void save(Deposit deposit) {
        depositEntityRepository.save(depositEntityMapper.toEntity(deposit));
    }
}
