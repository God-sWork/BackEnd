package gods.work.backend.controller;

import gods.work.backend.config.jwt.JwtProperties;
import gods.work.backend.config.jwt.TokenProvider;
import gods.work.backend.constants.Path;
import gods.work.backend.constants.WebConstants;
import gods.work.backend.domain.RefreshToken;
import gods.work.backend.domain.Trainer;
import gods.work.backend.dto.LoginTrainerRequest;
import gods.work.backend.dto.TokenResponse;
import gods.work.backend.repository.RefreshTokenRepository;
import gods.work.backend.service.LoginService;
import gods.work.backend.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@Slf4j
@RequiredArgsConstructor
@RestController
public class LoginController {

    private final TokenProvider tokenProvider;
    private final JwtProperties jwtProperties;
    private final RefreshTokenRepository refreshTokenRepository;
    private final LoginService loginService;


    @PostMapping(Path.LOGIN)
    public ResponseEntity<TokenResponse> login(@RequestBody LoginTrainerRequest requestDto, HttpServletRequest request, HttpServletResponse response) {
        Trainer trainer = loginService.login(requestDto);

        // 쿠키에 리프레쉬 토큰 추가
        Duration refresh_token_duration = Duration.ofDays(jwtProperties.getExpirationDaysRefresh());

        String newRefreshToken = tokenProvider.generateToken(trainer, refresh_token_duration);
        RefreshToken refreshToken = refreshTokenRepository.findByTrainerId(trainer.getTrainerId())
                .map(entity -> entity.update(newRefreshToken))
                .orElse(new RefreshToken(trainer.getTrainerId(), newRefreshToken));

        refreshTokenRepository.save(refreshToken);

        int cookieMaxAge = (int) refresh_token_duration.toSeconds();
        CookieUtil.deleteCookie(request, response, WebConstants.REFRESH_TOKEN_COOKIE_NAME);
        CookieUtil.addCookie(response, WebConstants.REFRESH_TOKEN_COOKIE_NAME, newRefreshToken, cookieMaxAge);
        log.debug("refresh token: {}", refreshToken);

        // 엑세스 토큰 생성
        String accessToken = tokenProvider.generateToken(trainer, Duration.ofHours(jwtProperties.getExpirationHoursAccess()));
        log.debug("access token: {}", accessToken);
        return ResponseEntity.ok().body(new TokenResponse(accessToken));
    }

    @GetMapping(Path.LOGOUT)
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        CookieUtil.deleteCookie(request, response, WebConstants.REFRESH_TOKEN_COOKIE_NAME);

        // todo: blackList 추가?

        log.debug("logout success");
        return ResponseEntity.noContent().build();
    }

    @PostMapping(Path.TOKEN)
    public ResponseEntity<TokenResponse> createToken(@RequestHeader("Authorization") String token) {
        String newAccessToken = loginService.createNewAccessToken(token);
        log.debug("new access token: {}", newAccessToken);
        return ResponseEntity.status(HttpStatus.CREATED).body(new TokenResponse(newAccessToken));
    }
}
