package gods.work.backend.repository;

import gods.work.backend.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByTrainerId(Integer trainerId);
    Optional<RefreshToken> findByRefreshToken(String token);
}
