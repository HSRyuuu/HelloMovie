package com.example.hellomovie.domain.user.social.common.persist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SocialUserRepository extends JpaRepository<SocialUser, String> {
    Optional<SocialUser> findByUserId(String userId);
    void deleteByUserId(String userId);
    boolean existsByNickname(String nickname);

}
