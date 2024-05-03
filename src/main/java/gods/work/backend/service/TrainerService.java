package gods.work.backend.service;

import gods.work.backend.domain.Trainer;
import gods.work.backend.dto.AddTrainerRequest;
import gods.work.backend.dto.LoginTrainerRequest;
import gods.work.backend.repository.TrainerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TrainerService implements UserDetailsService {

    private final TrainerRepository trainerRepository;

    public int save(AddTrainerRequest dto) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        return trainerRepository.save(Trainer.builder()
                .email(dto.getEmail())
                .password(encoder.encode(dto.getPassword()))
                .build()).getTrainerId();
    }

    public int login(LoginTrainerRequest dto) {
        String email = dto.getEmail();
        String password = dto.getPassword();
        Trainer trainer = trainerRepository.findByEmail(email).orElse(null);

        if (trainer == null) {
            throw new IllegalArgumentException("등록된 이메일이 없습니다.");
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (!encoder.matches(password, trainer.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // todo
        return 0;
    }

    public Trainer findById(int id) {
        return trainerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("unexpected id: " + id));
    }

    public Trainer findByEmail(String email) {
        return trainerRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("unexpected email: " + email));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
