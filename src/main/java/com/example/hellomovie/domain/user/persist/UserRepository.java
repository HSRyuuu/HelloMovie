package com.example.hellomovie.domain.user.persist;

import com.example.hellomovie.domain.user.persist.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
