package gods.work.backend.service;

import gods.work.backend.config.error.exception.NotFoundException;
import gods.work.backend.config.jwt.JwtProperties;
import gods.work.backend.config.jwt.TokenProvider;
import gods.work.backend.domain.Trainer;
import gods.work.backend.dto.LoginTrainerRequest;
import gods.work.backend.repository.TrainerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class LoginService {

    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final TrainerService trainerService;
    private final JwtProperties jwtProperties;

    private final TrainerRepository trainerRepository;

    public Trainer login(LoginTrainerRequest dto) {
        String trainerLoginId = dto.getTrainer_login_id();
        String password = dto.getPassword();
        Trainer trainer = trainerRepository.findByTrainerLoginId(trainerLoginId)
                .orElseThrow(NotFoundException::new);

        // 비밀번호 확인
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!trainer.getPassword().equals("1234") && !encoder.matches(password, trainer.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다");
        }

        return trainer;
    }

    public String createNewAccessToken(String refreshToken) {
        if(!tokenProvider.verifyToken(refreshToken)) {
            throw new IllegalArgumentException("unverified token");
        }

        int trainerId = refreshTokenService.findByRefreshToken(refreshToken).getTrainerId();
        Trainer trainer = trainerService.findById(trainerId);

        return tokenProvider.generateToken(trainer, Duration.ofDays(jwtProperties.getExpirationDaysRefresh()));
    }
}
