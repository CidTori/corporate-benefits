package com.example.backend.infrastructure.user;

import com.example.backend.infrastructure.deposit.DepositEntityRepository;
import com.example.backend.infrastructure.deposit.DepositMapper;
import com.example.backend.domain.deposit.Deposit;
import com.example.backend.domain.user.User;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor(staticName = "of")
public class UserAdapter extends User {
    private final UserEntity user;
    private final DepositMapper depositMapper;
    private final DepositEntityRepository depositRepository;

    @Override
    public void addDeposit(Deposit deposit) {
        depositRepository.save(depositMapper.toEntity(deposit, user));
    }

    @Override
    protected List<Deposit> getDeposits() {
        return user.getDeposits().stream()
                .map(depositMapper::toDomain)
                .toList();
    }
}
