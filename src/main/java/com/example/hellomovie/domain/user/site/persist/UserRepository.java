package com.example.hellomovie.domain.user.site.persist;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(String userId);
    Optional<User> findByEmailAuthKey(String uuid);
    boolean existsByUserId(String userId);
}
