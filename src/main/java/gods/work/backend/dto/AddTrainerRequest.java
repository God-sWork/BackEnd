package gods.work.backend.dto;

import gods.work.backend.domain.Trainer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
@NoArgsConstructor
public class AddTrainerRequest {

    @Schema(description = "로그인 아이디", example = "yewon.e19")
    private String trainerLoginId;

    @Schema(description = "이메일", example = "yewon.e19@gmail.com")
    private String email;

    @Schema(description = "비밀번호", example = "1234")
    private String password;

    @Schema(description = "이름", example = "이예원")
    private String name;

    @Schema(description = "성별", example = "여")
    private String gender;

    @Schema(description = "생년월일", example = "19990620", format = "yyyyMMdd")
    private String birthYmd;

    @Schema(description = "주소", example = "경기도 수원시 이의동")
    private String address;

    @Schema(description = "휴대폰 번호", example = "01022223333")
    private String phoneNumber;

    public Trainer toEntity() {
        PasswordEncoder encoder = new BCryptPasswordEncoder();

        return Trainer.builder()
                .trainerLoginId(this.trainerLoginId)
                .email(this.email)
                .password(encoder.encode(this.password))
                .name(this.name)
                .gender(this.gender)
                .birthYmd(this.birthYmd)
                .address(this.address)
                .phoneNumber(this.phoneNumber)
                .build();
    }

    public void update(Trainer updatedTrainer) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();

        this.email = updatedTrainer.getEmail() == null ? this.email : updatedTrainer.getEmail();
        this.password = updatedTrainer.getPassword() == null || updatedTrainer.getPassword().isEmpty()
                ? this.password : encoder.encode(updatedTrainer.getPassword());
        this.name = updatedTrainer.getName() == null ? this.name : updatedTrainer.getName();
        this.gender = updatedTrainer.getGender() == null ? this.gender : updatedTrainer.getGender();
        this.birthYmd = updatedTrainer.getBirthYmd() == null ? this.birthYmd : updatedTrainer.getBirthYmd();
        this.address = updatedTrainer.getAddress() == null ? this.address : updatedTrainer.getAddress();
        this.phoneNumber = updatedTrainer.getPhoneNumber() == null ? this.phoneNumber : updatedTrainer.getPhoneNumber();
    }
}
