package gods.work.backend.controller;

import gods.work.backend.dto.AddTrainerRequest;
import gods.work.backend.dto.LoginTrainerRequest;
import gods.work.backend.service.TrainerService;
import gods.work.backend.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.Duration;

@RequiredArgsConstructor
@Controller
public class LoginController {

    private final TrainerService trainerService;

    public static final Duration ACCESS_TOKEN_DURATION = Duration.ofDays(2);
    public static final String ACCESS_TOKEN_COOKIE_NAME = "access_token";


    @PostMapping("/login")
    public String login(LoginTrainerRequest requestDto, HttpServletRequest request, HttpServletResponse response) {
        String accessToken = trainerService.login(requestDto);

        if (accessToken == null) {
            return "redirect:/view/login";
        }

        // 쿠키에 토큰 추가
        int cookieMaxAge = (int) ACCESS_TOKEN_DURATION.toSeconds();
        CookieUtil.deleteCookie(request, response, ACCESS_TOKEN_COOKIE_NAME);
        CookieUtil.addCookie(response, ACCESS_TOKEN_COOKIE_NAME, accessToken, cookieMaxAge);

        return "redirect:/view/index";
    }

    @PostMapping("/signup")
    public String signup(AddTrainerRequest request) {
        trainerService.addTrainer(request.toEntity());
        return "redirect:/view/login";
    }

//    @PostMapping("/token")
//    public ResponseEntity<String> createToken(@RequestHeader("Authorization") String token) {
//        String newAccessToken = tokenService.createNewAccessToken(token);
//        return ResponseEntity.status(HttpStatus.CREATED).body(newAccessToken);
//    }
}
