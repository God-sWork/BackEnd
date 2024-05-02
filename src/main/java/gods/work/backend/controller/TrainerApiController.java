package gods.work.backend.controller;

import gods.work.backend.dto.AddTrainerRequest;
import gods.work.backend.service.TrainerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class TrainerApiController {

    private final TrainerService trainerService;

    @PostMapping("/trainer")
    public String signup(AddTrainerRequest request) {
        trainerService.save(request);
        return "redirect:/login";
    }
}
