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
    public ResponseEntity<String> login(@RequestBody LoginTrainerRequest requestDto, HttpServletRequest request, HttpServletResponse response) {
        Trainer trainer = loginService.login(requestDto);

        // 쿠키에 리프레쉬 토큰 추가
        Duration refreshDuration = Duration.ofDays(jwtProperties.getExpirationDaysRefresh());

        String newRefreshToken = tokenProvider.generateToken(trainer, refreshDuration);
        RefreshToken refreshToken = refreshTokenRepository.findByTrainerId(trainer.getTrainerId())
                .map(entity -> entity.update(newRefreshToken))
                .orElse(new RefreshToken(trainer.getTrainerId(), newRefreshToken));

        refreshTokenRepository.save(refreshToken);

        CookieUtil.deleteCookie(request, response, WebConstants.REFRESH_TOKEN_COOKIE_NAME);
        CookieUtil.addCookie(response, WebConstants.REFRESH_TOKEN_COOKIE_NAME, newRefreshToken, (int) refreshDuration.toSeconds());
        log.debug("refresh token: {}", refreshToken);

        // 엑세스 토큰 생성
        String accessToken = tokenProvider.generateToken(trainer, Duration.ofDays(jwtProperties.getExpirationHoursAccess()));
        response.addHeader(WebConstants.ACCESS_TOKEN_HEADER_NAME, accessToken);
        log.debug("access token: {}", accessToken);
        return ResponseEntity.ok().body("ok");
    }

    @GetMapping(Path.LOGOUT)
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        CookieUtil.deleteCookie(request, response, WebConstants.REFRESH_TOKEN_COOKIE_NAME);
        log.debug("logout success");
        return ResponseEntity.noContent().build();
    }

    @PostMapping(Path.TOKEN)
    public ResponseEntity<String> createToken(@RequestHeader("Authorization") String token, HttpServletResponse response) {
        String newAccessToken = loginService.createNewAccessToken(token);
        response.addHeader(WebConstants.ACCESS_TOKEN_HEADER_NAME, newAccessToken);
        log.debug("new access token: {}", newAccessToken);
        return ResponseEntity.ok().body("success");
    }
}
