package com.example.sociallogin.repository;

import com.example.sociallogin.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUniqueId(String uniqueId);
}
