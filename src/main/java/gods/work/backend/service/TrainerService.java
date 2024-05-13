package gods.work.backend.service;

import gods.work.backend.config.error.exception.NotFoundException;
import gods.work.backend.domain.Trainer;
import gods.work.backend.dto.MailDto;
import gods.work.backend.repository.TrainerRepository;
import gods.work.backend.util.DateUtil;
import gods.work.backend.util.ObjectUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class TrainerService implements UserDetailsService {

    private final TrainerRepository trainerRepository;
    private final EmailService emailService;

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

    public void sendPasswordResetMail(String trainerLoginId, String email) {
        Trainer trainer = findByTrainerLoginId(trainerLoginId);
        if (trainer == null || !trainer.getEmail().equals(email)) {
            throw new NotFoundException();
        }
        sendEmail(trainer);
    }

    private void sendEmail(Trainer trainer) {
        // 임시 비밀번호 생성
        MailDto mailDto = emailService.createMailAndChargePassword(trainer);
        // 메일 발송
        emailService.mailSend(mailDto);
    }

    public Trainer findById(int id) {
        return trainerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("unexpected trainerId: " + id));
    }

    public String findTrainerLoginIdByEmail(String email) {
        return trainerRepository.findByEmail(email)
                .orElseThrow(NotFoundException::new)
                .getTrainerLoginId();
    }

    public Trainer findByTrainerLoginId(String trainerLoginId) {
        return trainerRepository.findByTrainerLoginId(trainerLoginId)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findByTrainerLoginId(username);
    }
}
