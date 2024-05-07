package gods.work.backend.controller;

import gods.work.backend.dto.AddTrainerRequest;
import gods.work.backend.dto.UpdateTrainerRequest;
import gods.work.backend.util.SecurityUtil;
import gods.work.backend.service.TrainerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/trainer")
public class TrainerController {

    private final TrainerService trainerService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody AddTrainerRequest request) {
        trainerService.addTrainer(request.toEntity());
        return ResponseEntity.ok("success");
    }

    @PostMapping("/update")
    public ResponseEntity<String> update(@RequestBody UpdateTrainerRequest request) {
        trainerService.updateTrainer(request.toEntity());
        return ResponseEntity.ok("success");
    }

    @GetMapping
    public String test() {
        return "로그인 사용자: " + SecurityUtil.getLoginUsername();
    }
}
