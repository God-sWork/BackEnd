package gods.work.backend.controller;

import gods.work.backend.domain.Trainer;
import gods.work.backend.dto.AddTrainerRequest;
import gods.work.backend.dto.LoginTrainerRequest;
import gods.work.backend.service.TokenService;
import gods.work.backend.service.TrainerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class LoginController {

    private final TrainerService trainerService;
    private final TokenService tokenService;



    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginTrainerRequest request) {
        return ResponseEntity.ok(trainerService.login(request));
    }

//    @PostMapping("/token")
//    public ResponseEntity<String> createToken(@RequestHeader("Authorization") String token) {
//        String newAccessToken = tokenService.createNewAccessToken(token);
//        return ResponseEntity.status(HttpStatus.CREATED).body(newAccessToken);
//    }
}
