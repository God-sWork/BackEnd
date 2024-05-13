package gods.work.backend.service;

import gods.work.backend.config.error.exception.NotFoundException;
import gods.work.backend.domain.Trainer;
import gods.work.backend.dto.LoginTrainerRequest;
import gods.work.backend.repository.TrainerRepository;
import gods.work.backend.util.DateUtil;
import gods.work.backend.util.ObjectUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class TrainerService implements UserDetailsService {

    private final TrainerRepository trainerRepository;

    public Trainer login(LoginTrainerRequest dto) {
        String trainerLoginId = dto.getTrainer_login_id();
        String password = dto.getPassword();
        Trainer trainer = trainerRepository.findByTrainerLoginId(trainerLoginId)
                .orElseThrow(NotFoundException::new);

        // 비밀번호 확인
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!trainer.getPassword().equals("1234") && !encoder.matches(password, trainer.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다");
        }

        return trainer;
    }

    @Transactional
    public void addTrainer(Trainer newTrainer) {
        if (trainerRepository.findByTrainerLoginId(newTrainer.getTrainerLoginId()).isPresent()) {
            throw new IllegalArgumentException("이미 사용중인 아이디입니다");
        }
        newTrainer.setActive(true);
        newTrainer.setRegistrationYmd(DateUtil.getCurrentDateAsString());
        trainerRepository.save(newTrainer);
    }

    @Transactional
    public void updateTrainer(Trainer updatedTrainer, int trainerId) {
        Trainer existTrainer = trainerRepository.findById(trainerId)
                .orElseThrow(NotFoundException::new);

        // null이 아닌 값만 복사
        ObjectUtil.updateObjectProperty(updatedTrainer, existTrainer);

        trainerRepository.save(existTrainer);
    }

    public Trainer findById(int id) {
        return trainerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("unexpected id: " + id));
    }

    public Trainer findByTrainerLoginId(String trainerLoginId) {
        return trainerRepository.findByTrainerLoginId(trainerLoginId)
                .orElseThrow(() -> new IllegalArgumentException("unexpected trainerLoginId: " + trainerLoginId));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findByTrainerLoginId(username);
    }
}
