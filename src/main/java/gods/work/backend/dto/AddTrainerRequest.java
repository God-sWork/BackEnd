package gods.work.backend.dto;

import gods.work.backend.domain.Trainer;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
@NoArgsConstructor
public class AddTrainerRequest {

    private String email;

    private String password;

    private String name;

    private String gender;

    private String birthYmd;

    private String address;

    private String phoneNumber;

    public Trainer toEntity() {
        PasswordEncoder encoder = new BCryptPasswordEncoder();

        return Trainer.builder()
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

        this.password = updatedTrainer.getPassword() == null || updatedTrainer.getPassword().isEmpty()
                ? this.password : encoder.encode(updatedTrainer.getPassword());
        this.name = updatedTrainer.getName() == null ? this.name : updatedTrainer.getName();
        this.gender = updatedTrainer.getGender() == null ? this.gender : updatedTrainer.getGender();
        this.birthYmd = updatedTrainer.getBirthYmd() == null ? this.birthYmd : updatedTrainer.getBirthYmd();
        this.address = updatedTrainer.getAddress() == null ? this.address : updatedTrainer.getAddress();
        this.phoneNumber = updatedTrainer.getPhoneNumber() == null ? this.phoneNumber : updatedTrainer.getPhoneNumber();
    }
}
