package gods.work.backend.service;

import gods.work.backend.config.jwt.JwtProperties;
import gods.work.backend.config.jwt.TokenProvider;
import gods.work.backend.domain.Trainer;
import gods.work.backend.dto.LoginTrainerRequest;
import gods.work.backend.repository.TrainerRepository;
import gods.work.backend.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class TrainerService implements UserDetailsService {

    private final TrainerRepository trainerRepository;
    private final TokenProvider tokenProvider;
    private final JwtProperties jwtProperties;


    public String login(LoginTrainerRequest dto) {
        String email = dto.getEmail();
        String password = dto.getPassword();
        Trainer trainer = trainerRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));

        // 비밀번호 확인
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(password, trainer.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다");
        }

        // 엑세스, 리프레쉬 토큰 발급
        String accessToken = tokenProvider.generateToken(trainer, Duration.ofHours(jwtProperties.getExpirationHoursAccess()));
//        String refreshToken = tokenProvider.generateToken(trainer, Duration.ofDays(jwtProperties.getExpirationDaysRefresh()));

        return accessToken;
    }

    @Transactional
    public void addTrainer(Trainer newTrainer) {
        if (trainerRepository.findByEmail(newTrainer.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 사용중인 아이디입니다");
        }
        newTrainer.setActive(true);
        newTrainer.setRegistrationYmd(DateUtil.getCurrentDateAsString());
        trainerRepository.save(newTrainer);
    }

    @Transactional
    public void updateTrainer(Trainer updatedTrainer) {
        // todo
        Trainer.builder().build();
    }

    public Trainer findById(int id) {
        return trainerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("unexpected id: " + id));
    }

    public Trainer findByEmail(String email) {
        return trainerRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("unexpected email: " + email));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
