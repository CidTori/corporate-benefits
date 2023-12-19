package com.example.backend.application;

import com.example.backend.domain.user.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(Long id);
}
