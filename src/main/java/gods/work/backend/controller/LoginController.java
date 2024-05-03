package gods.work.backend.controller;

import gods.work.backend.dto.AddTrainerRequest;
import gods.work.backend.dto.LoginTrainerRequest;
import gods.work.backend.service.TrainerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class LoginController {

    private final TrainerService trainerService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody AddTrainerRequest request) {
        trainerService.save(request);
        return ResponseEntity.ok("success");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginTrainerRequest request) {

        int id = trainerService.login(request);
//
//        if(trainer == null) {
//            return ResponseEntity.ok("fail");
//        }

        // 로그인 성공 => Jwt Token 발급

//        String secretKey = "my-secret-key-123123";
//        long expireTimeMs = 1000 * 60 * 60;     // Token 유효 시간 = 60분
//
//        String jwtToken = JwtTokenUtil.createToken(user.getLoginId(), secretKey, expireTimeMs);

        return ResponseEntity.ok("success");
    }
}
