package gods.work.backend.config.jwt;

import gods.work.backend.domain.Trainer;
import gods.work.backend.repository.TrainerRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Duration;
import java.util.Date;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
public class TokenProviderTest {
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private TrainerRepository trainerRepository;
    @Autowired
    private JwtProperties jwtProperties;

    @DisplayName("generateToken() 테스트: 유저 정보와 만료 기간을 전달해 토큰 생성")
    @Test
    void generateToken() {
        // given
        Trainer testUser = trainerRepository.save(Trainer.builder()
                .email("user@email.com")
                .password("test")
                .build());

        // when
        String token = tokenProvider.generateToken(testUser, Duration.ofHours(14));

        Claims claims = Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody();

        // then
        int trainerId = claims.get("id", Integer.class);

        assertThat(trainerId).isEqualTo(testUser.getTrainerId());
    }

    @DisplayName("validToken_invalid() 테스트: 만료된 토큰인 때에 휴효성 검증에 실패한다")
    @Test
    void validToken_invalid() {
        // given
        String token = JwtFactory.builder()
                .expiration(new Date(new Date().getTime() - Duration.ofDays(7).toMillis()))
                .build().createToken(jwtProperties);

        // when
        boolean result = tokenProvider.verifyToken(token);
        //then
        assertThat(result).isFalse();
    }

    @DisplayName("validToken_valid() 테스트: 만료된 토큰인 때에 휴효성 검증에 성공한다")
    @Test
    void validToken_valid() {
        // given
        String token = JwtFactory.withDefaultValues()
                .createToken(jwtProperties);

        // when
        boolean result = tokenProvider.verifyToken(token);
        // then
        assertThat(result).isTrue();
    }

    @DisplayName("getAuthentication() 테스트: 토큰 기반으로 인증 정보를 가져올 수 있다.")
    @Test
    void getAuthentication() {
        // given
        String email = "test@email.com";
        String token = JwtFactory.builder()
                .subject(email)
                .build()
                .createToken(jwtProperties);

        // when
        Authentication authentication = tokenProvider.getAuthentication(token);
        // then
        assertThat(((UserDetails) authentication.getPrincipal()).getUsername()).isEqualTo(email);
    }

    @DisplayName("getUserId() 테스트: 토큰 기반으로 유저 ID를 가져올 수 있다.")
    @Test
    void getUserId() {
        // given
        Long userId = 1L;
        String token = JwtFactory.builder()
                .claims(Map.of("id", userId))
                .build()
                .createToken(jwtProperties);

        // when
        int userIdByToken = tokenProvider.getUserId(token);
        // then
        assertThat(userIdByToken).isEqualTo(userId);
    }


}
