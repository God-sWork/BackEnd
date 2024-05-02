package gods.work.backend.controller;

import gods.work.backend.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TokenApiController {

    private final TokenService tokenService;

    @PostMapping("/api/token")
    public ResponseEntity<String> createToken(@RequestHeader("Authorization") String token) {
        String newAccessToken = tokenService.createNewAccessToken(token);
        return ResponseEntity.status(HttpStatus.CREATED).body(newAccessToken);
    }

}
