package nodawoon.me_to_you.domain.user.domain.repository;

import nodawoon.me_to_you.domain.user.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByUserId(Long userId);

    Optional<RefreshToken> findByRefreshToken(String token);

    void deleteByUserId(Long userId);

    void deleteByRefreshToken(String refreshToken);
}
