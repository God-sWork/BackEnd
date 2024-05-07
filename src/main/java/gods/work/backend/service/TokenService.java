package gods.work.backend.service;

import gods.work.backend.config.jwt.TokenProvider;
import gods.work.backend.domain.Trainer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TokenService {

    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final TrainerService trainerService;

    public String createNewAccessToken(String refreshToken) {
        if(!tokenProvider.verifyToken(refreshToken)) {
            throw new IllegalArgumentException("unverified token");
        }

        int trainerId = refreshTokenService.findByRefreshToken(refreshToken).getTrainerId();
        Trainer trainer = trainerService.findById(trainerId);

        return tokenProvider.createToken(trainer);
    }
}
