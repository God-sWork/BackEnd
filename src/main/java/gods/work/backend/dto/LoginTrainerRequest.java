package gods.work.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginTrainerRequest {

    @Schema(description = "trainer_login_id", example = "test")
    private String trainer_login_id;

    @Schema(description = "password", example = "1234")
    private String password;
}
