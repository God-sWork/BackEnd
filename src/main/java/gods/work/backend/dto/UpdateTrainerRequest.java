package gods.work.backend.dto;

import gods.work.backend.domain.Trainer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
@NoArgsConstructor
public class UpdateTrainerRequest {

    @Schema(description = "트레이너 아이디")
    private int trainerId;

    @Schema(description = "비밀번호", example = "1234")
    private String password;

    @Schema(description = "이름", example = "테스트")
    private String name;

    @Schema(description = "성별", example = "남")
    private String gender;

    @Schema(description = "생년월일", example = "19990619", format = "yyyyMMdd")
    private String birthYmd;

    @Schema(description = "주소", example = "경기도 수원시 이의동")
    private String address;

    @Schema(description = "휴대폰 번호", example = "01012345678")
    private String phoneNumber;

    public Trainer toEntity() {
        PasswordEncoder encoder = new BCryptPasswordEncoder();

        return Trainer.builder()
                .password(encoder.encode(this.password))
                .name(this.name)
                .gender(this.gender)
                .birthYmd(this.birthYmd)
                .address(this.address)
                .phoneNumber(this.phoneNumber)
                .build();
    }
}
