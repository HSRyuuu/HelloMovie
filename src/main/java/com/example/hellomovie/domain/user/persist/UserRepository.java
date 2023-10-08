package com.example.hellomovie.domain.user.persist;

import com.example.hellomovie.domain.user.persist.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUserId(String userId);
    Optional<User> findByEmailAuthKey(String uuid);
}
