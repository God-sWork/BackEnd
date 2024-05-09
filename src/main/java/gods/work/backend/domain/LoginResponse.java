package gods.work.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class LoginResponse {
    private String accessToken;
}