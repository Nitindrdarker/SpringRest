package com.example.jwt2.demo.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jwt2.demo.Entities.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
