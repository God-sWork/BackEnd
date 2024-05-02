package gods.work.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginTrainerRequest {
    private String email;
    private String password;
}
