package gods.work.backend.controller;

import gods.work.backend.dto.AddTrainerRequest;
import gods.work.backend.dto.UpdateTrainerRequest;
import gods.work.backend.service.TrainerService;
import gods.work.backend.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/trainer")
public class TrainerController {

    private final TrainerService trainerService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody AddTrainerRequest request) {
        trainerService.addTrainer(request.toEntity());
        return ResponseEntity.status(HttpStatus.CREATED).body("success");
    }

    @PostMapping("/update")
    public ResponseEntity<String> update(@RequestBody UpdateTrainerRequest request) {
        trainerService.updateTrainer(request.toEntity());
        return ResponseEntity.status(HttpStatus.CREATED).body("success");
    }

    @GetMapping
    public String test() {
        return "로그인 사용자: " + SecurityUtil.getLoginUsername();
    }
}
