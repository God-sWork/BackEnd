package gods.work.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddTrainerRequest {
    private String email;
    private String password;
}
