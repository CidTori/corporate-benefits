package com.example.backend.infrastructure.user;

import com.example.backend.application.UserRepository;
import com.example.backend.infrastructure.deposit.DepositEntityRepository;
import com.example.backend.infrastructure.deposit.DepositMapper;
import com.example.backend.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserDatabaseRepository implements UserRepository {
    private final UserEntityRepository userRepository;
    private final DepositMapper depositMapper;
    private final DepositEntityRepository depositRepository;

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id)
                .map(u -> UserAdapter.of(u, depositMapper, depositRepository));
    }
}
