package gods.work.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class TokenResponse {
    private String accessToken;
}