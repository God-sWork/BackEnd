package gods.work.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginTrainerRequest {

    @Schema(description = "email", example = "test@test.com")
    private String email;

    @Schema(description = "password", example = "1234")
    private String password;
}
