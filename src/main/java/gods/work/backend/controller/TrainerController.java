package gods.work.backend.controller;

import gods.work.backend.constants.WebConstants;
import gods.work.backend.dto.AddTrainerRequest;
import gods.work.backend.dto.TrainerDto;
import gods.work.backend.dto.UpdateTrainerRequest;
import gods.work.backend.dto.WithdrawalTrainerRequest;
import gods.work.backend.service.TrainerService;
import gods.work.backend.util.CookieUtil;
import gods.work.backend.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/trainer")
public class TrainerController {

    private final TrainerService trainerService;

    @GetMapping("/{trainerId}")
    public ResponseEntity<TrainerDto> getById(@PathVariable Integer trainerId) {
        return ResponseEntity.ok(trainerService.findById(trainerId).toDto());
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody AddTrainerRequest request) {
        trainerService.addTrainer(request.toEntity());
        return ResponseEntity.status(HttpStatus.CREATED).body("success");
    }

    @PutMapping("/{trainerId}")
    public ResponseEntity<String> update(@RequestBody UpdateTrainerRequest request, @PathVariable int trainerId) {
        trainerService.updateTrainer(request.toEntity(), trainerId);
        return ResponseEntity.status(HttpStatus.CREATED).body("success");
    }

    // 이메일로 아이디 찾기
    @GetMapping("/find-login-id/{email}")
    public ResponseEntity<Map<String, String>> findByEmail(@PathVariable String email) {
        Map<String, String> result = new HashMap<>();
        result.put("login_id", trainerService.findTrainerLoginIdByEmail(email));
        return ResponseEntity.ok(result);
    }

    // 이메일로 비밀번호 초기화 링크 전송
    @GetMapping("/find-password")
    public ResponseEntity<String> resetPassword(@RequestParam String loginId, @RequestParam String email) {
        trainerService.sendPasswordResetMail(loginId, email);
        return ResponseEntity.ok("success");
    }

    @PostMapping("/withdrawal")
    public ResponseEntity<String> withdrawal(@RequestBody WithdrawalTrainerRequest requestDto, HttpServletRequest request, HttpServletResponse response) {
        trainerService.withdrawalTrainer(requestDto.getPassword());

        // 로그아웃
        CookieUtil.deleteCookie(request, response, WebConstants.REFRESH_TOKEN_COOKIE_NAME);
        return ResponseEntity.ok("success");
    }

    @GetMapping
    public String test() {
        return "로그인 사용자: " + SecurityUtil.getLoginUsername();
    }
}
