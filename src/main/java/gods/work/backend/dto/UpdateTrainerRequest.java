package gods.work.backend.dto;

import gods.work.backend.domain.Trainer;
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

    private String password;

    private String name;

    private String gender;

    private String birthYmd;

    private String address;

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
