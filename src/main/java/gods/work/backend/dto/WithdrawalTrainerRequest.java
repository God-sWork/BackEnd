package gods.work.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WithdrawalTrainerRequest {

    @Schema(description = "비밀번호")
    private String password;
}
